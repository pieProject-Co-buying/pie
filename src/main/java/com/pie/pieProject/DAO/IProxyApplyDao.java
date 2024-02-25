package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.ProxyApplyBoardDto;

@Mapper
public interface IProxyApplyDao {
	public List<ProxyApplyBoardDto> listDaoByNewer();

	public ProxyApplyBoardDto getView(String num);

	public void insertProxyBoard(ProxyApplyBoardDto dto);

	public void updateProxyBoard(ProxyApplyBoardDto dto);

	public void deleteProxyBoard(String num);
	
	public void updateState(ProxyApplyBoardDto dto);

}
