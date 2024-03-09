package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LikeController {
	@Autowired
	ILikeDao ldao;
	@Autowired
	BoardComp bcomp;
	
	@PostMapping("/updateHeart")
	public ResponseEntity<Integer> updateHeartAction(@RequestParam("num") String num,
			@RequestParam("tableName") String tableName, HttpServletRequest request) {

		String table = null;
		String prefix = null;

		if (tableName.equals("p")) {
			table = "proxyBuyBoard";
			prefix = "pr_";
		}else if(tableName.equals("t")) {
			table = "townBuyBoard";
			prefix = "to_";
		}else if(tableName.equals("s")) {
			table = "shareServiceBoard";
			prefix = "sh_";
		}

		if (ldao.checkLike(bcomp.getSession(request, "userId"), num, table) > 0) {
			ldao.LikeMinus(bcomp.getSession(request, "userId"), num, table);
		} else {
			ldao.LikePlus(bcomp.getSession(request, "userId"), num, table);
		}
		ldao.countLike(num, table, prefix+"like", prefix+"num");
		
		int result = ldao.getLike(prefix+"like", table, prefix+"num", num);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/likeList")
	public String likeList(@RequestParam(value = "category", required = false) String category, HttpServletRequest request, Model model) {
		
		String id = bcomp.getSession(request, "userId");
		if(category.equals("town")) {
			List<TownBuyBoardDto> list = ldao.likeTListById(id);
			for(TownBuyBoardDto d : list) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			model.addAttribute("list1",list);
			model.addAttribute("table","town");
		}else if(category.equals("proxy")) {
			List<ProxyBuyBoardDto> list = ldao.likePListById(id);
			for(ProxyBuyBoardDto d : list) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			model.addAttribute("list1",list);
			model.addAttribute("table","proxy");
		}else if(category.equals("share")) {
			List<ShareServiceDto> list = ldao.likeSListById(id);
			for(ShareServiceDto d : list) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			model.addAttribute("list1",list);
			model.addAttribute("table","share");
		}else if(category.equals("all")) {
			List<TownBuyBoardDto> list1 = ldao.likeTListById(id);
			for(TownBuyBoardDto d : list1) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			
			System.out.println("list1 : "+list1.size());
			List<ProxyBuyBoardDto> list2 = ldao.likePListById(id);
			for(ProxyBuyBoardDto d : list2) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			System.out.println("list2 : "+list2.size());
			List<ShareServiceDto> list3 = ldao.likeSListById(id);
			for(ShareServiceDto d : list3) {
				d.setCategory(bcomp.translate(d.getCategory()));
			}
			System.out.println("list3 : "+list3.size());
			model.addAttribute("list1",list1);
			model.addAttribute("list2",list2);
			model.addAttribute("list3",list3);
			model.addAttribute("table","all");
		}
		
		return "/pieContents/members/likeList";
	}	
}
