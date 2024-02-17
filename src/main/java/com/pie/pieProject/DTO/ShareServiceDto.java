package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareServiceDto {
	private int sh_num; // 게시물 번호
	private String sh_id; // 사용자 id
	private String sh_nickname; // 사용자 닉네임
	private String sh_title; // 게시글 제목
	private String sh_content; // 게시글 내용
	private String sh_productImg; // 게시글 이미지
	private int sh_product; // 제품 코드
	private int sh_price; // 가격
	private String sh_category; // 카테고리
	private String sh_process; // 진행현황
	private String sh_registDay; // 등록일
	private String sh_updateDay; // 수정일
	private String sh_deadLine; // 마감일
	private int sh_personnelMax; // 최대 인원
	private int sh_personnelNow; // 현재 인원
	private String sh_ip; // ip
	
	private int sh_hit; // 조회수
	private String sh_premium; // 구독회원 작성여부
	private String sh_profileImg; // 프로필 이미지
	private String sh_tag; // 태그
	private int sh_priceTotal; // 가격 총합
	private int sh_pricePer; // 인당 가격
	private String[] sh_productImgs; // 게시글 이미지 배열
	private String[] sh_tags; // 게시글 태그 배열
	private int sh_like; // 게시글 좋아요
	
}