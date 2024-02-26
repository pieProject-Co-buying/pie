package com.pie.pieProject.controllers;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

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
	@Value("${kakao.api.mapkey}")
	String kakaoMapApiKey;
	

	@RequestMapping("/townBuySearch")
	public String toBoardList(HttpServletRequest request, Model model) {

		model.addAttribute("list", dao.listDao());
		model.addAttribute("foodList", dao.categoryDaoNum("food", 4));
		model.addAttribute("babyList", dao.categoryDaoNum("baby", 4));
		model.addAttribute("lifeList", dao.categoryDaoNum("life", 4));

		/*
		 * String sP = request.getParameter("to_premium");
		 * model.addAttribute("premiumList", dao.listPremiumDao());
		 */

		return "pieContents/townBuying/townBuySearch";

	}

	@RequestMapping("/townBuyproduct")
	public String toBoardView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		
			String sId = request.getParameter("num");
			
			dao.updateHit(num);
			
			
			TownBuyBoardDto dto = dao.viewDao(sId);

			dto.setTo_productImgs(setArraysData(dto.getTo_productImg(), "/"));
			if(dto.getTo_tag()==null||dto.getTo_tag().equals("#")) {
				dto.setTo_tags(null);
			}else {
				dto.setTo_tags(setArraysData(dto.getTo_tag(), "#"));
			}
			/* dao.updateHit(sId); */
			
			String table = "townBuyBoard";
			if (ldao.checkLike(getSession(request, "userId"), sId, table) > 0) {
				model.addAttribute("like", true);
			} else {
				model.addAttribute("like", false);
			}
			
		model.addAttribute("board", dto);

		return "pieContents/townBuying/townBuyproduct";

	}

	@RequestMapping("/updateTownProduct")

	public String update(HttpServletRequest request) {
	    
	    
		TownBuyBoardDto dto = new TownBuyBoardDto();
		MemberDto mdto = mdao.find(getSession(request, "userId"));
		
		System.out.println(request.getParameter("to_category"));
		System.out.println(request.getParameter("to_title"));
		System.out.println(request.getParameter("to_content"));
		System.out.println(request.getParameter("to_price"));
		System.out.println(request.getParameter("to_personnelMax"));
		System.out.println(request.getParameter("to_deadLine"));
		System.out.println(request.getParameter("num"));

		dto.setTo_category(request.getParameter("to_category"));
		dto.setTo_title(request.getParameter("to_title"));
		dto.setTo_content(request.getParameter("to_content"));
		dto.setTo_num(Integer.parseInt(request.getParameter("num")));
		/* dto.setTo_num(request.getParameter("id")); */
		dto.setTo_personnelMax(Integer.parseInt(request.getParameter("to_personnelMax")));
		dto.setTo_deadLine(request.getParameter("to_deadLine"));
		dto.setTo_productImg(request.getParameter("to_files"));
		dto.setTo_tag(request.getParameter("pie_tagsOutput"));
		
		dto.setTo_address(mdto.getAddress_main());
		dto.setTo_pricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setTo_priceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setTo_ip(request.getRemoteAddr());
		
		
		dao.updateDao(dto);
		

		return "redirect:/townBuyproduct?num=" + request.getParameter("num");

		// return "redirect:/townBuyproduct?id="+sId;
		// 리턴시 어떤 id를 갖고 있는 페이지로 갈건지 지정해주지 않아서 에러
	}

	@RequestMapping("/updateTownProductForm")
	public String updateForm(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		
		TownBuyBoardDto dto = dao.viewDao(sId);
		dto.setTo_productImgs(setArraysData(dto.getTo_productImg(), "/"));
		if(dto.getTo_tag()==null||dto.getTo_tag().equals("#")) {
			dto.setTo_tags(null);
		}else {
			dto.setTo_tags(setArraysData(dto.getTo_tag(), "#"));
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

	@RequestMapping("/searchTownBuy")

	public String search(HttpServletRequest request, Model model) {
		String townKeyword = request.getParameter("townKeyword");
		String towncategory = request.getParameter("towncategory");
		
		List<TownBuyBoardDto> list = dao.searchDao(townKeyword);
		
		int food = 0;
		int baby = 0;
		int beautyAndFashion = 0;
		int pet = 0;
		int life= 0;
		int etc = 0;
		
		for(TownBuyBoardDto b : list) {
			String c= b.getTo_category();
			if(c.equals("food")) food++;
			else if(c.equals("baby")) baby++;
			else if(c.equals("beautyAndFashion")) beautyAndFashion++;
			else if(c.equals("pet")) pet++;
			else if(c.equals("life")) life++;
			else if(c.equals("etc")) etc++;
		}

		model.addAttribute("list", list);
		model.addAttribute("food", food);
		model.addAttribute("baby", baby);
		model.addAttribute("beautyAndFashion", beautyAndFashion);
		model.addAttribute("pet", pet);
		model.addAttribute("life", life);
		model.addAttribute("etc", etc);

		return "pieContents/townBuying/townBuySearchResult";

	}

	@RequestMapping("/townBuyingCategoryChoice")

	public String category(HttpServletRequest request, Model model) {

		String category = request.getParameter("category");

		model.addAttribute("list", dao.categoryDao(category));

		return "pieContents/townBuying/townBuyingCategory";

	}
	
	

	@RequestMapping("/writeTownBoard")
	public String write(HttpServletRequest request, Model model) {

		TownBuyBoardDto dto = new TownBuyBoardDto();
		MemberDto mdto = mdao.find(getSession(request, "userId"));

		dto.setTo_id(getSession(request, "userId"));
		dto.setTo_category(request.getParameter("to_category"));
		
		if (mdto.getPremium().equals("pro")) {
			dto.setTo_premium("1");
		} else {
			dto.setTo_premium("0");
		}
		
		dto.setTo_nickname(getSession(request,"nickName"));
		dto.setTo_title(request.getParameter("to_title"));
		dto.setTo_content(request.getParameter("to_content"));
		dto.setTo_profileImg(getSession(request, "pic"));
		dto.setTo_productImg(request.getParameter("to_files"));
		dto.setTo_tag(request.getParameter("pie_tagsOutput"));
		dto.setTo_address(mdto.getAddress_main());
		dto.setTo_personnelMax(Integer.parseInt(request.getParameter("to_personnelMax")));
		dto.setTo_pricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setTo_priceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setTo_ip(request.getRemoteAddr());
		dto.setTo_deadLine(request.getParameter("to_deadLine"));
		
		
		dao.writeDao(dto);

		return "redirect:/townBuySearch";

	}
	
	@RequestMapping("/townBuying")
	public String townBPage(HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(getSession(request, "userId"));
		String addr = mdto.getAddress_main().substring(0,6);

		List<TownBuyBoardDto> list = dao.listLocal(addr);
		model.addAttribute("list", list);
		model.addAttribute("now",mdto.getAddress_main());
		model.addAttribute("Mapi",kakaoMapApiKey);
		
		return "pieContents/townBuying/townBuyMain";
	}
	
	
	
	private String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for (String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}

	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}
	
}
