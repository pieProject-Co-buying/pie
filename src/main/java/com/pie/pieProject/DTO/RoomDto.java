package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomDto {
	
	
	private int roomNumber;
	private String roomName;
	private String partyMem;
	private String[] partyMems;
	
}
