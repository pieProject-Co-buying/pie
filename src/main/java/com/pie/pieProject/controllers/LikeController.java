package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DTO.BoardDto;
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

		if (tableName.equals("p")) {
			table = "proxyBuyBoard";
		}else if(tableName.equals("t")) {
			table = "townBuyBoard";
		}else if(tableName.equals("s")) {
			table = "shareServiceBoard";
		}

		if (ldao.checkLike(bcomp.getSession(request, "userId"), num, table) > 0) {
			ldao.LikeMinus(bcomp.getSession(request, "userId"), num, table);
		} else {
			ldao.LikePlus(bcomp.getSession(request, "userId"), num, table);
		}
		ldao.countLike(num, table);
		
		int result = ldao.getLike(table, num);
		System.out.println("num:"+num);
		System.out.println("result:"+result);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/likeList")
	public String likeList(@RequestParam(value = "category", required = false) String category, HttpServletRequest request, Model model) {
		
		String id = bcomp.getSession(request, "userId");
		String table = "";
		List<BoardDto> list = new ArrayList<>();
		System.out.println(category);
		
		if(category!=null&&!category.equals("")&&!category.equals("all")) {
			if(category.equals("town")) {
				table = "townBuyBoard";
				list = bcomp.setTURL(ldao.likeListById(table, id));
			}
			else if(category.equals("proxy")) {
				 table = "proxyBuyBoard";
				 list = bcomp.setPURL(ldao.likeListById(table, id));
			}
			else if(category.equals("share")) {
				table = "shareServiceBoard";
				 list = bcomp.setSURL(ldao.likeListById(table, id));
			}
		}else if(category==null||category.equals("")||category.equals("all")) {
			list.addAll(bcomp.setTURL(ldao.likeListById("townBuyBoard", id)));
			list.addAll(bcomp.setPURL(ldao.likeListById("proxyBuyBoard", id)));
			list.addAll(bcomp.setSURL(ldao.likeListById("shareServiceBoard", id)));
		}
		
		model.addAttribute("list",bcomp.translateList(list));
		
		return "/pieContents/members/likeList";
	}	
}
