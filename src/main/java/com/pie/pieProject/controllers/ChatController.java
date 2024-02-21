package com.pie.pieProject.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pie.pieProject.DAO.IChatDao;

import com.pie.pieProject.DTO.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;

@Controller
public class ChatController {
	

	@Autowired
	IChatDao dao;
	
	
	List<RoomDto> roomList = new ArrayList<RoomDto>();
	static int roomNumber = 0;

	
    @MessageMapping("/chating") // WebSocket에서 "/chating"으로 메시지가 오면 이 메서드가 호출됩니다.
    public void handleChatMessage(String message) {
    	
        JSONObject obj = new JSONObject(); // 받은 JSON 형태의 메시지를 파싱합니다.
		
        /*
		 * JSONObject jsonObject = new JSONObject(message);
		 */        
        
       
        String msg = (String)obj.get("userName"); // 메시지 내용을 추출합니다.
        String userName = (String)obj.get("msg"); // 사용자 이름을 추출합니다.
        String userId = (String)obj.get("userId"); // 사용자 아이디
        String roomNumber = (String)obj.get("roomNumber"); // 사용자 이름을 추출합니다.
        String roomName = (String)obj.get("roomName"); // 사용자 아이디
        
        
        // 추출한 정보를 Map에 담아서 데이터베이스에 저장합니다.
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("message", msg);
        params.put("roomNumber", roomNumber);
        params.put("roomName", roomName);
        
        // 데이터베이스에 저장하는 DAO 메서드를 호출합니다.
        dao.saveMsg(userId, userName, msg, roomNumber, roomName);
    }
    
    

	
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
	public ModelAndView room(Model model, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pieContents/chatting/room");
		
		 HttpSession session = request.getSession();
			/* String userId = (String)session.getAttribute("userId"); */
		 String userId = (String)session.getAttribute("nickName");
		
		model.addAttribute("roomList", dao.roomListByID(userId));
		
		return mv;
	}
	
	
	
	
   
	
	/**
	 * 방 생성하기
	 * @param params
	 * @return
	 */
	@RequestMapping("/createRoom")
	public @ResponseBody List<RoomDto> createRoom(@RequestParam HashMap<Object, Object> params, HttpServletRequest request){
	    
		
		HttpSession session = request.getSession();
        String nickName = (String) session.getAttribute("nickName");
        		
	    String roomName = (String) params.get("roomName");
	    Integer roomNumber = (Integer) params.get("roomNumber");
		/*String nickName = (String) params.get("nickName");*/

	    
	   
	    
	    if (roomNumber == null) {
	        roomNumber = 0; // 기본값 설정
	    }
	    
	    // 이전 방 목록에서 최대 방 번호를 찾아서 그 다음 번호를 증가시켜 새로운 방 번호로 사용
	    int maxRoomNumber = roomList.stream()
	                                .mapToInt(RoomDto::getRoomNumber)
	                                .max()
	                                .orElse(0);
	    int newRoomNumber = maxRoomNumber + 1;
	    
	    if(roomName != null && !roomName.trim().equals("")) {
	    	
	        RoomDto room = new RoomDto();
	        
	        room.setRoomNumber(newRoomNumber); // 이전 방 번호에서 1씩 증가된 값을 사용
	        room.setRoomName(roomName);
	        room.setPartyMem(nickName);
	        
	        roomList.add(room);
	        
	        // 마이바티스를 사용하여 방 정보를 데이터베이스에 삽입
	        dao.insertRoom(roomName, newRoomNumber, nickName); // 새로운 방 번호를 사용
	        
	    }        
	    
	    return roomList;
	    
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	
	
	/**
	 * 방 정보가져오기
	 * @param params
	 * @return
	 */
//	@RequestParam HashMap<Object, Object> params
	
	 @RequestMapping("/getRoom") 
	 public ResponseEntity<String> getRoom(HttpServletRequest request){
		 
		 HttpSession session = request.getSession();
			/* String userId = (String)session.getAttribute("userId"); */
		 String userId = "혜혜";
		 
		 JSONObject obj = new JSONObject();
		 
		 List<RoomDto> getRoom = new ArrayList<RoomDto>();
		 
		 System.out.println(userId);
		 
		 getRoom = dao.roomListByID("/"+userId);
		 
		 System.out.println(getRoom);
		 
		 for(RoomDto room : getRoom) {
			 
			obj.put("roomName",  room.getRoomName());
			obj.put("roomNum",  room.getRoomNumber());
			room.setPartyMems(room.getPartyMem().split("/"));
			obj.put("member", room.getPartyMems());
			
		 }
		 
		 return ResponseEntity.ok(obj.toJSONString()); 
	}
	 
	
	
	/**
	 * 채팅방
	 * @return
	 */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
		
		ModelAndView mv = new ModelAndView();
		int roomNumber = Integer.parseInt((String)params.get("roomNumber"));
		
		List<RoomDto> new_list = roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors.toList());
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