package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberAuthDao {
	public void joinUser(String id);
	public String findAuth(String id);
	public void deleteAuth(String id);
}
