package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
	private String num; // 게시물 번호
	private String id; // 작성자 ID
	private String nickname; // 작성자 닉네임
	private String premium; // 작성자 구독회원 여부
	private String category; // 게시글 카테고리
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	private String profileImg; // 게시글 작성자 이미지
	private String productImg; // 게시글 내 이미지
	private int hit; // 게시글 조회수
	private String tag; // 게시글 태그
	private String address; // 게시글 주소
	private String process; // 모집 진행현황
	private String registDay; // 게시글 작성일자
	private String updateDay; // 게시글 수정일자
	private String deadLine; // 게시글 마감일
	private int personnelMax; // 모집 최대 인원
	private int personnelNow; // 현재 모집 인원
	private String ip; // 게시글 IP
	
	private int priceTotal; // 가격 총합
	private int pricePer; // 인당 가격
	private String[] productImgs; // 게시글 내 이미지 배열
	private String[] tags; // 게시글 태그 배열
	private int likeNum; // 게시글 좋아요
	
	private String thumbnail; // 썸네일
	private String brand; // 브랜드
	private String productName; //제품명
	
	private String url; //링크
}
