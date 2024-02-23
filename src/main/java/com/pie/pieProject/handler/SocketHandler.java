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

import jakarta.servlet.http.HttpServletRequest;




@Component
public class SocketHandler extends TextWebSocketHandler {

	
	
	@Autowired
	IChatDao dao;
   
	
   HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵 - 현재 연결된 세션들
   
   
   
// chatRoomId: {session1, session2}
   private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
   
   
   
   @Override
   public void handleTextMessage(WebSocketSession session, TextMessage message) {
	   
      //메시지 발송
      String msg = message.getPayload();
      
//      
//      System.out.println();
//      System.out.println("===============================");     
//      System.out.println("msg : " + msg);
//      System.out.println("===============================");       
//      System.out.println();
//      
      
      JSONObject obj = jsonToObjectParser(msg);
      String userName = (String) obj.get("userName");
      
      for(String key : sessionMap.keySet()) {
         WebSocketSession wss = sessionMap.get(key);
         try {
            wss.sendMessage(new TextMessage(obj.toJSONString()));
         }catch(Exception e) {
            e.printStackTrace();
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
      
      
      
      //dao에 불러와서 저장
      dao.saveMsg((String)obj.get("roomName"), (String)obj.get("roomNumber"), (String)obj.get("userId"), (String)obj.get("userName"), (String)obj.get("msg"));
   
   
   }
   
   
   
   
   
   @SuppressWarnings("unchecked")
   @Override
   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
      //소켓 연결
      super.afterConnectionEstablished(session);
      sessionMap.put(session.getId(), session);
      
      
       // 클라이언트에게 세션 닉네임을 전송
       JSONObject obj = new JSONObject();

       obj.put("type", "getId");
       obj.put("sessionId", session.getId());
       session.sendMessage(new TextMessage(obj.toJSONString()));
       

       System.out.println("소켓통신성공");
       
   }
   
   
   
   
   
   
   @Override
   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      //소켓 종료
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
