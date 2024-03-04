package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISearchDao {
	public List<String> bestKeyword(String table);
	public List<String> KeywordAll();
	public int findkey(String id, String tableN);
	public void upHit(String id, String tableN);
	public void inputNew(String id, String tableN);
	public List<String> searchT(String key);
	public List<String> searchP(String key);
	
	public void makeFeed(String id, String category1,String category2,String category3);
	public List<String> getCategories();
	
}
