package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
	private String buyer_id; // 구매자 아이디
	private String buyer_name; // 구매자 이름
	private String buyer_nickname; // 구매자 닉네임
	private String buyer_tel; // 구매자 전화번호
	private String buyer_addr; // 구매자 주소
	private String buyer_email; // 구매자 이메일
	private String buyer_postcode; // 구매자 우편번호
	private String pay_uid; // 고유번호
    private String pay_method; // 결제수단
    private String pay_Merchant_uid; // 제품 코드
    private String pay_name; // 제품 이름
    private String pay_amount; // 제품 가격
    private String pay_num; // 게시물 번호
    private String pay_category; // 카테고리
    private String pay_date; // 구매일자
    private int pay_refund; // 환불여부
    
    private String productImg;
    private String process;
    private String num;
}