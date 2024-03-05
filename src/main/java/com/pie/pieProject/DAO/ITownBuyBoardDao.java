package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.TownBuyBoardDto;

import java.util.Date;
import java.util.List;





@Mapper
public interface ITownBuyBoardDao {
	
	// 목록
	public List<TownBuyBoardDto> listDao();
	//TownBuyBoardDto 값을 list 타입으로 리턴

	
	
	// 목록 - 조회수 순
	public List<TownBuyBoardDto> bestListDao();
	//TownBuyBoardDto 값을 list 타입으로 리턴

	
	// 목록 - 좋아요 순
	public List<TownBuyBoardDto> likeListDao();
	//TownBuyBoardDto 값을 list 타입으로 리턴
		
	//프리미엄 회원 목록
	public List<TownBuyBoardDto> listPremiumDao();
	
	
	
	// 상세페이지
	public TownBuyBoardDto viewDao(String sId);
	
	//삭제
	public int deleteDao(String sId);
	
	//수정

	public int updateDao(TownBuyBoardDto dto);
	
	
	//검색
	public List<TownBuyBoardDto> searchDao(String townKeyword);
	
	//검색
	public List<TownBuyBoardDto> searchCateDao(String townKeyword, String category);
	
	//카테고리 선택
	public List<TownBuyBoardDto> categoryDao(String category);
	
	
	public List<TownBuyBoardDto> categoryDaoNum(String category, int num);
	
	
	/* public List<TownBuyBoardDto> listPremiumDao(String category, int num); */
	public List<TownBuyBoardDto> listPremiumDao(String category, int num, String addr);	
	
	public void updatePer(String num);
	
	
	public void updateTownProcess(String num);
	
	
	
	//글쓰기
	/*
	 * public int writeDao(String to_category, String to_title, String to_content,
	 * String to_price, String to_personnelMax, String to_deadline);
	 */
	
	
	public int writeDao(TownBuyBoardDto dto);
	
	public List<TownBuyBoardDto> listLocal(String local);
	
	public List<TownBuyBoardDto> listLocalActive(String local);
	
	//조회수
	public void updateHit(String num);
	// 0시마다 마감 체크
	public void expireTownBuyBoard();
	
	
	//참여자수 증가
	public void updatePersonnelNow(String num);

	//내가쓴글 보기
	public List<TownBuyBoardDto> townListbyID(String sId);
	
	
	//진행 상태 변경
	/* public void updateProcess(String sId); */

	
	
}
