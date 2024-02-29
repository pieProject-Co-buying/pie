
package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessApplyDto {
	
	private int bus_apply_num;
	
	//입력부분
	private String bus_title;
	private String bus_content;
	private String bus_img;
	
	private String bus_name;
	private String bus_num;
	private String postCode;
	private String address_main;
	private String address_sub;
	
	private String bus_productName;
	private int bus_maxqnt;
	private int bus_unitPrice;

	
	private String bus_chargePerson;
	private String bus_phone;
	private String bus_email;
	
	
	private String bus_password;

	
	
	
	private String bus_writeDay;
	
	private int bus_hit;
	
}
