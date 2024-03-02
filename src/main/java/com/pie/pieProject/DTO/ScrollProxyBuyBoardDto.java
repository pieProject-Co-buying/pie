package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ScrollProxyBuyBoardDto {
	private String pr_num; // 게시글 번호
	private String pr_category; // 게시글 번호
	private String pr_title; // 게시글 번호
	private String pr_productImg; // 게시글 내 이미지
	private int pr_hit; // 조회수
	private String pr_process; // 모집 진행 여부
	private String pr_registDay; // 게시글 등록날짜
	private String pr_updateDay; // 게시글 수정날짜
	private String pr_deadLine; // 모집 마감일자
	private int pr_personnelMax; // 모집 마감일자
	private int pr_personnelNow; // 모집 마감일자
	private int pr_like; // 게시글 좋아요
	private boolean pr_soon;
	private String pr_updateinfo;
}
