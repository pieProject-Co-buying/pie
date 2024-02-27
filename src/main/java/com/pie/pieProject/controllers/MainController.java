package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

@Controller
public class MainController {
	@Autowired
	IProxyBuyDao pdao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ILikeDao ldao;
	@Autowired
	BoardComp Bcomp;

	@RequestMapping("/")
	public String mainPage(Model model) {

		List<ProxyBuyBoardDto> plist = pdao.listDaoByFavorite();

		for (ProxyBuyBoardDto dto : plist) {
			dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));
			if (dto.getPr_tag() == null || dto.getPr_tag().equals("#")) {
				dto.setPr_tags(null);
			} else {
				dto.setPr_tags(Bcomp.setArraysData(dto.getPr_tag(), "#"));
			}
			dto.setPr_category(Bcomp.translate(dto.getPr_category()));
		}
		plist.get(0).setPr_content(Bcomp.parsingHtml(plist.get(0).getPr_content()));
				
		model.addAttribute("list", plist);

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
	
	@RequestMapping("/test")
	public String test() {
		 return "pieContents/proxyBuying/test";
	}
}
