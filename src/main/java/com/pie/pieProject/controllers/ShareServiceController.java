package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IShareServiceDao;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShareServiceController {
	@Autowired
	IShareServiceDao dao;
	
	
	 @RequestMapping("/boardList") 
	 public String showBoard(HttpServletRequest request,Model model){ 
		 String sId=request.getParameter("sh_numID");
		 model.addAttribute("board", dao.selectBoard(Integer.parseInt(sId)));
		 return "pieContents/shareServiceProduct"; 
	 }
	 
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(Model model){ 
		model.addAttribute("list", dao.getBoardList());
		System.out.println(dao.getBoardList());
		return "pieContents/shareServiceBoard";
	}
}
