package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class chatDto {

	private int roomNum;
	private String sender;
	private String reciever;
	private String messages;
	
}
