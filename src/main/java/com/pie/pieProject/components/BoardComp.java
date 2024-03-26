package com.pie.pieProject.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pie.pieProject.DAO.IFriendDao;
import com.pie.pieProject.DTO.BoardDto;
import com.pie.pieProject.DTO.ProxyApplyBoardDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ScrollProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class BoardComp {
	@Autowired
	IFriendDao fDao;

	public String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}


	  public String[] setArraysData(String key, String wallWord) { String[]
	  str_imgs = key.split(wallWord); for (String s : str_imgs) {
	 s.replace(wallWord, ""); } return str_imgs; }


	public Date setDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(dateStr);
	}

	/*
	 * public String translate(String str) { String text = ""; if
	 * (str.equals("food")) text = "식품"; else if (str.equals("baby")) text = "육아";
	 * else if (str.equals("beautyAndFashion")) text = "뷰티/패션"; else if
	 * (str.equals("pet")) text = "반려동물"; else if (str.equals("life")) text = "생활";
	 * else if (str.equals("etc")) text = "기타"; else if (str.equals("OTT")) text =
	 * "OTT"; else if (str.equals("game")) text = "게임"; else if
	 * (str.equals("bookAndMusic")) text = "도서/음악"; return text; }
	 */

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
	
	/*
	 * public List<BoardDto> translateList(List<BoardDto> list) { for (BoardDto d :
	 * list) { d.setCategory(translate(d.getCategory()));
	 * d.setThumbnail(setThumbNail(d.getProductImg())); } return list; }
	 * 
	 * public List<BoardDto> setTURL(List<BoardDto> list) { for (BoardDto d : list)
	 * { d.setUrl("townBuyproduct?num="+d.getNum()); } return list; }
	 * 
	 * public List<BoardDto> setPURL(List<BoardDto> list) { for (BoardDto d : list)
	 * { d.setUrl("viewProxyBoard?num="+d.getNum()); } return list; }
	 * 
	 * public List<BoardDto> setSURL(List<BoardDto> list) { for (BoardDto d : list)
	 * { d.setUrl("boardList?num="+d.getNum()); } return list; }
	 * 
	 * public List<ProxyBuyBoardDto> translateProxyList(List<ProxyBuyBoardDto> list)
	 * { for (ProxyBuyBoardDto d : list) {
	 * d.setCategory(translate(d.getCategory()));
	 * d.setThumbnail(setThumbNail(d.getProductImg()));
	 * d.setUrl("viewProxyBoard?num="+d.getNum()); } return list; }
	 * 
	 * public List<ProxyApplyBoardDto>
	 * translateProxyApplyList(List<ProxyApplyBoardDto> list) { for
	 * (ProxyApplyBoardDto d : list) { d.setCategory(translate(d.getCategory()));
	 * d.setThumbnail(setThumbNail(d.getProductImg()));
	 * d.setUrl("viewProxyApplyBoard?num="+d.getNum());
	 * d.setProcess(setProcess(d.getProcess()));
	 * 
	 * } return list; }
	 * 
	 * public String setThumbNail(String pics) { return
	 * pics.substring(0,pics.indexOf("/")); }
	 * 
	 * public List<TownBuyBoardDto> translateTownList(List<TownBuyBoardDto> list) {
	 * for (TownBuyBoardDto d : list) { d.setCategory(translate(d.getCategory()));
	 * d.setThumbnail(setThumbNail(d.getProductImg()));
	 * d.setUrl("townBuyproduct?num="+d.getNum()); } return list; }
	 * 
	 * public List<ShareServiceDto> translateShareList(List<ShareServiceDto> list) {
	 * for (ShareServiceDto d : list) { d.setCategory(translate(d.getCategory()));
	 * d.setThumbnail(setThumbNail(d.getProductImg()));
	 * d.setUrl("boardList?num="+d.getNum()); } return list; }
	 */

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

	// 마감임박
	public boolean closeClosely(ScrollProxyBuyBoardDto dto) {

//		어떤형식?
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		어떤걸변환할래?
		LocalDateTime updatedate = LocalDateTime.parse(dto.getDeadLine(), formatter);

		LocalDateTime now = LocalDateTime.now();
		
		Duration duration = Duration.between(updatedate, now);

		long minutes = duration.toMinutes(); // 변경된 부분

		int remains = dto.getPersonnelMax() - dto.getPersonnelNow();

		if (((minutes > 0 && minutes < 1440) && remains > 0) || (minutes > 0 && (remains <= 5 && remains > 0))) {
			return true;
		} else {
			return false;
		}

	}

	public List<String> listText(String str) {
		String title = null;
		String subtitle = null;
		String text = null;

		if (str.equals("now")) {
			title = "현재 진행중";
			subtitle = "파이는 현재 진행중";
			text = "파이가 함께할 사람들을 구하고 있답니다!<br>" + "서두르지 않으면 자리가 없을지도 몰라요!";
		} else if (str.equals("food")) {
			title = "맛있는 파이!";
			subtitle = "파이가 준비한 식품이예요!";
			text = "파이가 직접 고른 맛있는 음식들이예요!<br>" + "보기만 해도 배고프지 않나요?";
		} else if (str.equals("baby")) {
			title = "우리 아이에게 필요해요!";
			subtitle = "육아용품 공구는 파이!";
			text = "우리 아이에게 필요한 것들을 안전하고 저렴하게!<br>" + "좋은 상품을 함께 나눠요";
		} else if (str.equals("beautyAndFashion")) {
			title = "오늘은 패션데이";
			subtitle = "빛나는 당신을 위해서!";
			text = "당신을 꾸며줄 멋진 아이템을 준비했어요.<br>" + "내일은 어떻게 외출할까요?";
		} else if (str.equals("pet")) {
			title = "우리집 강아지도 파이!";
			subtitle = "다양한 반려 용품과 함께";
			text = "우리 초코에게 필요한 상품들도 여기있어요<br>" + "필요한 상품들을 골라볼까요?";
		} else if (str.equals("life")) {
			title = "생활속 파이!";
			subtitle = "필요한 것들을 빠르고 쉽게!";
			text = "일상 생활에서 필요한 것들을 쉽고 빠르게 구하세요.<br>" + "편리함을 즐겨보세요.";
		} else if (str.equals("etc")) {
			title = "당신이 찾는 것들을 위하여";
			subtitle = "없는것 빼고 다있는 파이";
			text = "필요하신 물품들을 꼼꼼히 찾아봤어요.<br>" + "당신이 찾는 모든것을 모아둘게요.";
		}

		List<String> list = new ArrayList<>();
		list.add(title);
		list.add(subtitle);
		list.add(text);

		return list;
	}

	public boolean rUFollowing(String me, String you) {
		if (fDao.checkFollow(me, you) > 0) {
			return true;
		}
		return false;
	}
}
