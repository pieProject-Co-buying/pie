package com.pie.pieProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String mainPage() {
		return "Index";
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "pieContents/members/login_form";
	}
	
	@RequestMapping("/join")
	public String joinPage() {
		return "pieContents/members/join_form";
	}
	
	@RequestMapping("/shareService")
	public String shareSPage() {
		return "pieContents/shareService/shareServiceMain";
	}
	
	/*
	 * @RequestMapping("/shareServiceBoard") public String shareSBoardPage() {
	 * return "pieContents/shareServiceBoard"; }
	 */
	
	/*
	 * @RequestMapping("/shareServiceApply") public String shareSApplyPage() {
	 * return "pieContents/shareServiceApply"; }
	 */
	
	@RequestMapping("/townBuying")
	public String townBPage() {
		return "pieContents/townBuying/townBuyMain";
	}
	/*
	 * @RequestMapping("/townBuySearch") public String townBSearchPage() { return
	 * "pieContents/townbuySearch"; }
	 */
	
	@RequestMapping("/townBuyingCategory")
	public String townBCateogrizePage() {
		return "pieContents/townBuying/townBuyingCategory";
	}
	
	@RequestMapping("/townBuySearchResult")
	public String townBResultPage() {
		return "pieContents/townBuying/townBuySearchResult";
	}
	
	@RequestMapping("/townBuyResult")
	public String townBApplyResultPage() {
		return "pieContents/townBuying/townBuyResult";
	}
	
	/*
	 * @RequestMapping("/townBuyproduct") public String townBProductPage() { return
	 * "pieContents/townBuyproduct"; }
	 */
	
	@RequestMapping("/proxyBuyMain")
	public String proxyBPage() {
		return "pieContents/proxyBuying/proxyBuyMain";
	}
	
	@RequestMapping("/proxyBuyApply")
	public String proxyBApplyPage() {
		return "pieContents/proxyBuying/proxyBuyApply";
	}
	
	@RequestMapping("/proxyBuyBest")
	public String proxyBBestPage() {
		return "pieContents/proxyBuying/proxyBuyBest";
	}
	
	/*
	 * @RequestMapping("/proxyBuyProducts") public String proxyBProductsPage() {
	 * return "pieContents/proxyBuying/proxyBuyProducts"; }
	 */
	/*****************같이 씁시다 페이지*****************/
	@RequestMapping("/shareServiceProduct")
	public String shareSProductPage() {
		return "pieContents/shareService/shareServiceProduct";
	}
	@RequestMapping("/finish")
	public String shareSFinishPage() {
		return "pieContents/shareService/shareServiceFinish";
	}
	@RequestMapping("/townForm")
	public String townFormPage() {
		return "pieContents/townBuying/townForm";
	}
}
