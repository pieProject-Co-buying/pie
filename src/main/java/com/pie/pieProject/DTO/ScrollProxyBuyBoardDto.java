package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ScrollProxyBuyBoardDto {
	private String num; // 게시글 번호
	private String category; // 게시글 번호
	private String title; // 게시글 번호
	private String productImg; // 게시글 내 이미지
	private int hit; // 조회수
	private String process; // 모집 진행 여부
	private String registDay; // 게시글 등록날짜
	private String updateDay; // 게시글 수정날짜
	private String deadLine; // 모집 마감일자
	private int personnelMax; // 모집 마감일자
	private int personnelNow; // 모집 마감일자
	private int like; // 게시글 좋아요
	private boolean soon;
	private String updateinfo;
}
