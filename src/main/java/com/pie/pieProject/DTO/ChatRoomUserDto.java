package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomUserDto {
	private int roomNumber;
	private String joinMemNum;
	private String nickname;
}
