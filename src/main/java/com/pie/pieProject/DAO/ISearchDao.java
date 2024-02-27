package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISearchDao {
	public List<String> bestKeyword(String table);
	public int findkey(String id, String tableN);
	public void upHit(String id, String tableN);
	public void inputNew(String id, String tableN);
}
