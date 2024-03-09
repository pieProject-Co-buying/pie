package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	@Autowired
	ITownBuyBoardDao tdao;
	@Value("${kakao.api.mapkey}")
	String kakaoMapApiKey;

	@RequestMapping("/")
	public String mainPage(Model model, HttpServletRequest request) {

		// 대리구매 인기순 5개
		List<ProxyBuyBoardDto> plist = Bcomp.translateProxyList(pdao.listDaoByFavorite());
		for (ProxyBuyBoardDto dto : plist) {
			dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
			if (dto.getTag() == null || dto.getTag().equals("#")) {
				dto.setTags(null);
			} else {
				dto.setTags(Bcomp.setArraysData(dto.getTag(), "#"));
			}
		}
		
		if (plist.size() != 0) {
			plist.get(0).setContent(Bcomp.parsingHtml(plist.get(0).getContent()));
		}

		model.addAttribute("list", plist);

		// 로그인한 유저의 find 메소드를 활용해서 정보를 가지고 온다
		if (getSession(request, "userId") != null && !getSession(request, "userId").equals("")) {
			MemberDto mdto = mdao.find(getSession(request, "userId")); // 현재 로그인한 유저
			String useraddr = mdto.getAddress_main();
			String userMainAddr = useraddr.substring(0, 6);
			System.out.println(userMainAddr);

			List<TownBuyBoardDto> tlist = tdao.bestListDao(userMainAddr);

			for (TownBuyBoardDto d : tlist) {
				d.setCategory(Bcomp.translate(d.getCategory()));
			}

			model.addAttribute("tlist", tlist);

			List<TownBuyBoardDto> tlist2 = tdao.likeListDao(userMainAddr);
			for (TownBuyBoardDto d : tlist2) {
				d.setCategory(Bcomp.translate(d.getCategory()));
			}

			model.addAttribute("tlist2", tlist2);

			// 지도정보
			model.addAttribute("now", mdto.getAddress_main());
			model.addAttribute("Mapi", kakaoMapApiKey);
		}

		return "Index";
	}

	/*
	 * @RequestMapping("/login") public String loginPage() { return
	 * "pieContents/members/login_form"; }
	 */

	/*
	 * @RequestMapping("/join") public String joinPage() { return
	 * "pieContents/members/join_form"; }
	 */

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

	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}
}
