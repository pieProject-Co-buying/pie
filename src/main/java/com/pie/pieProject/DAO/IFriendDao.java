package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;

@Mapper
public interface IFriendDao {
	public int checkFollow(String me, String you);
	public void Following(String me, String you);
	public void unFollow(String me, String you);
	public List<MemberDto> friendsList(String me);
}
