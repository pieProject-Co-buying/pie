package com.pie.pieProject.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareServiceDto {
	private int sh_numID;
	private String sh_id;
	private String sh_nickname;
	private String sh_title;
	private String sh_content;
	private int sh_price;
	private String sh_category;
	private String sh_process;
	private String sh_registDay;
	private String sh_updateDay;
	private String sh_DeadLine;
	private int sh_personnelMax;
	private int sh_personnelNow;
	private String sh_ip;
}
