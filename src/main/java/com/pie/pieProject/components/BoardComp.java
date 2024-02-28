package com.pie.pieProject.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.pie.pieProject.DTO.ProxyBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class BoardComp {

	public String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}

	public String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for (String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}

	public Date setDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(dateStr);
	}

	public String translate(String str) {
		String text = "";
		if (str.equals("food"))
			text = "식품";
		else if (str.equals("baby"))
			text = "육아";
		else if (str.equals("beautyAndFashion"))
			text = "뷰티/패션";
		else if (str.equals("pet"))
			text = "반려동물";
		else if (str.equals("life"))
			text = "생활";
		else if (str.equals("etc"))
			text = "기타";
		else if (str.equals("OTT"))
			text = "OTT";
		else if (str.equals("game"))
			text = "게임";
		else if (str.equals("bookAndMusic"))
			text = "도서/음악";

		return text;
	}

	public String setProcess(String str) {
		String text = "";
		if (str.equals("0"))
			text = "접수중";
		if (str.equals("1"))
			text = "검토중";
		if (str.equals("2"))
			text = "접수반려";
		if (str.equals("3"))
			text = "공구진행중";
		if (str.equals("4"))
			text = "공구종료";
		return text;
	}

	public String masking(String str) {
		return str.replaceAll("(?<=.{2}).", "*");
	}

	public String parsingHtml(String html) {

		  // Jsoup을 사용하여 HTML 파싱
        Document document = Jsoup.parse(html);

        // HTML에서 텍스트만 추출
        String text = document.text();

		return text;
	}
	
	public List<ProxyBuyBoardDto> translateProxyList(List<ProxyBuyBoardDto> list){
		for(ProxyBuyBoardDto d : list) {
			d.setPr_category(translate(d.getPr_category()));
		}
		return list;
	}
	
	public String lastUpdateMessage(String str) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime updatedate = LocalDateTime.parse(str, formatter);

		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(updatedate, now);

		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;

		if (days > 0) {
			return String.format("%d일 전 업데이트", days);
		} else if (hours > 0) {
			return String.format("%d시간 전 업데이트", hours);
		} else {
			return String.format("%d분 전 업데이트", minutes);
		}
	}
}
