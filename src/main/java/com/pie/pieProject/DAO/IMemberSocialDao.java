package com.pie.pieProject.DAO;



import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.MemberDto;

@Mapper
public interface IMemberSocialDao {
	public int join(MemberDto mem);
	public MemberDto find(String id);
}
