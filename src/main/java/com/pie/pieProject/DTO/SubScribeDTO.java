package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SubScribeDTO {
	private int sub_num;
	private String buyer_id; // 구매자 아이디
	private String buyer_name; // 구매자 이름
	private String buyer_nickname; // 구매자 닉네임
	private String buyer_tel; // 구매자 전화번호
	private String buyer_email; // 구매자 이메일
	private String sub_uid; // 거래번호
	private String sub_customer_uid; // 구매자 고유 번호
	private String sub_method; // 결제 수단
	private String sub_merchant_uid; // 상품 고유 번호
	private String sub_name; // 상품 이름
	private int sub_amount;  // 상품 가격
	private String sub_date; // 결제일
	private String sub_premium; // 구독 여부
	
}
