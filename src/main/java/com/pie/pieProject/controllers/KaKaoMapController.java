package com.pie.pieProject.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class KaKaoMapController {
	@Autowired
	IMemberDao mdao;
	@Autowired
	ITownBuyBoardDao tdao;
	@Value("${kakao.api.key}")
	String apiKey;


	@RequestMapping(value = "/map", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ResponseEntity<String> getKaKaoApiFromAddress(@RequestParam("nowlogin") String userId,
			HttpServletRequest request) {
		
		System.out.println("접속중:"+userId);
		
		String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
		String jsonString = null;
		MemberDto mdto = mdao.find(userId);
		String ad = mdto.getAddr_admin();
		/* System.out.println("주소:"+ad); */
		
		List<TownBuyBoardDto> list = tdao.listLocalActive(ad);
		
		/* System.out.println("검색된 리스트" + list.size()); */
		
		JSONObject obj = new JSONObject();
		JSONArray itemList = new JSONArray();
		String roadFullAddr;
		

		for (TownBuyBoardDto dto : list) {
			try {
				/* System.out.println("검색된 주소 : "+dto.getTo_address()); */
				roadFullAddr = URLEncoder.encode(dto.getAddress(), "UTF-8");
				String addr = apiUrl + "?query=" + roadFullAddr; // 수정
				URL url = new URL(addr);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("Authorization", "KakaoAK " + apiKey); // 수정
				BufferedReader rd = null;
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				StringBuffer docJson = new StringBuffer();
				String line;

				while ((line = rd.readLine()) != null) {
					docJson.append(line);
				}
				jsonString = docJson.toString();
				rd.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONParser parser = new JSONParser();
			
			/* System.out.println("json : "+jsonString); */

			try {
				JSONObject jsonMain = (JSONObject) parser.parse(jsonString);
				// "documents"는 JSONArray이므로 JSONObject 대신 JSONArray로 처리
				JSONArray jsonArray = (JSONArray) jsonMain.get("documents");

				// 첫 번째 배열 요소를 가져옴
				JSONObject firstElement = (JSONObject) jsonArray.get(0);

				// "road_address"를 가져옴
				JSONObject roadAddress = (JSONObject) firstElement.get("road_address");

				// "x"와 "y"를 가져옴
				String x = roadAddress.get("x").toString();
				String y = roadAddress.get("y").toString();

					 JSONObject item = new JSONObject();
					 item.put("title", dto.getTitle());
					 item.put("x", x);
					 item.put("y", y);
					 item.put("num", dto.getNum());
					 item.put("category", dto.getCategory());
					 int max = dto.getPersonnelMax();
					 int now = dto.getPersonnelNow();
						/* System.out.println(max-now); */
					 item.put("endSoon", max-now);
					 
					itemList.add(item);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		obj.put("addressList", itemList);
		/* System.out.println(obj.toJSONString()); */

		return ResponseEntity.ok(obj.toJSONString());
	}
}
