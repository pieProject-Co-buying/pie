package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class likeDto {
	private String likingId; // 좋아요한 ID
	private int boardNum; // 좋아요한 게시글 번호
	private String boardTable; // 좋아요한 게시글이 포함된 테이블 이름
}
