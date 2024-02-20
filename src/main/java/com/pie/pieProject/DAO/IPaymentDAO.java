package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.PaymentDTO;

@Mapper
public interface IPaymentDAO {

	public void insertPayment(PaymentDTO dto);
}
