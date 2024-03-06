package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

@Mapper
public interface IParticipateCheckDao {
//	참여체크
	public void participate(String boardNum, String tableName, String writer);
//	쉐어서비스에서 글을 작성할경우
	public void participateShare(String boardNum, String tableName, String writer);

	public List<TownBuyBoardDto> getTownboard(String sId);

	public List<String> getDate(String sId, String tableName);
	
	public List<MemberDto> getPartiMem(String num, String tablename);
	public int getPartiMemNum(String num, String string);
}
