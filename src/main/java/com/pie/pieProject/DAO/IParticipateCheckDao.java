package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IParticipateCheckDao {
//	참여체크
	public void participate(String boardNum, String tableName, String writer);
}
