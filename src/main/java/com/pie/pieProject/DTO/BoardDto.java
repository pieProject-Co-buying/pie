package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	private String num; // 게시물 번호
	private String id; // 작성자 ID
	private String nickname; // 작성자 닉네임
	private String premium; // 작성자 구독회원 여부
	private String category; // 게시글 카테고리
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	private String profile_pic; // 게시글 작성자 이미지
	private String productImg; // 게시글 내 이미지
	private int hit; // 게시글 조회수
	private String tag; // 게시글 태그
	private String address; // 게시글 주소
	private String addr_admin; // 행정구역
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

	private String brand; // 브랜드
	private String productName; // 제품명

	private String category_ko; // 카테고리 번역
	private String thumbnail; // 썸네일


	public String getCategory_ko() {
		String category = this.category;

		if (category.equals("food"))
			this.category_ko = "식품";
		else if (category.equals("baby"))
			this.category_ko = "육아";
		else if (category.equals("beautyAndFashion"))
			this.category_ko = "뷰티/패션";
		else if (category.equals("pet"))
			this.category_ko = "반려동물";
		else if (category.equals("life"))
			this.category_ko = "생활";
		else if (category.equals("etc"))
			this.category_ko = "기타";
		else if (category.equals("OTT"))
			this.category_ko = "OTT";
		else if (category.equals("game"))
			this.category_ko = "게임";
		else if (category.equals("bookAndMusic"))
			this.category_ko = "도서/음악";
		
		return this.category_ko;
	}
	
	public String getThumbnail() {
		this.thumbnail = this.productImg.substring(0, this.productImg.indexOf("/"));
		
		return this.thumbnail;
	}
	
	public String[] getProductImgs() {
		this.productImgs = setArraysData(this.productImg, "/");
		return this.productImgs;
	}
	
	public String[] getTags() {
		this.tags = setArraysData(this.tag, "#");
		return this.tags;
	}
	
	public String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for (String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}
}
