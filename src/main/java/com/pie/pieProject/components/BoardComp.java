package com.pie.pieProject.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
	
	public String setProcess(String str){
		String text = "";
		if(str.equals("0")) text = "접수중";
		if(str.equals("1")) text = "검토중";
		if(str.equals("2")) text = "접수반려";
		if(str.equals("3")) text = "공구진행중";
		if(str.equals("4")) text = "공구종료";
		
		return text;
	}
	
	public String masking(String str) {
		return str.replaceAll("(?<=.{2}).","*");
	}
}
