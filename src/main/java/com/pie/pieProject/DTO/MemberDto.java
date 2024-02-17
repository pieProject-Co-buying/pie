package com.pie.pieProject.DTO;

import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MemberDto {
	private String mem_num; // 멤버 번호
	private String id; // 아이디
	private String password; // 비밀번호
	private String salt; // salt
	private String name; // 이름
	private String nickname; // 닉네임
	private String gender; // 성별
	private String profile_pic; // 프로필 이미지
	private String email; // 이메일
	private String phone; // 전화번호
	private String postCode; // 우편번호
	private String address_main; // 주소
	private String address_sub; // 세부 주소
	private boolean agreement; // 동의 여부
	private String friends; // 친구
	private ArrayList<String> friendsList; // 친구 리스트
	private String premium; // 구독회원 여부
	private Date preDate; // 구독 시작일
	private Date preEndDate; // 구독 종료일

}

