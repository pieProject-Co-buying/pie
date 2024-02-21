package com.pie.pieProject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;





@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	@Autowired
	com.pie.pieProject.handler.SocketHandler socketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(socketHandler, "/chating");
		
		/* registry.addHandler(socketHandler, "/chating/{roomNumber}"); */
		// 채팅방 구분하는 값 추가 
		
	}
	
	
	
}