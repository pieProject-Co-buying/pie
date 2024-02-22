package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatDto {

	private int roomNum;
	private String sender;
	
	private String message;
	private String sendTime;
	
	private String roomName;
	private String roomNumber;
	
	
	private String [] messages; //주고받은 메세지 받을 배열
	

	
	
}
