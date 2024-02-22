package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IPaymentDAO {
	// 결제 로직
	public void insertPayment(PaymentDTO dto);
	// 상품 코드와 카테고리로 해당 상품 검색
	//public PaymentDTO findPay(int num,String category);
	// 상품 코드와 카테고리로 해당 상품 결제일 검색
	//public PaymentDTO selectDate(int num,String category);
	public PaymentDTO payBoard(int num,String category);
	//public PaymentDTO payCode(int num);
}
