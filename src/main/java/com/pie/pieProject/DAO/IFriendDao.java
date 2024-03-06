package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;

@Mapper
public interface IFriendDao {
//	팔로우 체크
	public int checkFollow(String me, String you);

//	팔로잉	
	public void Following(String me, String you);

//	언팔로우
	public void unFollow(String me, String you);

//	팔로우리스트
	public List<MemberDto> friendsList(String me);
	
//	탈퇴자 삭제
	public void outMemberFollow(String id);
}
