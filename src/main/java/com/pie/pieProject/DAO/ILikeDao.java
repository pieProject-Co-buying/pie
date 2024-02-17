package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILikeDao {
	public int checkLike(String id, String boardNum, String tableName);
	public void LikePlus(String id, String boardNum, String tableName);
	public void LikeMinus(String id, String boardNum, String tableName);
	public void countLike(String boardNum, String tableName, String column1, String column2);
	public int getP(String num);
	public int getT(String num);

}
