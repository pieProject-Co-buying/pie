package com.pie.pieProject.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class KakaoProfile {
    private Integer id;
    private LocalDateTime connectedAt;
    private String email;
    private String nickname;

    public KakaoProfile(String jsonResponseBody){
    	JSONParser jsonParser = new JSONParser();
    	JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonResponseBody);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	this.id = ((Number) jsonObject.get("id")).intValue();

    	String connected_at = (String) jsonObject.get("connected_at");
    	connected_at = connected_at.substring(0, connected_at.length() - 1);
    	LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    	this.connectedAt = connectDateTime;

    	JSONObject properties = (JSONObject) jsonObject.get("properties");
    	this.nickname = (String) properties.get("nickname");

    	JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
    	this.email = (String) kakaoAccount.get("email");
    }

}