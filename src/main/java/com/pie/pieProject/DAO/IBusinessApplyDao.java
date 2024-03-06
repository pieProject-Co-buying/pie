package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;

import com.pie.pieProject.DTO.BusinessApplyDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

@Mapper
public interface IBusinessApplyDao {
	
	
	//글쓰기
	public int saveApply(BusinessApplyDto dto);
	
	//리스트보기
	public List<BusinessApplyDto> applyBoard();
	
	//상세보기
	public List<BusinessApplyDto> applyBoardDetail(String sId);
	
	
	//삭제
	public int deleteDao(String sId);	
	
	
	//수정
	public int updateDao(BusinessApplyDto dto);
	
	
	
	//페이징 관련 메소드
	public int getTotalItems();
	
    
    public List<BusinessApplyDto> getApplyBoardByPage(@Param("start") int start, @Param("pageSize") int pageSize);
	
}
