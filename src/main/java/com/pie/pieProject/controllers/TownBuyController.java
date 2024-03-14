package com.pie.pieProject.controllers;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.ISearchDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TownBuyController {

	@Autowired
	ITownBuyBoardDao dao;
	@Autowired
	ILikeDao ldao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ISearchDao sdao;
	@Value("${kakao.api.mapkey}")
	String kakaoMapApiKey;
	@Autowired
	IParticipateCheckDao paDao;
	@Autowired
	BoardComp bcomp;
	


	@GetMapping("/townBuySearch")
	public String toBoardList(HttpServletRequest request, Model model) {
		
		/* List <TownBuyBoardDto> tlist = dao.listDao(); */
		
		
		//로그인한 유저의 find 메소드를 활용해서 정보를 가지고 온다
		MemberDto mdto = mdao.find(bcomp.getSession(request, "userId")); //현재 로그인한 유저
		String useraddr = mdto.getAddress_main();
		String userMainAddr = useraddr.substring(0, 6);
		System.out.println(userMainAddr);
		
		
//		for(TownBuyBoardDto towndto : tlist) {
//			String addr = towndto.getAddress(); //addr에 towndto.getAddress() 담음	
//			String mainAddr = addr.substring(0, 6);
//			int boardNum = towndto.getNum();
//			
//			
//			obj.put("realaddr", addr);
//			obj.put("mainAddr", mainAddr);			
//			obj.put("boardNum", boardNum);
//			
//			System.out.println(obj);		
//		}
//		
		
		
		//listLocal 메소드를 이용해 유저의 주소를 담아 해당하는 주소와 일치하는 게시물들을 불러온다
		// 현재 모집마감 게시글도 불러옴
		List<TownBuyBoardDto> townlist = bcomp.translateTownList(dao.listLocal(userMainAddr));
		
		/*
		 * model.addAttribute("townlist", townlist);
		 */
		
		model.addAttribute("list", townlist);
		
		
		
		/*
		 * model.addAttribute("foodList", dao.categoryDaoNum("food", 4));
		 * model.addAttribute("babyList", dao.categoryDaoNum("baby", 4));
		 * model.addAttribute("lifeList", dao.categoryDaoNum("life", 4));
		 */

		/*
		 * String sP = request.getParameter("to_premium");
		 * model.addAttribute("premiumList", dao.listPremiumDao()); 
		 */
		
		model.addAttribute("bestKey",sdao.bestKeyword("townBuy"));

		model.addAttribute("PFoodList", bcomp.translateTownList(dao.listPremiumDao("food", 4, userMainAddr)));
		model.addAttribute("PBabyList", bcomp.translateTownList(dao.listPremiumDao("baby", 4, userMainAddr)));
		model.addAttribute("PLifeList", bcomp.translateTownList(dao.listPremiumDao("life", 4, userMainAddr)));
		

		return "pieContents/townBuying/townBuySearch";

	}

	@RequestMapping("/townBuyproduct")
	public String toBoardView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		
			String sId = request.getParameter("num");
			MemberDto mdto = mdao.find(bcomp.getSession(request, "userId")); //현재 로그인한 유저
			String useraddr = mdto.getAddress_main();
			String userMainAddr = useraddr.substring(0, 6);
			
//			조회수
			dao.updateHit(num);
			
//			다른 게시글 리스트
			List<TownBuyBoardDto> townlist = bcomp.translateTownList(dao.listLocal(userMainAddr));			
			
			TownBuyBoardDto dto = dao.viewDao(sId);
			
			dto.setProductImgs(bcomp.setArraysData(dto.getProductImg(), "/"));
			if(dto.getTag()==null||dto.getTag().equals("#")) {
				dto.setTags(null);
			}else {
				dto.setTags(bcomp.setArraysData(dto.getTag(), "#"));
			}
			dto.setCategory(bcomp.translate(dto.getCategory()));
			
			/* dao.updateHit(sId); */
			
//			좋아요
			String table = "townBuyBoard";
			if (ldao.checkLike(bcomp.getSession(request, "userId"), sId, table) > 0) {
				model.addAttribute("like", true);
			} else {
				model.addAttribute("like", false);
			}
						
			
		    if (dto.getPersonnelNow() >= dto.getPersonnelMax()) {
		        dao.updateTownProcess(sId);
		    }
			
//		    참여멤버 목록
		    List<MemberDto> list = paDao.getPartiMem(num, "townBuyBoard");
		    
		    model.addAttribute("list",townlist);
			model.addAttribute("partiMem", list);
			model.addAttribute("partiMemTotal", list.size());
			model.addAttribute("board", dto);
			
//			참여 여부 확인
			model.addAttribute("in",(paDao.chkPartiMem(bcomp.getSession(request, "userId"), "townBuyBoard", num)>0));
//			참여 했다가 취소 여부 확인
			model.addAttribute("cancel",(paDao.canceledBuying(bcomp.getSession(request, "userId"), "townBuyBoard", num)>0));
			
			
		

		return "pieContents/townBuying/townBuyproduct";

	}
	
	
	
	// 참여인원 증가
	@RequestMapping("/updatePersonnelNow")
	public String updatePersonnelNow(@RequestParam("num") String num, HttpServletRequest request) {

		dao.updatePersonnelNow(num);
		paDao.participate(num, "townBuyBoard", bcomp.getSession(request, "userId"));
		
		System.out.println("체크" + num);
		return "redirect:/townBuyproduct?num=" + request.getParameter("num");
	}

	
	
	
	// 참여인원 감소
	@RequestMapping("/canclePersonnelNow")
	public String canclePersonnelNow(@RequestParam("num") String num, HttpServletRequest request) {

		dao.canclePersonnelNow(num);	
		paDao.cancelBuying(bcomp.getSession(request, "userId"),"townBuyBoard" ,num);
		
		System.out.println("체크" + num);
				
		return "redirect:/townBuyproduct?num=" + request.getParameter("num");
	}	
	
	
	@RequestMapping("/updateTownProduct")

	public String update(HttpServletRequest request) {
	    
	    
		TownBuyBoardDto dto = new TownBuyBoardDto();
		MemberDto mdto = mdao.find(bcomp.getSession(request, "userId"));
		
		if(dto.getPersonnelNow()>1) {
			return "redirect:/townBuyproduct?num=" + request.getParameter("num");
		}
		
		
		  System.out.println(request.getParameter("category"));
		  System.out.println(request.getParameter("title"));
		  System.out.println(request.getParameter("content"));
		  System.out.println(request.getParameter("price"));
		  System.out.println(request.getParameter("personnelMax"));
		  System.out.println(request.getParameter("deadLine"));
		  System.out.println(request.getParameter("num"));
		  System.out.println(request.getParameter("pie_tagsOutput"));
		  System.out.println(request.getParameter("fileStr"));
		  System.out.println(request.getParameter("price_per"));
		  System.out.println(request.getParameter("price_total"));
		  System.out.println(request.getParameter("brand"));
		  System.out.println(request.getParameter("productName"));
		 

		dto.setCategory(request.getParameter("category"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setNum(request.getParameter("num"));
		dto.setPersonnelMax(Integer.parseInt(request.getParameter("personnelMax")));
		dto.setDeadLine(request.getParameter("deadLine"));
		dto.setProductImg(request.getParameter("fileStr"));
		dto.setTag(request.getParameter("pie_tagsOutput"));
		
		dto.setAddress(mdto.getAddress_main());
		dto.setPricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setPriceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setIp(request.getRemoteAddr());
		
		dto.setBrand(request.getParameter("brand"));
		dto.setProductName(request.getParameter("productName"));
		
		
		dao.updateDao(dto);
		

		return "redirect:/townBuyproduct?num=" + request.getParameter("num");

		// return "redirect:/townBuyproduct?id="+sId;
		// 리턴시 어떤 id를 갖고 있는 페이지로 갈건지 지정해주지 않아서 에러
	}

	@RequestMapping("/updateTownProductForm")
	public String updateForm(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		
		TownBuyBoardDto dto = dao.viewDao(sId);
		dto.setProductImgs(bcomp.setArraysData(dto.getProductImg(), "/"));
		if(dto.getTag()==null||dto.getTag().equals("#")) {
			dto.setTags(null);
		}else {
			dto.setTags(bcomp.setArraysData(dto.getTag(), "#"));
		}
		model.addAttribute("board", dto);
		return "pieContents/townBuying/updateTownProductForm";

	}

	
	@RequestMapping("/deleteTownProduct")
	public String delete(HttpServletRequest request) {

		String sId = request.getParameter("num");
		dao.deleteDao(sId);

		return "redirect:/townBuySearch";

	}

	

	@RequestMapping("/townBuyingCategoryChoice")
	public String category(HttpServletRequest request, Model model) {

		String category = request.getParameter("category");

		
		//로그인한 유저의 find 메소드를 활용해서 정보를 가지고 온다
		MemberDto mdto = mdao.find(bcomp.getSession(request, "userId")); //현재 로그인한 유저
		String useraddr = mdto.getAddress_main();
		String userMainAddr = useraddr.substring(0, 6);
		System.out.println(userMainAddr);

		model.addAttribute("list", bcomp.translateTownList(dao.categoryDao(category, userMainAddr)));
		model.addAttribute("bestKey",sdao.bestKeyword("townBuy"));

		return "pieContents/townBuying/townBuyingCategory";

	}
	
	

	@RequestMapping("/writeTownBoard")
	public String write(HttpServletRequest request, Model model) {

		TownBuyBoardDto dto = new TownBuyBoardDto();
		MemberDto mdto = mdao.find(bcomp.getSession(request, "userId"));

		dto.setId(bcomp.getSession(request, "userId"));
		dto.setCategory(request.getParameter("category"));
		
		if (mdto.getPremium().equals("pro")) {
			dto.setPremium("1");
		} else {
			dto.setPremium("0");
		}
		
		dto.setNickname(bcomp.getSession(request,"nickName"));
		dto.setTitle(request.getParameter("title"));
		dto.setContent(request.getParameter("content"));
		dto.setProfileImg(bcomp.getSession(request, "pic"));
		dto.setProductImg(request.getParameter("fileStr"));
		dto.setTag(request.getParameter("pie_tagsOutput"));
		dto.setAddress(mdto.getAddress_main());
		dto.setPersonnelMax(Integer.parseInt(request.getParameter("personnelMax")));
		dto.setPricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setPriceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setIp(request.getRemoteAddr());
		dto.setDeadLine(request.getParameter("deadLine"));
		dto.setBrand(request.getParameter("brand"));
		dto.setProductName(request.getParameter("productName"));
		
		
		dao.writeDao(dto);

		return "redirect:/townBuySearch";

	}
	
	// 동네 공구 메인페이지
	@RequestMapping("/townBuying")
	public String townBPage(HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(bcomp.getSession(request, "userId"));
		String addr = mdto.getAddress_main().substring(0,6);

		List<TownBuyBoardDto> list = dao.listLocal(addr);
		model.addAttribute("list", list);
		model.addAttribute("now",mdto.getAddress_main());
		model.addAttribute("Mapi",kakaoMapApiKey);
		
		return "pieContents/townBuying/townBuyMain";
	}
	
	@GetMapping("/cancelBuying")
	public String cancelBuying(@RequestParam("num") String num, HttpServletRequest request) {
		paDao.cancelBuying(bcomp.getSession(request, "userId"), "townBuyBoard", num);
		return "redirect:/townBuyproduct?num="+num;
	}
	
	
}
