
package com.pie.pieProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IBusinessApplyDao;
import com.pie.pieProject.DTO.BusinessApplyDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BusinessApplyController {

	@Autowired
	IBusinessApplyDao dao;

	@RequestMapping("/businessApplyForm")
	public String apply(HttpServletRequest request, Model model) {



		return "pieContents/businessApply/businessApplyForm";
	}

	
	@RequestMapping("/busApply")
	public String saveApply(HttpServletRequest request, Model model,
			@RequestParam(value = "bus_title") String bus_title,
			@RequestParam(value = "bus_content") String bus_content,
			@RequestParam(value = "bus_img") String bus_img,
			@RequestParam(value = "bus_name") String bus_name,
			@RequestParam(value = "bus_num") String bus_num,
			@RequestParam(value = "postCode") String postCode,
			@RequestParam(value = "address_main") String address_main,			
			@RequestParam(value = "address_sub") String address_sub,			
			@RequestParam(value = "bus_productName") String bus_productName,
			@RequestParam(value = "bus_maxqnt") Integer bus_maxqnt,
			@RequestParam(value = "bus_unitPrice") Integer bus_unitPrice,
			@RequestParam(value = "bus_chargePerson") String bus_chargePerson,
			@RequestParam(value = "bus_phone") String bus_phone,
			@RequestParam(value = "bus_email") String bus_email,
			@RequestParam(value = "bus_password") String bus_password
			

			) {


		
		
		 BusinessApplyDto dto = new BusinessApplyDto();
		 

		
		 
		 //내용
		 dto.setBus_title(bus_title); 
		 dto.setBus_content(bus_content);
		 dto.setBus_img(bus_img);

		 
		 
		 
		 //제품정보
		 dto.setBus_productName(bus_productName);
		 dto.setBus_maxqnt(bus_maxqnt); 		 
		 dto.setBus_unitPrice(bus_unitPrice);
		 
		 
		 //회사정보
		 dto.setBus_name(bus_name);  //회사이름
		 dto.setBus_num(bus_num); //사업자등록번호
		 dto.setPostCode(postCode);
		 dto.setAddress_main(address_main);
		 dto.setAddress_sub(address_sub);
		 
		 
		 
		 //담당자정보
		 dto.setBus_chargePerson(bus_chargePerson);
		 dto.setBus_email(bus_email);
		 dto.setBus_phone(bus_phone);
		 
		 
		 //비밀번호
		 dto.setBus_password(bus_password);
		 
		 
		 dao.saveApply(dto);
		 
		 
		 System.out.println(dto);
		 
		 return "redirect:/businessApplyBoard";

	}
	
	/*
	 * @RequestMapping("/businessApplyBoard") public String readApply(Model model) {
	 * 
	 * model.addAttribute("list", dao.applyBoard());
	 * 
	 * 
	 * 
	 * return "pieContents/businessApply/businessApplyBoard"; }
	 */


	// 페이지네이션 추가	
	@RequestMapping("/businessApplyBoard")
	public String readApply(Model model, @RequestParam(name = "page", defaultValue = "1") int page) { 
	    int pageSize = 10; // 페이지당 항목 수
	    int totalItems = dao.getTotalItems(); // 전체 항목 수를 가져오는 메서드 필요
	    int totalPages = (int) Math.ceil((double) totalItems / pageSize); // 총 페이지 수 계산
	    int start = (page - 1) * pageSize; // 시작 레코드 인덱스 계산
	    
	    List<BusinessApplyDto> list = dao.getApplyBoardByPage(start, pageSize); // 현재 페이지의 항목을 가져오는 메서드 필요
	    
	    model.addAttribute("list", list);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);
	    
	    return "pieContents/businessApply/businessApplyBoard";
	}
	
	
	
	
	@RequestMapping("/readApplyBoard")
	public String readApplydetail(Model model, HttpServletRequest request, @RequestParam("bus_apply_num") int bus_apply_num) {
		
		String sId = request.getParameter("bus_apply_num");
		
		
		List<BusinessApplyDto> dto = dao.applyBoardDetail(sId);
		model.addAttribute("board", dto);
		 
		return "pieContents/businessApply/readApplyBoard";
		
		
	}
	
	
	
	@RequestMapping("/deletebusApply")
	public String delete(HttpServletRequest request) {

		String sId = request.getParameter("bus_apply_num");
		dao.deleteDao(sId);

		return "redirect:/businessApplyBoard";

	}
	
	
	@RequestMapping("/businessApplyUpdateForm")
	public String updateApply(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page) {	
		String sId = request.getParameter("bus_apply_num");
		List<BusinessApplyDto> dto = dao.applyBoardDetail(sId);
		model.addAttribute("board", dto);
		
		return "pieContents/businessApply/businessApplyUpdateForm";
	}

	
	@RequestMapping("/businessApplyBoard/page/{pageNum}")
	public String goToPage(Model model, @PathVariable("pageNum") int pageNum) {
	    return "redirect:/businessApplyBoard?page=" + pageNum;
	}
	
	
	
	@RequestMapping("/busApplyUpdate")
	public String update(HttpServletRequest request,
			@RequestParam("bus_apply_num") String bus_apply_num,
			@RequestParam("bus_title") String bus_title,
			@RequestParam("bus_content") String bus_content,
			@RequestParam("bus_img") String bus_img,
			@RequestParam("bus_name") String bus_name,
			@RequestParam("bus_num") String bus_num,
			@RequestParam("postCode") String postCode,
			@RequestParam("address_main") String address_main,			
			@RequestParam("address_sub") String address_sub,	
			@RequestParam("bus_productName") String bus_productName,
			@RequestParam("bus_maxqnt") Integer bus_maxqnt,
			@RequestParam("bus_unitPrice") Integer bus_unitPrice,
			@RequestParam("bus_chargePerson") String bus_chargePerson,
			@RequestParam("bus_phone") String bus_phone,
			@RequestParam("bus_email") String bus_email,
			@RequestParam("bus_password") String bus_password
			) {

		
		 BusinessApplyDto dto = new BusinessApplyDto();
		 
		
		 dto.setBus_title(bus_title); 
		 dto.setBus_content(bus_content);
		 dto.setBus_img(bus_img);

		 
		 
		 //제품정보
		 dto.setBus_productName(bus_productName);
		 dto.setBus_maxqnt(bus_maxqnt); 		 
		 dto.setBus_unitPrice(bus_unitPrice);
		 
		 
		 //회사정보
		 dto.setBus_name(bus_name);  //회사이름
		 dto.setBus_num(bus_num); //사업자등록번호
		 dto.setPostCode(postCode);
		 dto.setAddress_main(address_main);
		 dto.setAddress_sub(address_sub);
		 
		 
		 
		 //담당자정보
		 dto.setBus_chargePerson(bus_chargePerson);
		 dto.setBus_email(bus_email);
		 dto.setBus_phone(bus_phone);
		 
		 
		 //비밀번호
		 dto.setBus_password(bus_password);
		 
		 dto.setBus_apply_num(Integer.parseInt(bus_apply_num));
		 	
		 
		 dao.updateDao(dto);
		
		
		return "redirect:/businessApplyBoard";
	}


	
	
}
