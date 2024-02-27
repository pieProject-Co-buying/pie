package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IFriendDao;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FriendsController {
	@Autowired
	IFriendDao dao;
	@Autowired
	BoardComp bcomp;
	
	@PostMapping("/Follwing")
	public ResponseEntity<Boolean> following(@RequestParam("you") String you, HttpServletRequest request){
		String me = bcomp.getSession(request, "userId");

		if(me.equals(you)){
			return ResponseEntity.ok(false);
		}
		
		if(dao.checkFollow(me, you)>0){
			dao.unFollow(me, you);
		}else if (dao.checkFollow(me, you)==0){
			dao.Following(me, you);
			return ResponseEntity.ok(true);
		}
		
		return ResponseEntity.ok(false);
	}
}
