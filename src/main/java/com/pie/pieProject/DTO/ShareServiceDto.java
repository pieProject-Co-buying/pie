package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareServiceDto {
	private int sh_numID; // 게시물 번호
	private String sh_id; // 사용자 id
	private String sh_nickname; // 사용자 닉네임
	private String sh_title; // 게시글 제목
	private String sh_content; // 게시글 내용
	private String sh_filename; // 첨부 파일 이름
	private int sh_product; // 제품 코드
	private int sh_price; // 가격
	private String sh_category; // 카테고리
	private String sh_process; // 진행현황
	private String sh_registDay; // 등록일
	private String sh_updateDay; // 수정일
	private String sh_DeadLine; // 마감일
	private int sh_personnelMax; // 최대 인원
	private int sh_personnelNow; // 현재 인원
	private String sh_ip; // ip
}