package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pie.pieProject.DAO.PaymentDAO;
import com.pie.pieProject.DTO.PaymentDTO;

@RestController
public class PaymentController {
	@Autowired
	private PaymentDAO dao;
	
    @PostMapping("/payCheck")
    public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
    	dao.insertPayment(dto);
        // 받은 데이터를 처리하는 로직
        System.out.println(dto.getPayMerchant_uid());
        
        // 응답 반환
        return new ResponseEntity<>("결제 정보를 성공적으로 받았습니다.", HttpStatus.OK);
    }
}
