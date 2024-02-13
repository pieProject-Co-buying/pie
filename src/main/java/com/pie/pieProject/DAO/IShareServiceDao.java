package com.pie.pieProject.DAO;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IShareServiceDao {
	
	public ArrayList<ShareServiceDto> getBoardList();
	public ShareServiceDto selectBoard(int num);
	public void updateBoard(ShareServiceDto dto);
	public int deleteBoard(int num);
}
