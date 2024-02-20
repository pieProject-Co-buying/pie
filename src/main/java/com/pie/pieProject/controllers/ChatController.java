package com.pie.pieProject.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pie.pieProject.DAO.IChatDao;

import com.pie.pieProject.DTO.*;


import org.json.simple.JSONObject;

@Controller
public class ChatController {
	

	@Autowired
	IChatDao dao;

	
    @MessageMapping("/chating") // WebSocket에서 "/chating"으로 메시지가 오면 이 메서드가 호출됩니다.
    public void handleChatMessage(String message) {
    	
        JSONObject obj = new JSONObject(); // 받은 JSON 형태의 메시지를 파싱합니다.
		
        /*
		 * JSONObject jsonObject = new JSONObject(message);
		 */        
        
       
        String msg = (String)obj.get("userName"); // 메시지 내용을 추출합니다.
        String userName = (String)obj.get("msg"); // 사용자 이름을 추출합니다.
        String userId = (String)obj.get("userId"); // 사용자 아이디
        
        // 추출한 정보를 Map에 담아서 데이터베이스에 저장합니다.
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("message", msg);
        
        // 데이터베이스에 저장하는 DAO 메서드를 호출합니다.
        dao.saveMsg(userId, userName, msg);
    }
    
    
	
	List<Room> roomList = new ArrayList<Room>();
	static int roomNumber = 0;
	

	
	
	@RequestMapping("/chat")
	public ModelAndView chat() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pieContents/chatting/chat");
		
		return mv;
	}
	
	
	/**
	 * 방 페이지
	 * @return
	 */
	@RequestMapping("/room")
	public ModelAndView room() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pieContents/chatting/room");
		return mv;
	}
	
	
	
	
   
	
	/**
	 * 방 생성하기
	 * @param params
	 * @return
	 */
	@RequestMapping("/createRoom")
	public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
		
		String roomName = (String) params.get("roomName");
		if(roomName != null && !roomName.trim().equals("")) {
			Room room = new Room();
			room.setRoomNumber(++roomNumber);
			room.setRoomName(roomName);
			roomList.add(room);
		}
		
		return roomList;
	}
	
	
	/**
	 * 방 정보가져오기
	 * @param params
	 * @return
	 */
	
	@RequestMapping("/getRoom")
	public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params){
		return roomList;
	}
	
	
	/**
	 * 채팅방
	 * @return
	 */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
		
		ModelAndView mv = new ModelAndView();
		int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
		
		List<Room> new_list = roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors.toList());
		if(new_list != null && new_list.size() > 0) {
			mv.addObject("roomName", params.get("roomName")); 
			mv.addObject("roomNumber", params.get("roomNumber"));
			
			mv.setViewName("pieContents/chatting/chat");
			
		}else {
			
			mv.setViewName("pieContents/chatting/room");
		}
		
		return mv;
	}
	
}