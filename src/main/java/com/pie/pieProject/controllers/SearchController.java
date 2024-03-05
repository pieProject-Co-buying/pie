package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyApplyDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.ISearchDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	@Autowired
	IMemberDao mdao;	

	@GetMapping("/searchProducts")
	public String search(HttpServletRequest request, Model model) {
		String townKeyword = request.getParameter("townKeyword");
		String category = request.getParameter("category");
		String table = request.getParameter("table");
		System.out.println(table);
		
		
		
		//로그인한 유저의 find 메소드를 활용해서 정보를 가지고 온다
		MemberDto mdto = mdao.find(getSession(request, "userId")); //현재 로그인한 유저
		String useraddr = mdto.getAddress_main();
		String userMainAddr = useraddr.substring(0, 6);
		System.out.println(userMainAddr);
		
		

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
				
				
				
				List<TownBuyBoardDto> list = tdao.searchDao(townKeyword, userMainAddr);
				List<TownBuyBoardDto> templist;
				
				

				if(category==null||category.equals("")) {
					model.addAttribute("list", list);
				}else {
					templist = tdao.searchCateDao(townKeyword,category, userMainAddr);
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
	
	

	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}
}
