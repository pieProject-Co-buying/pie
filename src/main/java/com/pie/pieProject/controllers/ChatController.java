package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.pie.pieProject.DAO.IFriendDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.*;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.simple.JSONObject;



@Controller
public class ChatController {

	@Autowired
	IChatDao dao;
	
	@Autowired
	IFriendDao fdao;
	
	@Autowired
	IMemberDao mdao;
	
	@Autowired
	ITownBuyBoardDao tbdao;
	
	@Autowired
	BoardComp bcomp;

	List<RoomDto> roomList = new ArrayList<RoomDto>();
	static int roomNumber = 0;

	@MessageMapping("/chating") // WebSocket에서 "/chating"으로 메시지가 오면 이 메서드가 호출됨
	
	public void handleChatMessage(String message, Model model) {

		JSONObject obj = new JSONObject(); // 받은 JSON 형태의 메시지를 파싱

		/*
		 * JSONObject jsonObject = new JSONObject(message);
		 */

		String msg = (String) obj.get("userName"); // 메시지 내용을 추출
		String userName = (String) obj.get("msg"); // 사용자 이름을 추출
		String userId = (String) obj.get("userId"); // 사용자 아이디
		String roomNumber = (String) obj.get("roomNumber"); // 사용자 이름을 추출
		String roomName = (String) obj.get("roomName"); // 사용자 아이디
		
		
		
		//추가해봄
		List<TownBuyBoardDto> tboard = tbdao.listDao();
		model.addAttribute("tboard", tboard);
		
	    // 사용자 아이디에 해당하는 프로필 이미지 가져오기
	    String profileImg = "";
	    for (TownBuyBoardDto boardDto : tboard) {
	        if (boardDto.getTo_id().equals(userId)) {
	            profileImg = boardDto.getTo_profileImg();
	            break;
	        }
	    }
	    
	    
	    
		// 추출한 정보를 Map에 담아서 데이터베이스에 저장
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("userName", userName);
		params.put("message", msg);
		params.put("roomNumber", roomNumber);
		params.put("roomName", roomName);


		// 데이터베이스에 저장하는 DAO 메서드를 호출
		dao.saveMsg(userId, userName, msg, roomNumber, roomName);
		

		

	}

	@RequestMapping("/chat")
	public ModelAndView chat() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("pieContents/chatting/chat");

		return mv;
	}

	
	/* 방 페이지  @return */
	@RequestMapping("/room")
	public ModelAndView room(Model model, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("pieContents/chatting/room");

		HttpSession session = request.getSession();

		String buyerId = request.getParameter("mem");
		List<MemberDto> friendsList = fdao.friendsList(bcomp.getSession(request, "userId"));
		
		System.out.println("친구목록:"+friendsList);
		
		
		
		model.addAttribute("roomList", dao.roomListByID(buyerId));
		model.addAttribute("fl",friendsList);
		model.addAttribute("you", buyerId);
		

		
		List<TownBuyBoardDto> tboard = tbdao.listDao();
		System.out.println(tboard);
		
		
		model.addAttribute("tboard", tboard);
		
		

		return mv;
	}

	/* 방 생성하기 @param params @return  */
	@RequestMapping("/createRoom")
	public @ResponseBody List<ChatRoomUserDto> createRoom(@RequestParam HashMap<Object, Object> params,
			
		HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		String managerMemId = (String) params.get("mem2");
		StringBuilder mems = new StringBuilder("@");
		// 리스트 잘 불어오고 있는지 확인용
		List<String> memList = new ArrayList<>(Arrays.asList(userId, managerMemId));

		mems.append(userId);
		mems.append("@");
		mems.append(managerMemId);


		
		 System.out.println("============================");
		 System.out.println("컨트롤러 createRoom 동작"); System.out.println("userId : " +  userId); System.out.println("roomName : " + managerMemId);
		 System.out.println("roomNumber : " + roomNumber);
		 System.out.println("memList : " + memList); System.out.println("mems : " +  mems); System.out.println("============================");

		 int roomNumber = dao.insertRoom(managerMemId, userId);
		 int roomNumber2 = dao.insertRoom(userId, managerMemId);
		 


		List<ChatRoomUserDto> myRooms = dao.roomListByID(userId);
		// 저장된 dao에서 정보 가지고 오기 위해 추가
		
		
		
		List<TownBuyBoardDto> tboard = tbdao.listDao();
		model.addAttribute("tboard", tboard);
		
		
		

		return myRooms;

	}

	/* 방 정보가져오기 @param  @return */

	@RequestMapping("/getRoom")
	public ResponseEntity<String> getRoom(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("nickName");

		System.out.println("##########userId : " + userId);
		/* String userId = "혜혜"; */

		JSONObject obj = new JSONObject();

		List<RoomDto> getRoom = new ArrayList<RoomDto>();
		
		List<TownBuyBoardDto> tboard = tbdao.listDao();
		model.addAttribute("tboard", tboard);

		return ResponseEntity.ok(obj.toJSONString());
	}

	/* 채팅방 @return  */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params, HttpServletRequest request, Model model) {
		// 요청 파라미터를 HashMap 으로 받음

		ModelAndView mv = new ModelAndView(); // 컨트롤러 메서드가 뷰와 모델 데이터를 함께 반환
		int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
		// 채팅방 번호
		String roomName = (String) params.get("roomNumber"); // 방이름
		
		System.out.println("movechating");
		
		RoomDto roomInfo = dao.getRoomInfo(roomNumber);
 
		List<RoomDto> new_list = dao.roomList();

		mv.addObject("roomInfo", roomInfo);
		mv.setViewName("pieContents/chatting/chat");
		

		
		// 대화 목록 불러오기
		List<ChatDto> chatList = dao.chatList(roomNumber);
		model.addAttribute("chatList", chatList);
		System.out.println("chatList : " + chatList);

		return mv;
	}
	
	

}