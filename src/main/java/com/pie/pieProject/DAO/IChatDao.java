package com.pie.pieProject.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pie.pieProject.DTO.ChatDto;
import com.pie.pieProject.DTO.RoomDto;

import java.util.List;



@Mapper
public interface IChatDao {
	

	
	//메세지 저장
	public void saveMsg(@Param("roomName") String roomName, @Param("roomNumber") String roomNumber, @Param("userId") String userId, @Param("userName") String userName, @Param("message") String message);
	
	//채팅방 생성
	public void insertRoom(@Param("roomName") String roomName, @Param("roomNumber") int roomNumber, @Param("nickName") String nickName);
	
	//아이디로 채팅룸 찾기
	public List<RoomDto> roomListByID(String userId, String yourId);
	
	public RoomDto room(String userId);
	
	//dto에서 리스트 찾기
	public List<RoomDto> roomList();
	
	//방정보로 채팅리스트 가져오기
	public List<ChatDto> chatList(@Param("roomNumber") int roomNumber);
	
	public List<RoomDto> roomListByMine(String userId);

}