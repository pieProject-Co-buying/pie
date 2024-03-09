package com.pie.pieProject.controllers;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShareServiceController {
	@Autowired
	IShareServiceDao dao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ILikeDao ldao;
	@Autowired
	IPaymentDAO Pdao;
	@Autowired
	BoardComp Bcomp;
	@Autowired
	IParticipateCheckDao paDao;
	@Autowired
	IProxyBuyDao rdao;


	/********** 전체 게시물 조회 **********/
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(@RequestParam(value="category", required = false)String category,@RequestParam(value="search", required = false) String key, Model model) {
		List<ShareServiceDto> list = new ArrayList<>();
		
		if(category==null||category.equals("")||key==null||key.equals("")) {
			list = dao.getBoardList();
		}else if((category==null||category.equals(""))&&key!=null&&!key.equals("")) {
			list = dao.searchBoard(key);
		}else if(category!=null&&!category.equals("")&&(key==null||key.equals(""))) {
			
		}else if(category!=null&&!category.equals("")&&key!=null&&!key.equals("")) {
			list= dao.searchTitle(category, key);
		}
			
		for (ShareServiceDto d : list) {
			if(d.getTag()==null||d.getTag().equals("#")) {
				d.setTags(null);
			}else { 
				d.setTags(Bcomp.setArraysData(d.getTag(), "#"));
			}
			String c = d.getCategory();
			if (c.equals("OTT")) {
				d.setCategory("OTT");
			} else if (c.equals("game")) {
				d.setCategory("게임");
			} else if (c.equals("bookAndMusic")) {
				d.setCategory("도서/음악");
			}
		}
		

		model.addAttribute("list", list);
		return "pieContents/shareService/shareServiceBoard";
	}

	/********** 게시물 상세 페이지 **********/
	@RequestMapping("/boardList")
	public String showBoard(@RequestParam("num") String num,HttpServletRequest request, Model model) {
		
		String sId = num;
		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));
		
		// 기간 만료 날짜 비교
		LocalDateTime deadline = LocalDateTime.parse(dto.getDeadLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime now = LocalDateTime.now();
		
//		다른 게시글 리스트
		List<ShareServiceDto> list = Bcomp.translateShareList(dao.getBoardList());
		
//		조회수
		dao.updateHit(num);
		
		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
		if(dto.getTag()==null||dto.getTag().equals("#")) {
			dto.setTags(null);
		}else {
			dto.setTags(Bcomp.setArraysData(dto.getTag(), "#"));
		}
		
		// 인원 만원시
		if(dto.getPersonnelMax()<=dto.getPersonnelNow()) {
			dao.maxChk(Integer.parseInt(sId));
		// 기간 만료시
		}else if(deadline.isBefore(now) || deadline.isEqual(now)) {
			dao.dateOver(Integer.parseInt(sId));
			Pdao.allRefund(Integer.parseInt(sId));
		}
		
//		좋아요
		String table = "shareServiceBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), sId, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		
//	    참여멤버 목록
	    List<MemberDto> partilist = paDao.getPartiMem(num, "Share");
	    
		model.addAttribute("partiMem", partilist);
		model.addAttribute("partiMemTotal", partilist.size());
		model.addAttribute("board", dto);
		model.addAttribute("member",mdto);
		model.addAttribute("list",list);
		
		
//		참여 여부 확인
		model.addAttribute("in",(paDao.chkPartiMem(Bcomp.getSession(request, "userId"), "Share", num)>0));
//		참여 했다가 취소 여부 확인
		model.addAttribute("cancel",(paDao.canceledBuying(Bcomp.getSession(request, "userId"), "Share", num)>0));
//		환불요청중
		if(Pdao.myPay(Bcomp.getSession(request, "userId"), num, "Share")!=null) {
			model.addAttribute("productNum",Pdao.myPay(Bcomp.getSession(request, "userId"), num, "Share").getPay_Merchant_uid());
		}
		
		return "pieContents/shareService/shareServiceProduct";
	}

	/********** 해당 게시물 수정 페이지 이동 **********/
	@RequestMapping("/updateShareBoardForm")
	public String modify(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");

		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));

		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
		if(dto.getTag()==null||dto.getTag().equals("#")) {
			dto.setTags(null);
		}else {
			dto.setTags(Bcomp.setArraysData(dto.getTag(), "#"));
		}
		/* dao.updateHit(sId); */

		String table = "shareServiceBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), sId, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}

		model.addAttribute("board", dto);

		return "pieContents/shareService/shareServiceUpdate";
	}

	/********** 해당 게시물 수정 **********/
	@RequestMapping("/updateShareBoard")
	public String update(HttpServletRequest request, Model model) {
		ShareServiceDto dto = new ShareServiceDto();
		String numID = request.getParameter("num");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String personnelMax = request.getParameter("personnelMax");
		String DeadLine = request.getParameter("deadLine");

		dto.setNum(numID);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setPricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setPriceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setPersonnelMax(Integer.parseInt(personnelMax));
		dto.setProductImg(request.getParameter("files"));
		dto.setCategory(request.getParameter("category"));
		dto.setTag(request.getParameter("pie_tagsOutput"));
		dto.setIp(request.getRemoteAddr());
		dto.setDeadLine(DeadLine);
		dto.setBrand(request.getParameter("brand"));
		dto.setProductName(request.getParameter("productName"));

		dao.updateBoard(dto);

		return "redirect:/boardList?num=" + numID;
	}

	/********** 해당 게시물 삭제 **********/
	@RequestMapping("/deleteShareService")
	public String delete(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		int id = Integer.parseInt(sId);
		dao.deleteBoard(id);
		return "redirect:/shareServiceBoard";
	}

	/********** 제목, 내용, 카테고리 기반 검색 **********/
	@RequestMapping("/searchTitle")
	public String search(HttpServletRequest request, Model model) {
		String sId = request.getParameter("search");
		String category="";
		
		if(request.getParameter("OTT").equals("OTT")) {
			category="OTT";
		}else if(request.getParameter("game").equals("game")) {
			category="game";
		}else if(request.getParameter("bookAndMusic").equals("bookAndMusic")) {
			category="game";
		}
		model.addAttribute("list", dao.searchTitle(sId,category));
		return "pieContents/shareService/shareServiceBoard";
	}

	/********** 게시판 작성 페이지 이동 **********/
	@RequestMapping("/writePost")
	public String witePost() {
		return "pieContents/shareService/shareForm";
	}

	/********** 게시판 작성 **********/
	@RequestMapping("/insertBoard")
	public String insert(HttpServletRequest request, Model model) {

		ShareServiceDto dto = new ShareServiceDto();
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));

		dto.setId(Bcomp.getSession(request, "userId"));

		dto.setCategory(request.getParameter("category"));

		if (mdto.getPremium().equals("pro")) {
			dto.setPremium("1");
		} else {
			dto.setPremium("0");
		}

		dto.setNickname(Bcomp.getSession(request, "nickName"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setProfileImg(Bcomp.getSession(request, "pic"));
		dto.setProductImg(request.getParameter("fileStr"));
		dto.setTag(request.getParameter("pie_tagsOutput"));
		dto.setPersonnelMax(Integer.parseInt(request.getParameter("personnelMax")));
		dto.setPricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setPriceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setIp(request.getRemoteAddr());
		dto.setDeadLine(request.getParameter("deadLine"));
		dto.setBrand(request.getParameter("brand"));
		dto.setProductName(request.getParameter("productName"));

		dao.insertBoard(dto);

		return "redirect:/shareServiceBoard";
	}

	
	
	/**********************admin 게시글 관리**********************/
	@RequestMapping("/shareServiceBoardConsole")
	public String boardConsole(@RequestParam("page") int page, @RequestParam(value="search", required=false) String search, Model model) {
		List<ShareServiceDto> list = dao.getBoardList();
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		if(pageNum<=0) pageNum=1;
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "pieContents/shareService/shareServiceBoardConsole";
	}
	/**********admin 게시물관리 페이지 id,nickname 기반 검색**********/
	@RequestMapping("/searchBoardName")
	public String searchBuyer(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		int page = Integer.parseInt(request.getParameter("page"));
		
		List<ShareServiceDto> list = dao.searchBuyer(search);
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		
		model.addAttribute("list", templist);
		return"pieContents/shareService/shareServiceBoardConsole";
	}
	/**********admin 게시물관리 페이지 진행여부**********/
	@RequestMapping("/processRemote")
	public String processRemote(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		String search = request.getParameter("search");
		int page = Integer.parseInt(request.getParameter("page"));
		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));
		
		if(dto.getProcess().equals("1")) {			
			dao.stopChk(Integer.parseInt(sId));
		}else if(dto.getProcess().equals("2")) {
			dao.minChk(Integer.parseInt(sId));			
		}else if(dto.getProcess().equals("3")) {
			 dao.dateOver(Integer.parseInt(sId)); 
		}
			 
		
		List<ShareServiceDto> list = dao.searchBuyer(search);
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		
		model.addAttribute("list", templist);
		return "redirect:/shareServiceBoardConsole?page=1";
	}
}