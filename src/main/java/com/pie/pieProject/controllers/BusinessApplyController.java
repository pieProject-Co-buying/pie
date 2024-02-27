
package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IBusinessApplyDao;
import com.pie.pieProject.DTO.BusinessApplyDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BusinessApplyController {
	
	/*
	 * @Autowired IBusinessApplyDao dao;
	 */
	@RequestMapping("/businessApplyForm")
	public String apply(HttpServletRequest request, Model model) {
		
		/*
		 * BusinessApplyDto dto = new BusinessApplyDto();
		 * 
		 * dto.setBus_name(request.getParameter("bus_name")); //회사명
		 * dto.setBus_num(request.getParameter("bus_num")); //사업자번호
		 * dto.setBus_adress(request.getParameter("bus_adress")); //회사주소
		 * dto.setBus_maxqnt(Integer.parseInt(request.getParameter("bus_maxqnt"))); //공구
		 * 수량
		 * dto.setBus_unitPrice(Integer.parseInt(request.getParameter("bus_unitPrice")))
		 * ; //단가 dto.setBus_chargePerson(request.getParameter("bus_chargePerson"));
		 * //담당자 dto.setBus_phone(request.getParameter("bus_phone")); //전화번호
		 * dto.setBus_img(request.getParameter("bus_img"));
		 * dto.setBus_password(request.getParameter("bus_password"));
		 * dto.setBus_productName(request.getParameter("bus_productName"));
		 * 
		 * dao.bus_apply(dto);
		 */
		
		return "pieContents/businessApply/businessApplyForm";
	}
	
}

