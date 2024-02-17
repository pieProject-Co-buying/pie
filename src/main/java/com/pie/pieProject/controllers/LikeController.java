package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.ILikeDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LikeController {
	@Autowired
	ILikeDao ldao;
	
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
		}

		if (ldao.checkLike(getSession(request, "userId"), num, table) > 0) {
			ldao.LikeMinus(getSession(request, "userId"), num, table);
		} else {
			ldao.LikePlus(getSession(request, "userId"), num, table);
		}
		ldao.countLike(num, table, prefix+"like", prefix+"num");
		
		int result = 0;
		if(table.equals("proxyBuyBoard")) {
			result = ldao.getP(num);
		}else if(table.equals("townBuyBoard")){
			result = ldao.getT(num);
		}
		
		return ResponseEntity.ok(result);
	}
	
	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}
}
