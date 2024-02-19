package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {
	private String pay_uid; // 고유번호
    private String payMethod; // 결제수단
    private String payMerchant_uid; // 제품 코드
    private String payName; // 제품 이름
    private double payAmount; // 제품 가격
   // private String payDate; // 구매일자
}
