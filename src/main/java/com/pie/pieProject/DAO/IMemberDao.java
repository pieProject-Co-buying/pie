package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;

@Mapper
public interface IMemberDao {
	public String getSalt(String id);
	public MemberDto login(String id, String password);
	public int join(MemberDto mem);
	public MemberDto find(String id);
	public void sub(String id);
	public void deleteMember(String id);
	public void updateMember(MemberDto mem);
	public void unSub(String id);
}