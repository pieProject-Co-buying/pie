package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;

@Mapper
public interface IMemberDao {
	public String getSalt(String id);
	public int login(String id, String password);
	public int join(MemberDto mem);
	public void test(String title, String content);
}
