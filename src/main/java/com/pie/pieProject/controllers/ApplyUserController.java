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
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
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
	public String proxyuploadAction(@RequestParam("pr_title") String title, @RequestParam("pr_content") String content,
			@RequestParam("pr_files") String pictures, @RequestParam(value = "pr_URL", required = false) String url,
			@RequestParam("pr_category") String pr_category, HttpServletRequest request) {

		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();

		System.out.println(title);
		System.out.println(pr_category);
		System.out.println(content);
		System.out.println(pictures);
		System.out.println(url);

		if (url == null) {
			dto.setPr_URL("");
		}

		dto.setPr_id(Bcomp.getSession(request, "userId"));
		dto.setPr_category(pr_category);
		dto.setPr_nickname(Bcomp.getSession(request, "nickName"));
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_profileImg(Bcomp.getSession(request, "pic"));
		dto.setPr_productImg(pictures);

		dto.setPr_ip(request.getRemoteAddr());
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
			dto.setPr_category(Bcomp.translate(dto.getPr_category()));
			dto.setPr_process(Bcomp.setProcess(dto.getPr_process()));
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
		
		int process = Integer.parseInt(dto.getPr_process());
		
		dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));
		dto.setPr_process(Bcomp.setProcess(dto.getPr_process()));
		
		
		model.addAttribute("board", dto);
		model.addAttribute("member", mdto);
		model.addAttribute("process", process);
		
		
		return "pieContents/proxyBuying/proxyBuyApplyment";
	}

	@GetMapping("/proxyApplyupdateForm")
	public String proxyUpdateForm(@RequestParam("num") String num, Model model) {
		System.out.println(num);
		ProxyApplyBoardDto dto = dao.getView(num);
		dto.setPr_productImgs(Bcomp.setArraysData(dto.getPr_productImg(), "/"));

		model.addAttribute("board", dto);
		return "/pieContents/proxyBuying/proxyApplyupdateForm";
	}

	@PostMapping("/proxyApplyFormUpdate")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("pr_title") String title,
			@RequestParam("pr_content") String content, @RequestParam("pr_files") String pictures,
			@RequestParam("pr_URL") String url, @RequestParam("pr_category") String pr_category,
			HttpServletRequest request) {
		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();

		dto.setPr_num(num);
		dto.setPr_category(pr_category);
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_productImg(pictures);
		dto.setPr_URL(url);
		dto.setPr_ip(request.getRemoteAddr());
		dao.updateProxyBoard(dto);

		return "redirect:/viewProxyApplyBoard?num=" + num;
	}

	@GetMapping("/deleteProxyApplyAction")
	public String proxyDeleteForm(@RequestParam("num") String num, Model model) {
		dao.deleteProxyBoard(num);
		return "redirect:/proxyBuyApply?page=1";
	}
	
	@PostMapping("/stateUpdateAction")
	public String proxyUpdateAction(@RequestParam("num") String num, @RequestParam("pr_statement") String pr_statement
) {
		ProxyApplyBoardDto chk = dao.getView(num);
		if(chk.getPr_process().equals("0")&&(pr_statement.equals("3")||pr_statement.equals("4"))) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getPr_process().equals("1")&&(pr_statement.equals("0")||pr_statement.equals("4"))) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getPr_process().equals("2")||chk.getPr_process().equals("4")){
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}else if(chk.getPr_process().equals("3")&&!pr_statement.equals("4")) {
			return "redirect:/viewProxyApplyBoard?num=" + num;
		}
		
		ProxyApplyBoardDto dto = new ProxyApplyBoardDto();
		
		System.out.println(num);
		System.out.println(pr_statement);

		dto.setPr_num(num);
		dto.setPr_process(pr_statement);

		dao.updateState(dto);

		return "redirect:/viewProxyApplyBoard?num=" + num;
	}
	
	

}
