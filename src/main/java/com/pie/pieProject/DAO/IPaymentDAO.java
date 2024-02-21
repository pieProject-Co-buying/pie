package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.PaymentDTO;

@Mapper
public interface IPaymentDAO {

	public void insertPayment(PaymentDTO dto);
	//public void ProxyPayment(ProxyBuyBoardDto Pdto);
	public PaymentDTO findPay(String pay);
}
