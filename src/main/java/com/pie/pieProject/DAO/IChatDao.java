package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.TownBuyBoardDto;
import com.pie.pieProject.DTO.chatDto;

import java.util.Date;
import java.util.List;





@Mapper
public interface IChatDao {
	
	
	//방생성
	public int createRoomDao(chatDto dto);
	

	
	
}