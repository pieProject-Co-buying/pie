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

import com.pie.pieProject.DAO.ITownBuyBoardDao;

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

		String sId = request.getParameter("id");

		dao.updateDao(request.getParameter("to_title"), request.getParameter("to_content"), sId);
		System.out.println(request.getParameter("to_title"));
		System.out.println(request.getParameter("to_content"));
		System.out.println(sId);

		return "redirect:/townBuyproduct?id=" + sId;

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
		System.out.println(request.getParameter("to_deadLine"));
		System.out.println(request.getParameter("to_category"));
		System.out.println(request.getParameter("to_title"));
		System.out.println(request.getParameter("to_content"));
		System.out.println(request.getParameter("to_price"));
		System.out.println(request.getParameter("to_personnelMax"));

		dao.writeDao(request.getParameter("to_category"), request.getParameter("to_title"),
				request.getParameter("to_content"), request.getParameter("to_price"),
				request.getParameter("to_personnelMax"), request.getParameter("to_deadLine"));

		return "redirect:/townBuySearch";

	}

}
