package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;

@Controller
public class PaymentController {
	@Autowired
	private IPaymentDAO dao;
	
    @PostMapping("/payCheck")
    public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
    	System.out.println(dto.getBuyer_addr());
    	System.out.println(dto.getBuyer_email());
    	System.out.println(dto.getBuyer_name());
    	System.out.println(dto.getBuyer_nickname());
    	System.out.println(dto.getBuyer_postcode());
    	System.out.println(dto.getBuyer_tel());
    	System.out.println(dto.getPay_method());
    	
    	dao.insertPayment(dto);
    	ShareServiceDto Sdto = new ShareServiceDto();

        // 응답 반환
        return new ResponseEntity<>("결제 정보를 성공적으로 받았습니다.", HttpStatus.OK);
    }
}
