package com.pie.pieProject.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IFeedDao;
import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.ISearchDao;
import com.pie.pieProject.DTO.FeedDto;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ScrollProxyBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProxyBuyController {
	@Autowired
	IProxyBuyDao dao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ILikeDao ldao;
	@Autowired
	BoardComp Bcomp;
	@Autowired
	ISearchDao sdao;
	@Autowired
	IFeedDao fdao;
	@Autowired
	IParticipateCheckDao paDao;

	@RequestMapping("/proxyBuyMain")
	public String proxyBPage(Model model) {
		model.addAttribute("bestKey", sdao.bestKeyword("proxyBuy"));
		return "pieContents/proxyBuying/proxyBuyMain";
	}

	@RequestMapping("/proxyBuyBest")
	public String proxyBBestPage(Model model) {
		List<ProxyBuyBoardDto> list = dao.listDaoByFavorite();

		for (ProxyBuyBoardDto dto : list) {
			dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));
			if (dto.getPr_tag() == null || dto.getPr_tag().equals("#")) {
				dto.setPr_tags(null);
			} else {
				dto.setPr_tags(Bcomp.setArraysData(dto.getPr_tag(), "#"));
			}
		}

		model.addAttribute("list", list);
		return "pieContents/proxyBuying/proxyBuyBest";
	}

	@GetMapping("/redirectChkFeed")
	public String redirectChkFeed() {
		return "redirect:/chkFeed";
	}

	@GetMapping("/chkFeed")
	public String chkFeed(Model model, HttpServletRequest request) {
		if (Bcomp.getSession(request, "userId") == null || Bcomp.getSession(request, "userId").equals("")) {
			return "redirect:/proxyBuyProducts";
		}
		if (dao.chkFeed(Bcomp.getSession(request, "userId")) > 0) {
			return "redirect:/proxyBuyProducts";
		}

		List<String> list1 = sdao.KeywordAll();
		model.addAttribute("list", list1);

		return "pieContents/proxyBuying/proxyBuySelectFeed";
	}

	@GetMapping("/resetFeed")
	public String resetFeed(HttpServletRequest request) {
		fdao.resetFeed(Bcomp.getSession(request, "userId"));
		return "redirect:/chkFeed";
	}

	@GetMapping("/feedAnalize")
	public String feedAnalize(@RequestParam("feedCategory") String tags, HttpServletRequest request) {
		String[] tag;
		if (tags == null || tags.equals("#") || tags.equals("")) {
			tag = null;
		} else {
			tag = Bcomp.setArraysData(tags, "#");
		}

		List<String> analizeCate = new ArrayList<>();

		for (String s : tag) {
			analizeCate.addAll(sdao.searchT(s));
			analizeCate.addAll(sdao.searchP(s));
		}

		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String str : analizeCate) {
			Integer count = map.get(str);
			if (count == null) {
				map.put(str, 1);
			} else {
				map.put(str, count + 1);
			}
		}
		
		System.out.println("카테고리 개수 : "+map.size());

		List<String> keySet = new ArrayList<>(map.keySet());
		// Value 값으로 오름차순 정렬
		keySet.sort((o1, o2) -> map.get(o2).compareTo(map.get(o1)));
		for (String key : keySet) {
			System.out.print("Key : " + key);
			System.out.println(", Val : " + map.get(key));
		}
		

		FeedDto feed = new FeedDto();
		feed.setId(Bcomp.getSession(request, "userId"));
		
		String[] set = {"none", "none", "none"};
		
		int max = 3;
		if(keySet.size()<=3) {
			max = keySet.size();
		}
		for(int i = 0; i<max; i++) {
			if(keySet.get(i)!=null) set[i] = keySet.get(i);
		}
		
		feed.setFeed1(set[0]);
		feed.setFeed2(set[1]);
		feed.setFeed3(set[2]);

		fdao.makeFeed(feed);

		return "redirect:/proxyBuyProducts";
	}

	@GetMapping("/noneFeed")
	public String setNoneFeed(HttpServletRequest request) {
		FeedDto feed = new FeedDto();
		feed.setId(Bcomp.getSession(request, "userId"));
		feed.setFeed1("none");
		feed.setFeed2("none");
		feed.setFeed3("none");
		fdao.makeFeed(feed);

		return "redirect:/proxyBuyProducts";
	}

	@GetMapping("/proxyBuyProducts")
	public String getList(HttpServletRequest request, Model model) {
		FeedDto dto = null;
		if (Bcomp.getSession(request, "userId") != null && !Bcomp.getSession(request, "userId").equals("")) {
			dto = fdao.getFeed(Bcomp.getSession(request, "userId"));
		}

		if (dto == null
				|| (dto.getFeed1().equals("none") && dto.getFeed2().equals("none") && dto.getFeed3().equals("none"))) {
			List<ProxyBuyBoardDto> list1 = dao.listDaoByNewerNumber(10);

			Bcomp.translateProxyList(list1);
			
			model.addAttribute("text1" , Bcomp.listText("now"));
			model.addAttribute("list1", list1);
			model.addAttribute("haveFeed", false);
		} else {
			List<ProxyBuyBoardDto> list1 = dao.listDaoByCategoryNumber(dto.getFeed1(), 10);
			List<ProxyBuyBoardDto> list2 = dao.listDaoByCategoryNumber(dto.getFeed2(), 10);
			List<ProxyBuyBoardDto> list3 = dao.listDaoByCategoryNumber(dto.getFeed3(), 10);

			Bcomp.translateProxyList(list1);
			Bcomp.translateProxyList(list2);
			Bcomp.translateProxyList(list3);

			model.addAttribute("list1", list1);
			model.addAttribute("list2", list2);
			model.addAttribute("list3", list3);
			
			model.addAttribute("text1" , Bcomp.listText(dto.getFeed1()));
			model.addAttribute("text2" , Bcomp.listText(dto.getFeed2()));
			model.addAttribute("text3" , Bcomp.listText(dto.getFeed3()));
			model.addAttribute("haveFeed", true);
		}

		/*
		 * List<ProxyBuyBoardDto> allList = dao.listDaoByNewerNumber(8);
		 * 
		 * 
		 * Bcomp.translateProxyList(allList);
		 * 
		 * model.addAttribute("allList", allList);
		 */
		 
		return "pieContents/proxyBuying/proxyBuyProducts";
	}
	
	@PostMapping("/infiniteLoading")
	public ResponseEntity<List<ScrollProxyBuyBoardDto>> getpagingList (@RequestParam("page")String page){
		int p = Integer.parseInt(page);
		p = ((p-1)*8);
		int m = p+8;
		
		List<ScrollProxyBuyBoardDto> list = dao.listDaoByNext(p,m);
		for(ScrollProxyBuyBoardDto d : list) {
			d.setPr_soon(Bcomp.closeClosely(d));
			d.setPr_updateinfo(Bcomp.lastUpdateMessage(d.getPr_updateDay()));
			d.setPr_productImg(d.getPr_productImg().substring(0, d.getPr_productImg().indexOf('/')));
			d.setPr_category(Bcomp.translate(d.getPr_category()));
		}
		
		return ResponseEntity.ok(list);
	}
	

	@GetMapping("/viewProxyBoard")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));

		System.out.println(num);
		dao.updateHit(num);
		System.out.println("chkpoint1");

		ProxyBuyBoardDto dto = dao.getView(num);
		System.out.println("chkpoint2");
		dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));
		if (dto.getPr_tag() == null || dto.getPr_tag().equals("#")) {
			dto.setPr_tags(null);
		} else {
			dto.setPr_tags(Bcomp.setArraysData(dto.getPr_tag(), "#"));
		}

		String table = "proxyBuyBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), num, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		System.out.println("chkpoint3");
		
		List<MemberDto> list = paDao.getPartiMem(num, "Proxy");
		
		
		model.addAttribute("list",Bcomp.translateProxyList(dao.listDaoByNewerNumber(10)));
		model.addAttribute("partiMem", list);
		model.addAttribute("partiMemTotal", list.size());
		model.addAttribute("board", dto);
		model.addAttribute("member", mdto);
		return "pieContents/proxyBuying/proxyBuyProduct";
	}

	@GetMapping("/proxyWriteForm")
	public String proxyWriteForm() {
		return "pieContents/proxyBuying/proxyForm";
	}

	@PostMapping("/uploadAction")
	public String proxyuploadAction(@RequestParam("pr_title") String title, @RequestParam("pr_content") String content,
			@RequestParam("pr_files") String pictures, @RequestParam("pie_tagsOutput") String tags,
			@RequestParam("pr_deadLine") String deadLine, @RequestParam("pr_personnelMax") String pr_personnelMax,
			@RequestParam("price_total") String pr_priceTotal, @RequestParam("price_per") String pr_pricePer,
			@RequestParam("pr_category") String pr_category, HttpServletRequest request) {

		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();

		System.out.println(tags);
		dto.setPr_id(Bcomp.getSession(request, "userId"));
		dto.setPr_category(pr_category);
		dto.setPr_nickname(Bcomp.getSession(request, "nickName"));
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_profileImg(Bcomp.getSession(request, "pic"));
		dto.setPr_productImg(pictures);
		dto.setPr_tag(tags);
		dto.setPr_deadLine(deadLine);
		dto.setPr_personnelMax(Integer.parseInt(pr_personnelMax));
		dto.setPr_priceTotal(Integer.parseInt(pr_priceTotal));
		dto.setPr_pricePer(Integer.parseInt(pr_pricePer));
		dto.setPr_ip(request.getRemoteAddr());
		dao.insertProxyBoard(dto);

		return "redirect:/proxyBuyProducts";
	}

	@GetMapping("/updateProxyForm")
	public String proxyUpdateForm(@RequestParam("num") String num, Model model) {
		System.out.println(num);
		ProxyBuyBoardDto dto = dao.getView(num);
		dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));
		if (dto.getPr_tag() == null || dto.getPr_tag().equals("#")) {
			dto.setPr_tags(null);
		} else {
			dto.setPr_tags(Bcomp.setArraysData(dto.getPr_tag(), "#"));
		}
		model.addAttribute("board", dto);
		return "/pieContents/proxyBuying/proxyupdateForm";
	}

	@PostMapping("/updateProxyAction")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("pr_title") String title,
			@RequestParam("pr_content") String content, @RequestParam("pr_files") String pictures,
			@RequestParam("pie_tagsOutput") String tags, @RequestParam("pr_deadLine") String deadLine,
			@RequestParam("pr_personnelMax") String pr_personnelMax, @RequestParam("price_total") String pr_priceTotal,
			@RequestParam("price_per") String pr_pricePer, @RequestParam("pr_category") String pr_category,
			HttpServletRequest request) {
		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();

		System.out.println(tags);
		dto.setPr_num(num);
		dto.setPr_category(pr_category);
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_productImg(pictures);
		dto.setPr_tag(tags);
		dto.setPr_deadLine(deadLine);
		dto.setPr_personnelMax(Integer.parseInt(pr_personnelMax));
		dto.setPr_priceTotal(Integer.parseInt(pr_priceTotal));
		dto.setPr_pricePer(Integer.parseInt(pr_pricePer));
		dto.setPr_ip(request.getRemoteAddr());
		dao.updateProxyBoard(dto);

		return "redirect:/viewProxyBoard?num=" + num;
	}

	@GetMapping("/deleteProxyAction")
	public String proxyDeleteForm(@RequestParam("num") String num, Model model) {
		dao.deleteProxyBoard(num);
		return "redirect:/proxyBuyProducts";
	}

	// Function to calculate and format the time difference
}
