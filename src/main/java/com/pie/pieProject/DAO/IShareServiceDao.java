package com.pie.pieProject.DAO;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pie.pieProject.DTO.ShareServiceDto;

@Mapper
public interface IShareServiceDao {
	
	public ArrayList<ShareServiceDto> getBoardList();
	public ShareServiceDto selectBoard(int num);
}
