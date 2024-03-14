package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.BoardDto;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

@Mapper
public interface IParticipateCheckDao {
//	참여체크
	public void participate(String boardNum, String tableName, String writer);
//	쉐어서비스에서 글을 작성할경우
	public void participateShare(String boardNum, String tableName, String writer);

	public List<BoardDto> getTownboard(String sId);

	public List<String> getDate(String sId, String tableName);
	
	public List<MemberDto> getPartiMem(String num, String tablename);
	
	public int getPartiMemNum(String num, String string);
//	참여 여부 확인
	public int chkPartiMem(String id, String tablename , String num);
//	참여 취소
	public  void cancelBuying(String id, String tablename , String num);
//	이전 참여 취소 여부
	public int canceledBuying(String id, String tablename , String num);
	
}
