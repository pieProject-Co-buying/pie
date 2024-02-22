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

		String msg = (String) obj.get("userName"); // 메시지 내용을 추출합니다.
		String userName = (String) obj.get("msg"); // 사용자 이름을 추출합니다.
		String userId = (String) obj.get("userId"); // 사용자 아이디
		String roomNumber = (String) obj.get("roomNumber"); // 사용자 이름을 추출합니다.
		String roomName = (String) obj.get("roomName"); // 사용자 아이디

		// 추출한 정보를 Map에 담아서 데이터베이스에 저장합니다.
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("userName", userName);
		params.put("message", msg);
		params.put("roomNumber", roomNumber);
		params.put("roomName", roomName);

		/*
		 * System.out.println(); System.out.println("===============================");
		 * System.out.println("컨트롤러 handleChatMessage 동작"); System.out.println(obj);
		 * System.out.println("userId : " + obj.get("userId"));
		 * System.out.println("userName : " + obj.get("userName"));
		 * System.out.println("msg : " + obj.get("msg"));
		 * System.out.println("roomName : " + obj.get("roomName"));
		 * System.out.println("roomNumber : " + obj.get("roomNumber"));
		 * System.out.println("==============================="); System.out.println();
		 */

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
	 * 
	 * @return
	 */
	@RequestMapping("/room")
	public ModelAndView room(Model model, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("pieContents/chatting/room");

		HttpSession session = request.getSession();
		/* String userId = (String)session.getAttribute("userId"); */
		String userId = (String) session.getAttribute("nickName");
		String yourId = request.getParameter("mem");
		System.out.println(dao.roomListByID(userId).size());

		model.addAttribute("roomList", dao.roomListByID(userId));
		model.addAttribute("you", yourId);

		return mv;
	}

	/**
	 * 방 생성하기
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping("/createRoom")
	public @ResponseBody List<RoomDto> createRoom(@RequestParam HashMap<Object, Object> params,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		String nickName = (String) session.getAttribute("nickName");

		String roomName = (String) params.get("roomName");
		Integer roomNumber = (Integer) params.get("roomNumber");
		StringBuilder mems = new StringBuilder("@");
		// 리스트 잘 불어오고 있는지 확인용
		List<String> memList = new ArrayList<>(Arrays.asList(nickName, roomName));

		mems.append(nickName);
		mems.append("@");
		mems.append(roomName);

		/*
		 * System.out.println("============================");
		 * System.out.println("컨트롤러 createRoom 동작"); System.out.println("nickName : " +
		 * nickName); System.out.println("roomName : " + roomName);
		 * System.out.println("roomNumber : " + roomNumber);
		 * System.out.println("memList : " + memList); System.out.println("mems : " +
		 * mems); System.out.println("============================"); 
		 * 
		 * 
		 */
		List<RoomDto> myRooms;
		
		if (roomNumber == null) {
			roomNumber = 0; // 기본값 설정

			List<RoomDto> AllRooms = dao.roomList();
			// 이전 방 목록에서 최대 방 번호를 찾아서 그 다음 번호를 증가시켜 새로운 방 번호로 사용
			int maxRoomNumber = AllRooms.stream().mapToInt(RoomDto::getRoomNumber).max().orElse(0);
			int newRoomNumber = maxRoomNumber + 1;
			

			if (roomName != null && nickName != null) {
				List<RoomDto> nowRoom = new ArrayList<>();
				myRooms = dao.roomListByID(nickName);
				// 방 이름이 중복되지 않고 닉네임과 방 이름이 모두 포함된 경우에만 새로운 방을 추가
				boolean isRoomNameValid = roomList.stream().noneMatch(room -> room.getRoomName().equals(roomName));
				boolean isNickNameValid = roomList.stream().noneMatch(room -> room.getPartyMem().contains(nickName));

				if (isRoomNameValid && isNickNameValid) {
					/*
					 * mems.append(nickName); mems.append("@"); mems.append(roomName);
					 */
					// 이 구문이 들어가면 중복값으로 넣어지는 현상 발생

					RoomDto room = new RoomDto();
					room.setRoomNumber(newRoomNumber);
					room.setRoomName(roomName);
					room.setPartyMem(mems.toString());

					roomList.add(room);

					// 새로운 방 정보를 데이터베이스에 삽입
					dao.insertRoom(roomName, newRoomNumber, mems.toString());
					
					nowRoom.add(room);
					return nowRoom;
				}
			}
		}
		myRooms = dao.roomListByID(nickName);
		// 저장된 dao에서 정보 가지고 오기 위해 추가
		

		return myRooms;

		/*
		 * if(roomName != null && !roomName.trim().equals("")) {
		 * 
		 * System.out.println("여기까진 넘어왔어요");
		 * 
		 * if(memList.contains(nickName) && memList.contains(roomName)) {
		 * if(memList.contains(nickName) && memList.contains(roomName)) {
		 * 
		 * System.out.println("이미 방이 있어요");
		 * 
		 * return roomList;
		 * 
		 * } else {
		 * 
		 * System.out.println("방이없네요");
		 * 
		 * 
		 * RoomDto room = new RoomDto();
		 * 
		 * room.setRoomNumber(newRoomNumber); // 이전 방 번호에서 1씩 증가된 값을 사용
		 * room.setRoomName(roomName); room.setPartyMem(mems.toString());
		 * 
		 * roomList.add(room);
		 * 
		 * // 마이바티스를 사용하여 방 정보를 데이터베이스에 삽입 dao.insertRoom(roomName, newRoomNumber,
		 * memList.toString()); // 새로운 방 번호를 사용
		 * 
		 * }
		 * 
		 * }
		 * 
		 * return roomList;
		 */

	}

	/**
	 * 방 정보가져오기
	 * 
	 * @param params
	 * @return
	 */
