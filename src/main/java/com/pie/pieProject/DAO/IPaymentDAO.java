package com.pie.pieProject.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IPaymentDAO {
	//전체 결제 내역
	public ArrayList<PaymentDTO> paymentList();
	// 결제 로직
	public void insertPayment(PaymentDTO dto);
	// 아이디, 닉네임 기반 검색
	public ArrayList<PaymentDTO> searchBuyer(String keyword);
	//
	public ArrayList<PaymentDTO> buyList(String id);	
	// 상품 코드와 카테고리로 해당 상품 검색
	//public PaymentDTO findPay(int num,String category);
	// 상품 코드와 카테고리로 해당 상품 결제일 검색
	//public PaymentDTO selectDate(int num,String category);
	public PaymentDTO payBoard(int num,String category);
	//public PaymentDTO payCode(int num);
	
	public ArrayList<String> buyListpic(String id);
	public ArrayList<String> buyListpro(String id);
}
