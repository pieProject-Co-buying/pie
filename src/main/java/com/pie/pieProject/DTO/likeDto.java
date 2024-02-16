package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class likeDto {
	private String likingId;
	private boolean likeHeart;
	private int boardNum;
	private String boardTable;
}
