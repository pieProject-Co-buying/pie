package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyApplyDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyApplyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ApplyUserController {
	@Autowired
	IMemberDao mdao;
	@Autowired
	IProxyApplyDao dao;
	@Autowired
	BoardComp Bcomp;

	@GetMapping("/proxyApplyForm")
	public String proxyApplyForm() {
		return "pieContents/proxyBuying/proxyBuyApplyForm";
	}

	@PostMapping("/proxyApplyFormUpload")
	public String proxyuploadAction(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("fileStr") String pictures, @RequestParam(value = "url", required = false) String url,
			@RequestParam("category") String category, @RequestParam("brand") String brand,  @RequestParam("productName") String productName, HttpServletRequest request) {

		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();

		System.out.println(title);
		System.out.println(category);
		System.out.println(content);
		System.out.println(pictures);
		System.out.println(url);
		System.out.println(brand);
		System.out.println(productName);

		if (url == null||url.equals("")) {
			dto.setUrl("");
		}

		dto.setId(Bcomp.getSession(request, "userId"));
		dto.setCategory(category);
		dto.setNickname(Bcomp.getSession(request, "nickName"));
		dto.setTitle(title);
		dto.setContent(content);
		dto.setProfile_pic(Bcomp.getSession(request, "pic"));
		dto.setProductImg(pictures);
		dto.setUrl(url);
		dto.setBrand(brand);
		dto.setProductName(productName);

		dto.setIp(request.getRemoteAddr());
		dao.insertProxyBoard(dto);

		return "redirect:/proxyBuyApply?page=1";
	}

	@RequestMapping("/proxyBuyApply")
	public String proxyBApplyPage(@RequestParam("page") int page, Model model) {
		List<ProxyApplyBoardDto> allList = dao.listDaoByNewer();

		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) allList.size() / pageLimit);
		if(pageNum<=0) pageNum=1;

		for (ProxyApplyBoardDto dto : allList) {
			dto.setCategory(Bcomp.translate(dto.getCategory()));
			dto.setProcess(Bcomp.setProcess(dto.getProcess()));
		}

		List<ProxyApplyBoardDto> list = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, allList.size());
		System.out.println(minPage);
		System.out.println(maxPage);

		for (int i = minPage; i < maxPage; i++) {
			list.add(allList.get(i));
		}

		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "pieContents/proxyBuying/proxyBuyApply";
	}

	@GetMapping("/viewProxyApplyBoard")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));
		System.out.println("넘버:"+num);
		
		ProxyApplyBoardDto dto = dao.getView(num);

		
		int process = Integer.parseInt(dto.getProcess());
		
		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));
		dto.setProcess(Bcomp.setProcess(dto.getProcess()));
		dto.setCategory(Bcomp.translate(dto.getCategory()));
		
		
		model.addAttribute("board", dto);
		model.addAttribute("member", mdto);
		model.addAttribute("process", process);
		
		
		return "pieContents/proxyBuying/proxyBuyApplyment";
	}

	@GetMapping("/proxyApplyupdateForm")
	public String proxyUpdateForm(@RequestParam("num") String num, Model model) {
		System.out.println(num);
		ProxyApplyBoardDto dto = dao.getView(num);
		dto.setProductImgs(Bcomp.setArraysData(dto.getProductImg(), "/"));

		model.addAttribute("board", dto);
		return "/pieContents/proxyBuying/proxyApplyupdateForm";
	}

	@PostMapping("/proxyApplyFormUpdate")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("fileStr") String pictures,
			@RequestParam("url") String url, @RequestParam("category") String category, @RequestParam("brand") String brand,  @RequestParam("productName") String productName,
			HttpServletRequest request) {
		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();

		dto.setNum(num);
		dto.setCategory(category);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setProductImg(pictures);
		dto.setUrl(url);
		dto.setIp(request.getRemoteAddr());
		dto.setBrand(brand);
		dto.setProductName(productName);
		dao.updateProxyBoard(dto);

		return "redirect:/viewProxyApplyBoard?num=" + num;
	}

	@GetMapping("/deleteProxyApplyAction")
	public String proxyDeleteForm(@RequestParam("num") String num, Model model) {
		dao.deleteProxyBoard(num);
		return "redirect:/proxyBuyApply?page=1";
	}
	
	@PostMapping("/stateUpdateAction")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("statement") String statement
) {
		ProxyApplyBoardDto chk = dao.getView(num);
		if(chk.getProcess().equals("0")&&(statement.equals("3")||statement.equals("4"))) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getProcess().equals("1")&&(statement.equals("0")||statement.equals("4"))) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getProcess().equals("2")||chk.getProcess().equals("4")){
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getProcess().equals("3")&&!statement.equals("4")) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}
		
		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();
		
		System.out.println(num);
		System.out.println(statement);

		dto.setNum(num);
		dto.setProcess(statement);

		dao.updateState(dto);

		return "redirect:/viewProxyApplyBoard?num=" + num;
	}
	
	

}
