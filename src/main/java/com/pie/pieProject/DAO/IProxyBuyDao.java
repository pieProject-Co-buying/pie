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
	public void insertProxyBoard(String title, String content);
}
