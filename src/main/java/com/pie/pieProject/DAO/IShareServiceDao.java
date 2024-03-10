package com.pie.pieProject.DAO;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IShareServiceDao {
	
	// 전체 게시물 조회
	public ArrayList<ShareServiceDto> getBoardList();
	// 해당 게시물 조회, 해당 게시물 수정 페이지 이동
	public ShareServiceDto selectBoard(int num);
	// 해당 게시물 수정
	public void updateBoard(ShareServiceDto dto);
	// 해당 게시물 삭제
	public int deleteBoard(int num);
	// 제목,내용 기반 검색
	public ArrayList<ShareServiceDto> searchTitle(String category,String keyword);
	// 제목,내용 기반 검색
	public ArrayList<ShareServiceDto> searchBoard(String keyword);
	// 카테고리 검색
	public ArrayList<ShareServiceDto> searchCategory(String category);
	// 게시물 작성
	public void insertBoard(ShareServiceDto dto);
	// 내가 쓴 게시물 조회
	public ArrayList<ShareServiceDto> myBoard(String keyword);
	// 게시판 순서 정렬
	public ArrayList<ShareServiceDto> completePay(int num);
	// 현재 인원 증가
	public void updateNow(int num);
	// 최대인원 여부
	public void maxChk(int num);
	// 최소인원 여부
	public void minChk(int num);
	// 진행 여부
	public void stopChk(int num);	
	// 조회수 증가
	public void updateHit(String num);
	// 게시물 정렬
	public ArrayList<ShareServiceDto> desc();
	// 구매자 이름 및 아이디로 검색
	public ArrayList<ShareServiceDto> searchBuyer(String keyword);
	// 환불 시 현재 인원 감소
	public void refundNowPerson(int num);
	// 기간만료
	public void dateOver(int num);
	// 0시마다 마감 체크
	public void expireShareServiceBoard();
}
