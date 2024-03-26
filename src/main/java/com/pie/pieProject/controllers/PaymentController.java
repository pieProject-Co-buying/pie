package com.pie.pieProject.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DAO.ISubscribeDAO;
import com.pie.pieProject.DTO.BoardDto;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.SubScribeDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	@Autowired
	private IPaymentDAO dao;
	@Autowired
	IProxyBuyDao Pdao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	IShareServiceDao Sdao;
	@Autowired
	ISubscribeDAO Subdao;
	@Autowired
	IParticipateCheckDao paDao;
	@Value("${port.shop.code}")
	String shopCode;
	@Value("${port.shop.key}")
	private String apiKey;
	@Value("${port.shop.secret}")
	private String secretKey;

	@PostMapping("/shoppingInfo")
	public ResponseEntity<String> getShoppingInfo(@RequestParam("nowLogin") String nowLogin,
			@RequestParam("nowBoard") String nowBoard) {
		MemberDto dto = mdao.find(nowLogin);
		BoardDto board = Pdao.getView(nowBoard);

		JSONObject obj = new JSONObject();

		obj.put("shopkey", shopCode);
		obj.put("name", board.getProductName());
		obj.put("amount", board.getPricePer());
		obj.put("buyer_name", dto.getName());
		obj.put("buyer_tel", dto.getPhone());
		obj.put("buyer_addr", dto.getAddress_main() + " " + dto.getAddress_sub());
		obj.put("buyer_email", dto.getEmail());
		obj.put("buyer_postcode", dto.getPostCode());
		obj.put("id", nowLogin);
		obj.put("boardNum", nowBoard);

		return ResponseEntity.ok(obj.toString());
	}

	@PostMapping("/payCheck")
	@PreAuthorize("ADMIN")
	public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
		dao.insertPayment(dto);
		paDao.participate(dto.getPay_num(), dto.getPay_category(), dto.getBuyer_id());
		// 응답 반환
		return new ResponseEntity<>(dto.getPay_category(), HttpStatus.OK);
	}

	/********** 결제완료 페이지 이동 **********/
	@RequestMapping("/shareServiceFinish")
	public String goFinish(HttpServletRequest request, Model model) {
		// @RequestParam(name = "msg", required = false) String msg{
		HttpSession session = request.getSession();
		String uId = (String) session.getAttribute("userId");
		String nid = request.getParameter("num");
		String merchant = request.getParameter("merchant_uid");
		String category = request.getParameter("category");

		String productImg = null;
		String brand = null;
		String productName = null;
		int price = 0;

		if (category.equals("Share")) {
			ShareServiceDto bl = Sdao.selectBoard(Integer.parseInt(nid));
			productImg = bl.getProductImg();
			price = bl.getPricePer();
			brand = bl.getBrand();
			productName = bl.getProductName();

			// 작성자가 아닐 경우에만 현재 인원 카운트
			if (!bl.getId().equals(uId)) {
				Sdao.updateNow(Integer.parseInt(nid));
			}

			bl = Sdao.selectBoard(Integer.parseInt(nid));
			if (bl.getPersonnelMax() <= bl.getPersonnelNow()) {
				Sdao.maxChk(Integer.parseInt(nid));
			} else if (bl.getPersonnelMax() > bl.getPersonnelNow()) {
				Sdao.minChk(Integer.parseInt(nid));
			}

		} else if (category.equals("Proxy")) {
			ProxyBuyBoardDto bl = Pdao.getView(nid);
			productImg = bl.getProductImg();
			price = bl.getPricePer();
			brand = bl.getBrand();
			productName = bl.getProductName();

			Pdao.updateNow(Integer.parseInt(nid));
			bl = Pdao.getView(nid);
			if (bl.getPersonnelMax() <= bl.getPersonnelNow()) {
				Pdao.maxChk(Integer.parseInt(nid));
			}
		}

		model.addAttribute("find", mdao.find(uId));
		model.addAttribute("productImg", productImg);
		model.addAttribute("brand", brand);
		model.addAttribute("productName", productName);
		model.addAttribute("price", price);
		model.addAttribute("pay", dao.payBoard(merchant, category));

		return "pieContents/shareService/shareServiceFinish";
	}

	// 정기결제 (구독)
	@PostMapping("/complete")
	public ResponseEntity<String> insertSubScribe(@RequestBody SubScribeDTO dto) {
		Subdao.insertSubScribe(dto);

		return new ResponseEntity<>(dto.getSub_premium(), HttpStatus.OK);
	}

	// admin 결제내역 페이지
	@RequestMapping("/shareServiceApplyConsole")
	public String applyConsole(@RequestParam("page") int page, Model model) {
		List<PaymentDTO> list = dao.paymentList();
		model.addAttribute("pay", list);

		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		if (pageNum <= 0)
			pageNum = 1;

		List<PaymentDTO> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());

		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}

		System.out.println(templist.size());

		model.addAttribute("payList", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);

		return "pieContents/shareService/shareServiceApplyConsole";
	}

	/********** admin 결제내역 페이지 id,nickname 기반 검색 **********/
	@RequestMapping("/searchBuyerName")
	public String searchBuyer(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		model.addAttribute("list", dao.searchBuyer(search));
		int page = Integer.parseInt(request.getParameter("page"));

		List<PaymentDTO> list = dao.searchBuyer(search);

		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);

		List<PaymentDTO> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());

		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}

		model.addAttribute("payList", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("list", templist);

		return "pieContents/shareService/shareServiceApplyConsole";
	}

	/********** 환불요청(페이지 내부) **********/
	@RequestMapping("/refundPayInPage")
	public String refundPayInPage(HttpServletRequest request, Model model) {

		String num = request.getParameter("num");
		String pnum = request.getParameter("pnum");
		String category = request.getParameter("category");

		PaymentDTO dto = dao.payBoard(pnum, "Proxy");

		URL url;
		try {
			url = new URL("https://api.iamport.kr/payments/cancel");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			conn.setRequestMethod("POST");

			// 요청의 Content-Type, Accept, Authorization 헤더 설정
			conn.setRequestProperty("Content-type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", getToken(apiKey, secretKey));

			// 해당 연결을 출력 스트림(요청)으로 사용
			conn.setDoOutput(true);

			// JSON 객체에 해당 API가 필요로하는 데이터 추가.

			JSONObject json = new JSONObject();
			json.put("merchant_uid", dto.getPay_Merchant_uid());
			json.put("reason", "free");

			// 출력 스트림으로 해당 conn에 요청
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(json.toString());
			bw.flush();
			bw.close();

			// 입력 스트림으로 conn 요청에 대한 응답 반환
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			br.close();
			conn.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dao.refundPay(pnum);
		paDao.cancelBuying(dto.getBuyer_id(), category, num);
		return "redirect:/viewProxyBoard?num=" + num;
	}

	public String getToken(String apiKey, String secretKey) throws IOException {
		URL url = new URL("https://api.iamport.kr/users/getToken");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		// 요청 방식을 POST로 설정
		conn.setRequestMethod("POST");

		// 요청의 Content-Type과 Accept 헤더 설정
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");

		// 해당 연결을 출력 스트림(요청)으로 사용
		conn.setDoOutput(true);

		JSONObject json = new JSONObject();
		json.put("imp_key", apiKey);
		json.put("imp_secret", secretKey);

		// 출력 스트림으로 해당 conn에 요청
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		bw.write(json.toString()); // json 객체를 문자열 형태로 HTTP 요청 본문에 추가
		bw.flush(); // BufferedWriter 비우기
		bw.close(); // BufferedWriter 종료

		// 응답 읽기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder responseBuilder = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			responseBuilder.append(line);
		}
		br.close();

		// JSON 파싱
		JSONParser parser = new JSONParser();
		JSONObject jsonResponse;
		String accessToken = "";
		try {
			jsonResponse = (JSONObject) parser.parse(responseBuilder.toString());
			JSONObject response = (JSONObject) jsonResponse.get("response");
			accessToken = (String) response.get("access_token");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 연결 종료
		conn.disconnect();

		return accessToken;
	}

	/********** 환불요청 **********/
	@RequestMapping("/refundPay")
	public String refundPay(HttpServletRequest request, Model model) {
		PaymentDTO dto = new PaymentDTO();
		String num = request.getParameter("num");

		dto.setPay_refund("1");

		dao.refundPay(num);
		return "redirect:/shareServicebuyBoard";
	}

	/********** 환불요청 확인 **********/
	@RequestMapping("/refundPayCheck")
	public String refundPayCheck(@RequestParam("page") int page, @RequestParam("category") String category,
			HttpServletRequest request, Model model) {

		ShareServiceDto sdto = new ShareServiceDto();

		String num = request.getParameter("num");
		String pnum = request.getParameter("pnum");
		PaymentDTO dto = dao.payBoard(num, "Share");
		System.out.println(pnum);
		System.out.println(dto);

		if (category.equals("Share")) {
			Sdao.refundNowPerson(pnum);
		} else if (category.equals("Proxy")) {
			Pdao.refundNowPerson(pnum);
		}
		dao.refundPayCheck(num);
//		 취소
		paDao.cancelBuying(pnum, "Share", dto.getBuyer_id());

		List<PaymentDTO> list = dao.paymentList();
		model.addAttribute("pay", list);

		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);

		List<PaymentDTO> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());

		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}

		System.out.println(templist.size());

		model.addAttribute("payList", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "pieContents/shareService/shareServiceApplyConsole";
	}

	/********** 기간만료로 인한 전체 환불 **********/
	@RequestMapping("/allRefund")
	public String allRefund(@RequestParam("page") int page, HttpServletRequest request, Model model) {

		String num = request.getParameter("num");

		dao.allProcessRefund(Integer.parseInt(num));
		Sdao.dateOver(Integer.parseInt(num));

		List<PaymentDTO> list = dao.paymentList();
		model.addAttribute("pay", list);

		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);

		List<PaymentDTO> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());

		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}

		System.out.println(templist.size());

		model.addAttribute("payList", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "pieContents/shareService/shareServiceApplyConsole";
	}

	@PostMapping("/getPayData")
	public ResponseEntity<PaymentDTO> insertPayment(@RequestParam("num") String num,
			@RequestParam("category") String category) {
		return ResponseEntity.ok(dao.payBoard(num, category));
	}
}
