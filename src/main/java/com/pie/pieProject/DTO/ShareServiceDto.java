package com.pie.pieProject.DTO;

public class ShareServiceDto extends BoardDto {
	
	private String url; //링크

	public String getUrl() {
		this.url = "shareServiceProduct?num="+getNum();
		return this.url;
	}

}