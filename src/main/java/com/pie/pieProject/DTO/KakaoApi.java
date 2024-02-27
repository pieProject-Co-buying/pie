package com.pie.pieProject.DTO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class KakaoApi {

	@Value("${kakao.api.key}")
	private String kakaoApiKey;

	@Value("${kakao.redirect_uri}")
	private String kakaoRedirectUri;
	
	public KakaoApi(){
		 log.info("Kakao API Key: {}", kakaoApiKey);
	        log.info("Kakao Redirect URI: {}", kakaoRedirectUri);
	}
	
	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
		String reqUrl = "https://kauth.kakao.com/oauth/token";

		try {
		    URL url = new URL(reqUrl);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		    // 필수 헤더 세팅
		    conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		    conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.

		    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
		        StringBuilder sb = new StringBuilder();

		        // 필수 쿼리 파라미터 세팅
		        sb.append("grant_type=authorization_code");
		        sb.append("&client_id=").append(kakaoApiKey);
		        sb.append("&redirect_uri=").append(kakaoRedirectUri);
		        sb.append("&code=").append(code);

		        bw.write(sb.toString());
		        bw.flush();
		    }

		    int responseCode = conn.getResponseCode();

		    try (BufferedReader br = new BufferedReader(new InputStreamReader(
		            (responseCode >= 200 && responseCode <= 300) ? conn.getInputStream() : conn.getErrorStream()))) {

		        StringBuilder responseSb = new StringBuilder();
		        String line;

		        while ((line = br.readLine()) != null) {
		            responseSb.append(line);
		        }

		        String result = responseSb.toString();

		        JSONParser jsonParser = new JSONParser();
		        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

		        accessToken = (String) jsonObject.get("access_token");
		        refreshToken = (String) jsonObject.get("refresh_token");
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return accessToken;
	}
	
	
	public HashMap<String, Object> getUserInfo(String accessToken) {
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqUrl = "https://kapi.kakao.com/v2/user/me";
	    
	    try {
	        URL url = new URL(reqUrl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

	        int responseCode = conn.getResponseCode();

	        try (BufferedReader br = new BufferedReader(
	                new InputStreamReader((responseCode >= 200 && responseCode <= 300) ? conn.getInputStream() : conn.getErrorStream()))) {

	            StringBuilder responseSb = new StringBuilder();
	            String line;

	            while ((line = br.readLine()) != null) {
	                responseSb.append(line);
	            }

	            String result = responseSb.toString();

	            JSONParser jsonParser = new JSONParser();
	            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

	            JSONObject properties = (JSONObject) jsonObject.get("properties");
	            JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");

	            String nickname = (String) properties.get("nickname");
	            String email = (String) kakaoAccount.get("email");

	            userInfo.put("nickname", nickname);
	            userInfo.put("email", email);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return userInfo;
	}

}
