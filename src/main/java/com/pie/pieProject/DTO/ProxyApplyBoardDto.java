package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProxyApplyBoardDto {
	private String pr_num; // 게시글 번호
	private String pr_id; // 게시글 ID
	private String pr_nickname; // 게시글 닉네임
	private String pr_category; // 물품 카테고리
	private String pr_title; // 게시글 제목
	private String pr_content; // 게시글 내용
	private String pr_profileImg; // 게시글 작성자 프로필 이미지
	private String[] pr_productImgs; // 게시글 내 이미지배열
	private String pr_productImg; // 게시글 내 이미지
	private String pr_process; // 모집 진행 여부
	private String pr_registDay; // 게시글 등록날짜
	private String pr_updateDay; // 게시글 수정날짜
	private String pr_chkDay; // 게시글 수정날짜
	private String pr_URL; // 게시글 수정날짜
	private String pr_ip; // 게시글 작성 IP
}
