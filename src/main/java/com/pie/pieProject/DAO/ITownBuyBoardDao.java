package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.TownBuyBoardDto;

import java.util.Date;
import java.util.List;





@Mapper
public interface ITownBuyBoardDao {
	
	// 목록
	public List<TownBuyBoardDto> listDao();
	//TownBuyBoardDto 값을 list 타입으로 리턴
	
	
	//프리미엄 회원 목록
	public List<TownBuyBoardDto> listPremiumDao();
	
	
	
	// 상세페이지
	public TownBuyBoardDto viewDao(String sId);
	
	//삭제
	public int deleteDao(String sId);
	
	//수정
	public int updateDao(String to_title, String to_content, String sId);
	
	
	//검색
	public List<TownBuyBoardDto> searchDao(String townKeyword);
	
	
	//카테고리 선택
	public List<TownBuyBoardDto> categoryDao(String category);
	
	
	//글쓰기
	public int writeDao(String to_category, String to_title, String to_content, String to_price, String to_personnelMax, String to_deadline);
	
	
}
