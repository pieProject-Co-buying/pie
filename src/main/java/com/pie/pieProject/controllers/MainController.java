package com.pie.pieProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String mainPage() {
		return "Index";
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "pieContents/login_form";
	}
	
	@RequestMapping("/join")
	public String joinPage() {
		return "pieContents/join_form";
	}
}
