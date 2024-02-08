package com.pie.pieProject.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PieCustomErrorController implements ErrorController {

	private String VIEW_PATH = "/errors/";

	@RequestMapping(value = "/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());
			model.addAttribute("errorCode", statusCode);
			return VIEW_PATH + "errorPage";
		}
		return "error";
	}
}
