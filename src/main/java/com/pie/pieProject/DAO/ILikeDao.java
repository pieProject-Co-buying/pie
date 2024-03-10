package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.BoardDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

@Mapper
public interface ILikeDao {
	public int checkLike(String id, String boardNum, String tableName);
	public void LikePlus(String id, String boardNum, String tableName);
	public void LikeMinus(String id, String boardNum, String tableName);
	public void countLike(String boardNum, String tableName);
	public int getLike(String table, String num);
	
	public List<BoardDto> likeListById(String table, String id);
	/*
	 * public List<ProxyBuyBoardDto> likePListById(String id); public
	 * List<ShareServiceDto> likeSListById(String id);
	 */
}
