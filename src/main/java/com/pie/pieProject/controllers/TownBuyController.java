package com.pie.pieProject.controllers;


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
				
		return "pieContents/townBuySearch";
		
		
	
	}
	
	
}
                                                                          