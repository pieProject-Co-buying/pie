package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProxyApplyBoardDto extends BoardDto{
	@Getter
	@Setter
	private String chkDay; // 게시글 수정날짜
	@Getter
	@Setter
	private String url; //링크
	
}
