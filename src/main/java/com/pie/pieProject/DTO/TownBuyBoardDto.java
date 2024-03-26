package com.pie.pieProject.DTO;


public class TownBuyBoardDto extends BoardDto{

	private String url; //링크

	public String getUrl() {
		this.url = "townBuyproduct?num="+getNum();
		return this.url;
	}
}
