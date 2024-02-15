package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProxyBuyController {
	@Autowired
	IProxyBuyDao dao;
	
	@GetMapping("/proxyBuyProducts")
	public String getList(Model model) {
		List<ProxyBuyBoardDto> list1 = new ArrayList<>();
		List<ProxyBuyBoardDto> list2 = new ArrayList<>();
		
		int max1 = 3;
		int max2 = 3;
		
		if(list1.size()<3) {
			max1=dao.listDaoByNewer().size();
			max2=dao.listDaoByCategory("육아").size();
		}
		
		
		for(int i=0; i<max1; i++) {
			list1.add(dao.listDaoByNewer().get(i));
		}
	
		
		for(int i=0; i<max2; i++) {
			list2.add(dao.listDaoByCategory("육아").get(i));
		}
		
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		model.addAttribute("allList",dao.listDao());
		return "pieContents/proxyBuyProducts"; 
	}
	
	@GetMapping("/viewProxyBoard")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		model.addAttribute("board",dao.getView(num));
		return "pieContents/proxyBuyProduct";
	}
	
	@GetMapping("/proxyWriteForm")
	public String proxyWriteForm() {
		return "pieContents/proxyForm";
	}
	
	@PostMapping("/testUpload")
	public String proxyWriteAction(@RequestParam("pr_title") String title, @RequestParam("pr_content") String content) {
		dao.insertProxyBoard(title, content);
		return "redirect:/proxyBuyProducts";
	}

}
