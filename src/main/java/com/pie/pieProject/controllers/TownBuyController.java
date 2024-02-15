package com.pie.pieProject.controllers;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TownBuyController {

	@Autowired
	ITownBuyBoardDao dao;

	@RequestMapping("/townBuySearch")
	public String toBoardList(HttpServletRequest request, Model model) {

		model.addAttribute("list", dao.listDao());

		/*
		 * String sP = request.getParameter("to_premium");
		 * model.addAttribute("premiumList", dao.listPremiumDao());
		 */

		return "pieContents/townBuySearch";

	}

	@RequestMapping("/townBuyproduct")
	public String toBoardView(HttpServletRequest request, Model model) {

		String sId = request.getParameter("id");
		model.addAttribute("list", dao.viewDao(sId));

		return "pieContents/townBuyproduct";

	}

	@RequestMapping("/updateTownProduct")

	public String update(HttpServletRequest request) {
		

	    
	    
		TownBuyBoardDto dto = new TownBuyBoardDto();
		
		System.out.println(request.getParameter("to_category"));
		System.out.println(request.getParameter("to_title"));
		System.out.println(request.getParameter("to_content"));
		System.out.println(request.getParameter("to_price"));
		System.out.println(request.getParameter("to_personnelMax"));
		System.out.println(request.getParameter("to_deadLine"));
		System.out.println(request.getParameter("id"));

		
		dto.setTo_category(request.getParameter("to_category"));
		dto.setTo_title(request.getParameter("to_title"));
		dto.setTo_content(request.getParameter("to_content"));
		dto.setTo_price(request.getParameter("to_price"));
		dto.setTo_num(Integer.parseInt(request.getParameter("id")));
		/* dto.setTo_num(request.getParameter("id")); */
		dto.setTo_personnelMax(Integer.parseInt(request.getParameter("to_personnelMax")));
		dto.setTo_deadLine(request.getParameter("to_deadLine"));

		
		
		
		dao.updateDao(dto);
		
	


		return "redirect:/townBuyproduct?id=" + request.getParameter("id");

		// return "redirect:/townBuyproduct?id="+sId;
		// 리턴시 어떤 id를 갖고 있는 페이지로 갈건지 지정해주지 않아서 에러
	}

	@RequestMapping("/updateTownProductForm")
	public String updateForm(HttpServletRequest request, Model model) {

		String sId = request.getParameter("id");

		model.addAttribute("list", dao.viewDao(sId));

		return "pieContents/updateTownProductForm";

	}
	
	

	
	@RequestMapping("/deleteTownProduct")
	public String delete(HttpServletRequest request) {

		String sId = request.getParameter("id");
		dao.deleteDao(sId);

		return "redirect:/townBuySearch";

	}

	@RequestMapping("/searchTownBuy")

	public String search(HttpServletRequest request, Model model) {
		String townKeyword = request.getParameter("townKeyword");

		model.addAttribute("list", dao.searchDao(townKeyword));

		return "pieContents/townBuySearchResult";

	}

	@RequestMapping("/townBuyingCategoryChoice")

	public String category(HttpServletRequest request, Model model) {

		String category = request.getParameter("category");

		model.addAttribute("list", dao.categoryDao(category));

		return "pieContents/townBuyingCategory";

	}
	
	

	@RequestMapping("/writeTownBoard")
	public String write(HttpServletRequest request, Model model) {


		

		
		TownBuyBoardDto dto = new TownBuyBoardDto();
		

		

		dto.setTo_category(request.getParameter("to_category"));
		dto.setTo_title(request.getParameter("to_title"));
		dto.setTo_content(request.getParameter("to_content"));
		dto.setTo_price(request.getParameter("to_price"));
		dto.setTo_personnelMax(Integer.parseInt(request.getParameter("to_personnelMax")));
		dto.setTo_deadLine(request.getParameter("to_deadLine"));
		
		

		dao.writeDao(dto);

		return "redirect:/townBuySearch";

		

	}

	
	
}
