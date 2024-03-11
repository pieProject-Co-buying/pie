package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProxyApplyBoardDto {
	private String num; // 게시글 번호
	private String id; // 게시글 ID
	private String nickname; // 게시글 닉네임
	private String category; // 물품 카테고리
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	private String profileImg; // 게시글 작성자 프로필 이미지
	private String[] productImgs; // 게시글 내 이미지배열
	private String productImg; // 게시글 내 이미지
	private String process; // 모집 진행 여부
	private String registDay; // 게시글 등록날짜
	private String updateDay; // 게시글 수정날짜
	private String chkDay; // 게시글 수정날짜
	private String URL; // 게시글 수정날짜
	private String ip; // 게시글 작성 IP
	private String brand;
	private String productName;
}
