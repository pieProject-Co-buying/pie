package com.pie.pieProject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberDto {
	String mem_num;
	String id;
	String password;
	String name;
	String nickname;
	String gender;
	String profile_pic;
	String email;
	String phone;
	String postCode;
	String address_main;
	String address_sub;
	boolean agreement;
	String friends;
}
