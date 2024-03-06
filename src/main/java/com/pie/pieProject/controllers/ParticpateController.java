package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ParticpateController {
	@Autowired
	BoardComp bcomp;
	@Autowired
	IShareServiceDao sdao;
	@Autowired
	IProxyBuyDao pdao;
	@Autowired
	ITownBuyBoardDao tdao;
	@Autowired
	IPaymentDAO Pdao;
	@Autowired
	IParticipateCheckDao paDao;

	/********** 내가 작성한 게시글 조회 **********/
	@RequestMapping("/shareServiceApply")
	public String Applyboard(HttpServletRequest request, Model model) {
		// HttpSession 객체 가져오기
		// 세션에서 userId 가져오기
		String sId = bcomp.getSession(request, "userId");
		String category = request.getParameter("category");

		/*
		 * spring security 가 적용되어 있어서 사용하지 않아도 됨 // 세션 id가 null일 경우 로그인 페이지로 이동 if (sId
		 * == null) { return "pieContents/members/login_form"; }
		 */
		
		if (category == null || category.equals("") || category.equals("town") ) {
			List<TownBuyBoardDto> list = tdao.townListbyID(sId);
			for (TownBuyBoardDto d : list) {
				d.setTo_category(bcomp.translate(d.getTo_category()));
			}
			model.addAttribute("list", list);
		} else if (category.equals("share")) {
			List<ShareServiceDto> list = sdao.myBoard(sId);
			for (ShareServiceDto d : list) {
				d.setSh_category(bcomp.translate(d.getSh_category()));
			}
			model.addAttribute("list", list);
		} 

		// 뷰 이름 반환
		return "pieContents/shareService/shareServiceApply";
	}
	
	// 개인 사용자 구매 내역
		@RequestMapping("/shareServicebuyBoard")
		public String buyList(HttpServletRequest request, Model model) {
			// HttpSession 객체 가져오기
			// 세션에서 userId 가져오기
			String sId = bcomp.getSession(request, "userId");
			String category = request.getParameter("category");
			
			String board = "proxybuyboard";
			String prefix = "pr_";
			
			System.out.println(sId);
			
			// 세션 id가 null일 경우 로그인 페이지로 이동
			// 모델에 추가
			
			// 카테고리 지정을 안할경우 대리구매 관련 구매내역부터 보이게
			if(category==null||category.equals("")||category.equals("town")) {
				category = "town";
				List<TownBuyBoardDto> list = paDao.getTownboard(sId);
				List<String> partList = paDao.getDate(sId, "townBuyBoard");
				model.addAttribute("list", list);
				model.addAttribute("partList", partList);
				// 뷰 이름 반환
				return "pieContents/shareService/shareServicebuyBoard";
				
			}else if(category.equals("Share")) {
				board = "shareServiceBoard";
				prefix = "sh_";
			}			

			List<PaymentDTO> list = Pdao.buyList(sId,category);
			List<String> piclist = Pdao.buyListpic(sId,category,board,prefix);
			List<String> processList = Pdao.buyListpro(sId,category,board,prefix);
			List<String> buyNum = Pdao.buyListNum(sId,category,board,prefix);

			System.out.println("리스트 : "+list.size());
			System.out.println("사진 : "+piclist.size());
			System.out.println("진행 : "+processList.size());
			System.out.println("넘버 : "+buyNum.size());
			
			 for(int i = 0; i<list.size(); i++) {
			 list.get(i).setProductImg(piclist.get(i));
			 list.get(i).setProcess(processList.get(i));
			 list.get(i).setNum(buyNum.get(i));
			 }
		
			model.addAttribute("list", list);
			// 뷰 이름 반환
			return "pieContents/shareService/shareServicebuyBoard";
		}
}
