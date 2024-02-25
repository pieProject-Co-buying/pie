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

	/*
	 * @RequestMapping("/townBuying") public String townBPage() { return
	 * "pieContents/townBuying/townBuyMain"; }
	 */

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


	/*
	 * @RequestMapping("/townBuyproduct") public String townBProductPage() { return
	 * "pieContents/townBuyproduct"; }
	 */



	/*
	 * @RequestMapping("/proxyBuyProducts") public String proxyBProductsPage() {
	 * return "pieContents/proxyBuying/proxyBuyProducts"; }
	 */
	/***************** 같이 씁시다 페이지 *****************/
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
