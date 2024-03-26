package com.pie.pieProject.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.pie.pieProject.DAO.IPaymentDAO;
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
	@Autowired
	IPaymentDAO Pdao;
	@Value("${port.shop.code}")
	String shopCode;
	

	/*
	 * @RequestMapping("/proxyBuyMain") public String proxyBPage(Model model) {
	 * return "pieContents/proxyBuying/proxyBuyMain"; }
	 */

	/*
	 * @RequestMapping("/proxyBuyBest") public String proxyBBestPage(Model model) {
	 * List<ProxyBuyBoardDto> list = dao.listDaoByFavorite();
	 * 
	 * model.addAttribute("list", list); return
	 * "pieContents/proxyBuying/proxyBuyBest"; }
	 */

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
		model.addAttribute("bestKey", sdao.bestKeyword("proxyBuy"));
		FeedDto dto = null;
		if (Bcomp.getSession(request, "userId") != null && !Bcomp.getSession(request, "userId").equals("")) {
			dto = fdao.getFeed(Bcomp.getSession(request, "userId"));
		}

		if (dto == null
				|| (dto.getFeed1().equals("none") && dto.getFeed2().equals("none") && dto.getFeed3().equals("none"))) {
			List<ProxyBuyBoardDto> list1 = dao.listDaoByNewerNumber(10);
			
			model.addAttribute("text1" , Bcomp.listText("now"));
			model.addAttribute("list1", list1);
			model.addAttribute("haveFeed", false);
		} else {
			List<ProxyBuyBoardDto> list1 = dao.listDaoByCategoryNumber(dto.getFeed1(), 10);
			List<ProxyBuyBoardDto> list2 = dao.listDaoByCategoryNumber(dto.getFeed2(), 10);
			List<ProxyBuyBoardDto> list3 = dao.listDaoByCategoryNumber(dto.getFeed3(), 10);

			
			System.out.println("list1:"+list1);
			System.out.println("list2:"+list2);
			System.out.println("list3:"+list3);
			

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
			d.setSoon(Bcomp.closeClosely(d));
			d.setUpdateinfo(Bcomp.lastUpdateMessage(d.getUpdateDay()));
			d.setProductImg(d.getProductImg().substring(0, d.getProductImg().indexOf('/')));
			d.setCategory(d.getCategory());
		}
		
		return ResponseEntity.ok(list);
	}
	

	@GetMapping("/proxyBuyProduct")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));

		System.out.println(num);
		dao.updateHit(num);
		System.out.println("chkpoint1");

		ProxyBuyBoardDto dto = dao.getView(num);
		System.out.println("chkpoint2");
		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
		if (dto.getTag() == null || dto.getTag().equals("#")) {
			dto.setTags(null);
		} else {
			dto.setTags(Bcomp.setArraysData(dto.getTag(), "#"));
		}
		dto.setCategory(dto.getCategory());

		String table = "proxyBuyBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), num, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		System.out.println("chkpoint3");
		
		List<MemberDto> list = paDao.getPartiMem(num, "Proxy");
		
		model.addAttribute("list",dao.listDaoByNewerNumber(10));
		model.addAttribute("partiMem", list);
		model.addAttribute("partiMemTotal", list.size());
		model.addAttribute("board", dto);
		model.addAttribute("member", mdto);
		
//		참여 여부 확인
		model.addAttribute("in",(paDao.chkPartiMem(Bcomp.getSession(request, "userId"), "Proxy", num)>0));
//		참여 했다가 취소 여부 확인
		model.addAttribute("cancel",(paDao.canceledBuying(Bcomp.getSession(request, "userId"), "Proxy", num)>0));
//		환불요청중
		if(Pdao.myPay(Bcomp.getSession(request, "userId"), num, "Proxy")!=null) {
			model.addAttribute("productNum",Pdao.myPay(Bcomp.getSession(request, "userId"), num, "Proxy").getPay_Merchant_uid());
		}
		
//		구분
		model.addAttribute("form","proxy");
//		결제상점
		model.addAttribute("shop",shopCode);

		
		return "pieContents/townBuying/townBuyproduct";
	}


	@PostMapping("/uploadAction")
	public String proxyuploadAction(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("pie_tagsOutput") String tags,@RequestParam("fileStr") String files,
			@RequestParam("deadLine") String deadLine, @RequestParam("personnelMax") String personnelMax,
			@RequestParam("price_total") String priceTotal, @RequestParam("price_per") String pricePer,
			@RequestParam("category") String category, @RequestParam("brand") String brand, @RequestParam("productName") String productName,  HttpServletRequest request) {

		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();

		dto.setId(Bcomp.getSession(request, "userId"));
		dto.setCategory(category);
		dto.setNickname(Bcomp.getSession(request, "nickName"));
		dto.setTitle(title);
		dto.setContent(content);
		dto.setProfile_pic(Bcomp.getSession(request, "pic"));
		dto.setProductImg(files);
		dto.setTag(tags);
		dto.setDeadLine(deadLine);
		dto.setPersonnelMax(Integer.parseInt(personnelMax));
		dto.setPriceTotal(Integer.parseInt(priceTotal));
		dto.setPricePer(Integer.parseInt(pricePer));
		dto.setBrand(brand);
		dto.setProductName(productName);
		dto.setIp(request.getRemoteAddr());
		dao.insertProxyBoard(dto);

		return "redirect:/proxyBuyProducts";
	}

	@GetMapping("/updateProxyForm")
	public String proxyUpdateForm(@RequestParam("num") String num, Model model) {
		System.out.println(num);
		ProxyBuyBoardDto dto = dao.getView(num);
		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
		if (dto.getTag() == null || dto.getTag().equals("#")) {
			dto.setTags(null);
		} else {
			dto.setTags(Bcomp.setArraysData(dto.getTag(), "#"));
		}
		model.addAttribute("board", dto);
		return "/pieContents/proxyBuying/proxyupdateForm";
	}

	@PostMapping("/updateProxyAction")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("fileStr") String pictures,
			@RequestParam("pie_tagsOutput") String tags, @RequestParam("deadLine") String deadLine,
			@RequestParam("personnelMax") String personnelMax, @RequestParam("price_total") String priceTotal,
			@RequestParam("price_per") String pricePer, @RequestParam("category") String category, @RequestParam("brand") String brand, @RequestParam("productName") String productName,
			HttpServletRequest request) {
		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();

		System.out.println(tags);
		dto.setNum(num);
		dto.setCategory(category);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setProductImg(pictures);
		dto.setTag(tags);
		dto.setDeadLine(deadLine);
		dto.setPersonnelMax(Integer.parseInt(personnelMax));
		dto.setPriceTotal(Integer.parseInt(priceTotal));
		dto.setPricePer(Integer.parseInt(pricePer));
		dto.setIp(request.getRemoteAddr());
		dto.setBrand(brand);
		dto.setProductName(productName);
		dao.updateProxyBoard(dto);

		return "redirect:/proxyBuyProduct?num=" + num;
	}

	@GetMapping("/deleteProxyAction")
	public String proxyDeleteForm(@RequestParam("num") String num, Model model) {
		dao.deleteProxyBoard(num);
		return "redirect:/proxyBuyProducts";
	}

	// Function to calculate and format the time difference
}
