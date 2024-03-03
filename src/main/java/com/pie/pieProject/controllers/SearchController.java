package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IProxyApplyDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.ISearchDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
	@Autowired
	ITownBuyBoardDao tdao;
	@Autowired
	IProxyBuyDao pdao;
	@Autowired
	ISearchDao sdao;
	@Autowired
	BoardComp bcomp;

	@GetMapping("/searchProducts")
	public String search(HttpServletRequest request, Model model) {
		String townKeyword = request.getParameter("townKeyword");
		String category = request.getParameter("category");
		String table = request.getParameter("table");
		System.out.println(table);

		int food = 0;
		int baby = 0;
		int beautyAndFashion = 0;
		int pet = 0;
		int life = 0;
		int etc = 0;

		if (table != null&&!table.equals("")) {

			if (table.equals("t")) {
				
				if(sdao.findkey(townKeyword,"townBuy")==0) {
					System.out.println(townKeyword);
					sdao.inputNew(townKeyword, "townBuy");
				}else {
					sdao.upHit(townKeyword, "townBuy");
				}
				
				List<TownBuyBoardDto> list = tdao.searchDao(townKeyword);;
				List<TownBuyBoardDto> templist;

				if(category==null||category.equals("")) {
					model.addAttribute("list", list);
				}else {
					templist = tdao.searchCateDao(townKeyword,category);
					for(TownBuyBoardDto b : templist) {
						b.setTo_category(bcomp.translate(b.getTo_category()));
					}
					
					model.addAttribute("list", templist);
				}

				for (TownBuyBoardDto b : list) {
					String c = b.getTo_category();
					if (c.equals("food"))
						food++;
					else if (c.equals("baby"))
						baby++;
					else if (c.equals("beautyAndFashion"))
						beautyAndFashion++;
					else if (c.equals("pet"))
						pet++;
					else if (c.equals("life"))
						life++;
					else if (c.equals("etc"))
						etc++;
					b.setTo_category(bcomp.translate(b.getTo_category()));
				}
				
				model.addAttribute("key",townKeyword);
				model.addAttribute("food", food);
				model.addAttribute("baby", baby);
				model.addAttribute("beautyAndFashion", beautyAndFashion);
				model.addAttribute("pet", pet);
				model.addAttribute("life", life);
				model.addAttribute("etc", etc);

				return "pieContents/townBuying/townBuySearchResult";
			}else if(table.equals("p")){
				
				if(sdao.findkey(townKeyword,"proxyBuy")==0) {
					sdao.inputNew(townKeyword, "proxyBuy");
				}else {
					sdao.upHit(townKeyword, "proxyBuy");
				}
				
				List<ProxyBuyBoardDto> list = pdao.searchDao(townKeyword);
				List<ProxyBuyBoardDto> templist;
				
				if(category==null||category.equals("")) {
					model.addAttribute("list", list);
				}else {
					templist = pdao.searchCateDao(townKeyword,category);
					for(ProxyBuyBoardDto b : templist) {
						b.setPr_category(bcomp.translate(b.getPr_category()));
					}
					model.addAttribute("list", templist);
				}

				for (ProxyBuyBoardDto b : list) {
					String c = b.getPr_category();
					if (c.equals("food"))
						food++;
					else if (c.equals("baby"))
						baby++;
					else if (c.equals("beautyAndFashion"))
						beautyAndFashion++;
					else if (c.equals("pet"))
						pet++;
					else if (c.equals("life"))
						life++;
					else if (c.equals("etc"))
						etc++;

				}
				
				model.addAttribute("key",townKeyword);
				model.addAttribute("food", food);
				model.addAttribute("baby", baby);
				model.addAttribute("beautyAndFashion", beautyAndFashion);
				model.addAttribute("pet", pet);
				model.addAttribute("life", life);
				model.addAttribute("etc", etc);
				
				return "pieContents/proxyBuying/proxyBuySearchResult";
			}
			
		}
		return "redirect:/";
	}
}
