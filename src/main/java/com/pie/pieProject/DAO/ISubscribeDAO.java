package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.SubScribeDTO;

@Mapper
public interface ISubscribeDAO {
	public void insertSubScribe(SubScribeDTO dto);
}
