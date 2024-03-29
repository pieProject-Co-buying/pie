package com.pie.pieProject.DAO;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IPaymentDAO {
	// 전체 결제 내역
	public ArrayList<PaymentDTO> paymentList();

	// 결제 로직
	public void insertPayment(PaymentDTO dto);

	// 아이디, 닉네임 기반 검색
	public ArrayList<PaymentDTO> searchBuyer(String keyword);

	// 구매 목록 출력
	public ArrayList<PaymentDTO> buyList(String id, String category);
	
	// 구매 목록 출력
	public ArrayList<PaymentDTO> buyListbyId(String table, String id, String category);

	// 상품 코드와 카테고리로 검색
	public PaymentDTO payBoard(String num, String category);

	// 구매 상품 이미지 가져오기
	public ArrayList<String> buyListpic(String id, String category, String tableName, String prefix);

	// 구매 상품 현황 가져오기
	public ArrayList<String> buyListpro(String process, String category, String tableName, String prefix);

	// 구매 상품 넘버 가져오기
	public ArrayList<String> buyListNum(String num, String category, String tableName, String prefix);

	// 환불 신청
	public int refundPay(String num);

	// 환불 여부
	public int refundPayCheck(String num);

	// 전체 환불
	public int allRefund(int num);
	public int allProcessRefund(int num);
	
	// 아이디, 보드넘버, 테이블종류로 검색
	public PaymentDTO myPay(String id, String num, String table);

}
