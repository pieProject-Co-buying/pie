package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class TownBuyBoardDto {
	
	private Integer to_num; // 게시물 번호
	private String to_id; // 작성자 ID
	private String to_nickname; // 작성자 닉네임
	private String to_premium; // 작성자 구독회원 여부
	private String to_category; // 게시글 카테고리
	private String to_title; // 게시글 제목
	private String to_content; // 게시글 내용
	private String to_profileImg; // 게시글 작성자 이미지
	private String to_productImg; // 게시글 내 이미지
	private Integer to_hit; // 게시글 조회수
	private String to_tag; // 게시글 태그
	private String to_address; // 게시글 주소
	private String to_process; // 모집 진행현황
	private String to_registDay; // 게시글 작성일자
	private String to_updateDay; // 게시글 수정일자
	private String to_deadLine; // 게시글 마감일
	private Integer to_personnelMax; // 모집 최대 인원
	private String to_personnelNow; // 현재 모집 인원
	private String to_ip; // 게시글 IP
	
	private int to_priceTotal; // 가격 총합
	private int to_pricePer; // 인당 가격
	private String[] to_productImgs; // 게시글 내 이미지 배열
	private String[] to_tags; // 게시글 태그 배열
	private int to_like; // 게시글 좋아요
	
}
