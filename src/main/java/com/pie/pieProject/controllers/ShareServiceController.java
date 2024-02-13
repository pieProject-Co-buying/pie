package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.ShareServiceDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShareServiceController {
	 @Autowired
	 IShareServiceDao dao;
	
	 @RequestMapping("/boardList") 
	 public String showBoard(HttpServletRequest request,Model model){ 
		 String sId=request.getParameter("sh_numID");
		 model.addAttribute("board", dao.selectBoard(Integer.parseInt(sId)));
		 return "pieContents/shareServiceProduct"; 
	 }
	 
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(Model model){ 
		model.addAttribute("list", dao.getBoardList());
		return "pieContents/shareServiceBoard";
	}
	@RequestMapping("/writePost")
	public String witePost(){ 
		return "pieContents/shareServiceWrite";
	}
	@RequestMapping("/modifyForm")
	public String modify(HttpServletRequest request,Model model){ 
		String sId=request.getParameter("sh_numID");
		model.addAttribute("board", dao.selectBoard(Integer.parseInt(sId)));
		return "pieContents/shareServiceModify";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,Model model){
		String sId=request.getParameter("sh_numID");
		int id = Integer.parseInt(sId);
		dao.deleteBoard(id);
		return "redirect:/shareServiceBoard";
	}
	@RequestMapping("/updateBoard")
	public String update(HttpServletRequest request,Model model) {
		ShareServiceDto dto=new ShareServiceDto();
		String sh_numID=request.getParameter("sh_numID");
		String sh_title=request.getParameter("sh_title");
		String sh_content=request.getParameter("sh_content");
		String sh_price=request.getParameter("sh_price");
		String sh_personnelMax=request.getParameter("sh_personnelMax");
		String sh_DeadLine=request.getParameter("sh_DeadLine");
		
		System.out.println(sh_numID);
		System.out.println(sh_title);
		System.out.println(sh_content);
		System.out.println(sh_price);
		System.out.println(sh_personnelMax);
		System.out.println(sh_DeadLine);
		int tempNumId = Integer.parseInt(sh_numID);
		
		dto.setSh_numID(tempNumId);
		dto.setSh_title(sh_title);
		dto.setSh_content(sh_content);
		dto.setSh_price(Integer.parseInt(sh_price));
		dto.setSh_personnelMax(Integer.parseInt(sh_personnelMax));
		dto.setSh_DeadLine(sh_DeadLine);
		
		dao.updateBoard(dto);
		
		return "redirect:/boardList?sh_numID="+sh_numID;
	}
}
