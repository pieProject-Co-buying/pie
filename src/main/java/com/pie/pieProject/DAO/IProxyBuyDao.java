package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.ProxyBuyBoardDto;

@Mapper
public interface IProxyBuyDao {
	public List<ProxyBuyBoardDto> listDao();
	public List<ProxyBuyBoardDto> listDaoByNewer();
	public List<ProxyBuyBoardDto> listDaoByCategory(String category);
	public ProxyBuyBoardDto getView(String num);
	public void insertProxyBoard(ProxyBuyBoardDto dto);
	public void updateHit(String num);
	public void selectLike(String num);
	
	public int checkLike(String id, String boardNum, String tableName);
	public void LikePlus(String id, String boardNum, String tableName);
	public void LikeMinus(String id, String boardNum, String tableName);
	public void countLike(String boardNum, String tableName);
}
