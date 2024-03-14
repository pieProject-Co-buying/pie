package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.BoardDto;
import com.pie.pieProject.DTO.ProxyApplyBoardDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ScrollProxyBuyBoardDto;

@Mapper
public interface IProxyBuyDao {
	public List<BoardDto> listDaoBoard();
	
	public List<ProxyBuyBoardDto> listDao();

	public List<ProxyBuyBoardDto> listDaoByNewer();

	public List<ProxyBuyBoardDto> listDaoByNewerNumber(int num);
	
	public List<ScrollProxyBuyBoardDto> listDaoByNext(int page,int max);

	public List<ProxyBuyBoardDto> listDaoByCategory(String category);

	public List<ProxyBuyBoardDto> listDaoByCategoryNumber(String category, int num);
	
	public List<ProxyBuyBoardDto> listDaoByFavorite();
	//검색
	public List<ProxyBuyBoardDto> searchDao(String townKeyword);

	public ProxyBuyBoardDto getView(String num);

	public void insertProxyBoard(ProxyBuyBoardDto dto);

	public void updateHit(String num);
	
	public void updateProxyBoard(ProxyBuyBoardDto dto);
	
	public void deleteProxyBoard(String num);
	
	public void updateNow(int num);
	
	public void maxChk(int num);
	
	// 환불 시 현재 인원 감소
	public void refundNowPerson(String num);
	
	public int chkFeed(String id);

	public List<ProxyBuyBoardDto> searchCateDao(String townKeyword, String category);
	
	public void expireProxyBuyBoard();
}