//	@RequestParam HashMap<Object, Object> params

	@RequestMapping("/getRoom")
	public ResponseEntity<String> getRoom(HttpServletRequest request) {

		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("nickName");

		System.out.println("##########userId : " + userId);
		/* String userId = "혜혜"; */

		JSONObject obj = new JSONObject();

		List<RoomDto> getRoom = new ArrayList<RoomDto>();

		getRoom = dao.roomListByID("/" + userId);

		for (RoomDto room : getRoom) {

			obj.put("roomName", room.getRoomName());
			obj.put("roomNum", room.getRoomNumber());
			room.setPartyMems(room.getPartyMem().split("/"));
			obj.put("member", room.getPartyMems());

		}

		/*
		 * System.out.println("=======================");
		 * System.out.println("컨트롤러 getRoom 동작"); System.out.println("userId" + userId);
		 * System.out.println("getRoom" + getRoom);
		 * System.out.println("=======================");
		 * 
		 */

		return ResponseEntity.ok(obj.toJSONString());
	}

	/**
	 * 채팅방
	 * 
	 * @return
	 */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params, HttpServletRequest request, Model model) { // 요청
																															// 파라미터를
		// HashMap
		// 으로 받음

		ModelAndView mv = new ModelAndView(); // 컨트롤러 메서드가 뷰와 모델 데이터를 함께 반환
		int roomNumber = Integer.parseInt((String) params.get("roomNumber")); // 채팅방 번호
		String roomName = (String) params.get("roomNumber"); // 방이름
		String yourId = request.getParameter("mem");

		/*
		 * List<RoomDto> new_list =
		 * roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors
		 * .toList());
		 */ // 방번호가 RoomDto 객체를 'Loomlist'에서 찾아 리스트로 반환

		List<RoomDto> new_list = dao.roomList();

		if (new_list != null && new_list.size() > 0) {

			mv.addObject("roomName", params.get("roomName"));
			mv.addObject("roomNumber", params.get("roomNumber"));

			mv.setViewName("pieContents/chatting/chat");

		} else {

			mv.setViewName("pieContents/chatting/room");
		}

		/*
		 * // 정보 확인하는 출력구문 System.out.println("===================");
		 * System.out.println("컨트롤러 chating 동작"); System.out.println(roomNumber);
		 * System.out.println(roomName); System.out.println("new_list" + new_list);
		 * System.out.println("===================");
		 * 
		 */

		// 대화 목록 불러오기
		List<ChatDto> chatList = dao.chatList(roomNumber);
		model.addAttribute("chatList", chatList);

		System.out.println("chatList : " + chatList);

		return mv;
	}

}