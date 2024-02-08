package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDao {
	public int login(String id, String password);
	
	
}
