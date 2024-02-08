package com.pie.pieProject.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor

public class TownBuyBoardDto {
	
	private String to_num;
	private String to_id;
	private String to_nickname;
	private String to_premium;
	private String to_category;
	private String to_title;
	private String to_content;
	private String to_profileImg;
	private String to_productImg;
	private Integer to_hit;
	private String to_tag;
	private String to_address;
	private String to_process;
	private String to_registDay;
	private String to_updateDay;
	private Integer to_deadString;
	private Integer to_personnelMax;
	private String to_personnelNow;
	private String to_ip;
	
}
