package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pie.pieProject.DAO.IMemberDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class MemberController {
	@Autowired
	IMemberDao dao;

	@PostMapping("/loginAction")
	public String loginProcess(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		int su = dao.login(id, password);
		if(su>0) {
			HttpSession session = request.getSession(true);
			session.setAttribute("userId", id);
			return "redirect:/";
		}else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/logout")
	public String logoutProcess(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

}
