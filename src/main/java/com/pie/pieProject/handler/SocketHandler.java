package com.pie.pieProject.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.pie.pieProject.DAO.IChatDao;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Component
public class SocketHandler extends TextWebSocketHandler {
	@Autowired
	BoardComp bcomp;

	@Autowired
	IChatDao dao;

	HashMap<String, WebSocketSessionDto> sessionMap = new HashMap<>(); // 웹소켓 세션을 담아둘 맵 - 현재 연결된 세션들

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		// 메시지 발송
		String msg = message.getPayload();

		System.out.println();
		System.out.println("===============================");
		System.out.println("msg : " + msg);
		System.out.println("===============================");
		System.out.println();

//      
//      System.out.println();
//      System.out.println("===============================");     
//      System.out.println("msg : " + msg);
//      System.out.println("===============================");       
//      System.out.println();
//      

		JSONObject obj = jsonToObjectParser(msg);
		String userName = (String) obj.get("userName");

		for (String key : sessionMap.keySet()) {
			WebSocketSession wss = sessionMap.get(key).getWss();
			if (sessionMap.get(key).getMem1() != null) {
				if ((sessionMap.get(key).getMem1().equals(obj.get("mem1").toString())
						&& sessionMap.get(key).getMem2().equals(obj.get("mem2").toString()))
						|| (sessionMap.get(key).getMem2().equals(obj.get("mem1").toString())
								&& sessionMap.get(key).getMem1().equals(obj.get("mem2").toString()))) {
					try {
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println();
		System.out.println("===============================");
		System.out.println("소켓핸들러 handleTextMessage 동작");
		System.out.println(obj);
		System.out.println("userId : " + obj.get("userId"));
		System.out.println("userName : " + obj.get("userName"));
		System.out.println("msg : " + obj.get("msg"));
		System.out.println("roomName : " + obj.get("roomName"));
		System.out.println("roomNumber : " + obj.get("roomNumber"));
		System.out.println("===============================");
		System.out.println();

		// dao에 불러와서 저장
		dao.saveMsg((String) obj.get("roomName"), (String) obj.get("roomNumber"), (String) obj.get("userId"),
				(String) obj.get("userName"), (String) obj.get("msg"));

		String mem1 = (String) obj.get("mem1");
		String mem2 = (String) obj.get("mem2");
		String sessionId = (String) obj.get("sessionId");

		// 알람기능 구현위한 테스트중
		showMessageNotification(mem1, mem2, userName);

	}

	// 알람기능 구현위한 테스트중
	private void showMessageNotification(String mem1, String mem2, String userName) {
		// 알림을 표시하는 로직

		for (String key : sessionMap.keySet()) {
			WebSocketSessionDto dto = sessionMap.get(key);
			WebSocketSession wss = dto.getWss();
			if (dto.getMem1() != null) {
				if ((dto.getMem1().equals(mem1) && dto.getMem2().equals(mem2))
						|| (dto.getMem2().equals(mem1) && dto.getMem1().equals(mem2))) {
					try {
						// 알림을 보낼 메시지 생성
						JSONObject notification = new JSONObject();
						notification.put("type", "notification");
						notification.put("userName", userName);
						notification.put("message", userName + "님의 메시지가 도착했습니다");

						System.out.println(notification);

						// 메시지 전송
						wss.sendMessage(new TextMessage(notification.toJSONString()));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				if ((mem1.equals(userName) || mem2.equals(userName))
						&& (dto.getNickname().equals(mem1) || dto.getNickname().equals(mem2))) {
					try {
						// 알림을 보낼 메시지 생성
						JSONObject notification = new JSONObject();
						notification.put("type", "notification");
						notification.put("userName", userName);
						notification.put("message", userName + "님의 메시지가 도착했습니다");

						System.out.println(notification);

						// 메시지 전송
						wss.sendMessage(new TextMessage(notification.toJSONString()));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
   @Override
   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	   
      //소켓 연결
      super.afterConnectionEstablished(session);
      WebSocketSessionDto dto = new WebSocketSessionDto();
      if (session.getUri().getQuery() != null && !session.getUri().getQuery().equals("")) {
          String queryString = session.getUri().getQuery();
          String[] queryArr = queryString.split("&");
          for (String item : queryArr) {
        	  String[] data = item.split("=");
        	  
        	  System.out.println(data.length);
        	  if(data.length<=1) {
        		  return;
        	  }
        	  
        	  if (data[0].equals("sessionId")) {
                  dto.setNickname(data[1]);
        	  } else if (data[0].equals("roomNumber")) {
                  dto.setRoomNumber(Integer.parseInt(data[1]));
        	  } else if (data[0].equals("mem1")) {    
                  dto.setMem1(data[1]);
        	  } else if (data[0].equals("mem2")) {
                  dto.setMem2(data[1]);
        	  }
          }
      }
      dto.setWss(session);
      sessionMap.put(session.getId(), dto);
      
      
       // 클라이언트에게 세션 닉네임을 전송
       JSONObject obj = new JSONObject();

       obj.put("type", "getId");
       obj.put("sessionId", session.getId());
       session.sendMessage(new TextMessage(obj.toJSONString()));
       

       System.out.println("소켓통신성공");
       
   }

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료
		sessionMap.remove(session.getId());
		super.afterConnectionClosed(session, status);

		System.out.println("통신해제");
	}

	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

}

@Getter
@Setter
class WebSocketSessionDto {
	private String nickname;
	private int roomNumber;
	private WebSocketSession wss;
	private String mem1;
	private String mem2;

}