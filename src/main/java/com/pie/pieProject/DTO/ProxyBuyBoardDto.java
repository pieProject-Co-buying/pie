package com.pie.pieProject.DTO;


public class ProxyBuyBoardDto extends BoardDto {

	private String url; //링크

	public String getUrl() {
		this.url = "proxyBuyProduct?num="+getNum();
		return this.url;
	}
}
