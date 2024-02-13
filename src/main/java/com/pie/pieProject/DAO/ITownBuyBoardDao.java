package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.TownBuyBoardDto;

import java.util.List;





@Mapper
public interface ITownBuyBoardDao {
	
	// 목록
	public List<TownBuyBoardDto> listDao();
	//TownBuyBoardDto 값을 list 타입으로 리턴
	
	// 상세페이지
	public TownBuyBoardDto viewDao(String sId);
	
	//삭제
	public int deleteDao(String sId);
	
	//수정
	public int updateDao(String to_title, String to_content, String sId);
	
	
}
