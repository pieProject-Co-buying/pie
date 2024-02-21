package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProxyBuyController {
	@Autowired
	IProxyBuyDao dao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ILikeDao ldao;

	public static String UPLOAD_DIRECTORY2 = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\imgs\\test";

	@GetMapping("/proxyBuyProducts")
	public String getList(Model model) {
		List<ProxyBuyBoardDto> list1 = dao.listDaoByNewerNumber(3);
		List<ProxyBuyBoardDto> list2 = dao.listDaoByCategoryNumber("test", 3);

		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		model.addAttribute("allList", dao.listDao());
		return "pieContents/proxyBuying/proxyBuyProducts";
	}

	@GetMapping("/viewProxyBoard")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(getSession(request, "userId"));
		
		System.out.println(num);
		dao.updateHit(num);
		System.out.println("chkpoint1");
		
		ProxyBuyBoardDto dto = dao.getView(num);
		System.out.println("chkpoint2");
		dto.setPr_productImgs(setArraysData(dto.getPr_productImg(), "/"));
		dto.setPr_tags(setArraysData(dto.getPr_tag(), "#"));

		String table = "proxyBuyBoard";
		if (ldao.checkLike(getSession(request, "userId"), num, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		System.out.println("chkpoint3");

		model.addAttribute("board", dto);
		model.addAttribute("member",mdto);
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

		dto.setPr_id(getSession(request, "userId"));
		dto.setPr_category(pr_category);
		dto.setPr_nickname(getSession(request, "nickName"));
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_profileImg(getSession(request, "pic"));
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
		dto.setPr_productImgs(setArraysData(dto.getPr_productImg(), "/"));
		dto.setPr_tags(setArraysData(dto.getPr_tag(), "#"));
		model.addAttribute("board",dto);
		return "/pieContents/proxyBuying/proxyupdateForm";
	}
	
	@PostMapping("/updateProxyAction")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("pr_title") String title, @RequestParam("pr_content") String content,
			@RequestParam("pr_files") String pictures, @RequestParam("pie_tagsOutput") String tags,
			@RequestParam("pr_deadLine") String deadLine, @RequestParam("pr_personnelMax") String pr_personnelMax,
			@RequestParam("price_total") String pr_priceTotal, @RequestParam("price_per") String pr_pricePer,
			@RequestParam("pr_category") String pr_category, HttpServletRequest request) {
		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();

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

		return "redirect:/viewProxyBoard?num="+num;
	}
	@GetMapping("/deleteProxyAction")
	public String proxyDeleteForm(@RequestParam("num") String num, Model model){
		dao.deleteProxyBoard(num);
		return "redirect:/proxyBuyProducts";
	}

	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}

	private String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for (String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}
}
