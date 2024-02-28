package com.pie.pieProject.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pie.pieProject.DTO.FeedDto;

@Mapper
public interface IFeedDao {
	public void makeFeed(FeedDto feed);
	public FeedDto getFeed(String id);
	public void resetFeed(String id);
}
