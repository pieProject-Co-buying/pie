package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberDto {
	private String mem_num;
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String gender;
	private String profile_pic;
	private String email;
	private String phone;
	private String postCode;
	private String address_main;
	private String address_sub;
	private boolean agreement;
	private String friends;
}
