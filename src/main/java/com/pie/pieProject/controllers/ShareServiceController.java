package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.tokens.DocumentEndToken;

import com.pie.pieProject.DAO.ILikeDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ProxyApplyBoardDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShareServiceController {
	@Autowired
	IShareServiceDao dao;
	@Autowired
	IMemberDao mdao;
	@Autowired
	ILikeDao ldao;
	@Autowired
	IPaymentDAO Pdao;
	@Autowired
	BoardComp Bcomp;
	@Autowired
	IProxyBuyDao rdao;


	/********** 전체 게시물 조회 **********/
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(Model model) {
		List<ShareServiceDto> list = dao.getBoardList();
		for (ShareServiceDto d : list) {
			if(d.getSh_tag()==null||d.getSh_tag().equals("#")) {
				d.setSh_tags(null);
			}else {
				d.setSh_tags(Bcomp.setArraysData(d.getSh_tag(), "#"));
			}
			String c = d.getSh_category();
			if (c.equals("OTT")) {
				d.setSh_category("OTT");
			} else if (c.equals("game")) {
				d.setSh_category("게임");
			} else if (c.equals("bookAndMusic")) {
				d.setSh_category("도서/음악");
			}
		}

		model.addAttribute("list", list);
		return "pieContents/shareService/shareServiceBoard";
	}

	/********** 게시물 상세 페이지 **********/
	@RequestMapping("/boardList")
	public String showBoard(@RequestParam("num") String num,HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));
		List<ShareServiceDto> list = dao.getBoardList();
		dao.updateHit(num);
		
		dto.setSh_productImgs(Bcomp.setArraysData(dto.getSh_productImg(), "/"));
		if(dto.getSh_tag()==null||dto.getSh_tag().equals("#")) {
			dto.setSh_tags(null);
		}else {
			dto.setSh_tags(Bcomp.setArraysData(dto.getSh_tag(), "#"));
		}

		String table = "shareServiceBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), sId, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		
		model.addAttribute("board", dto);
		model.addAttribute("member",mdto);
		model.addAttribute("list",list);
		return "pieContents/shareService/shareServiceProduct";
	}

	/********** 해당 게시물 수정 페이지 이동 **********/
	@RequestMapping("/updateShareBoardForm")
	public String modify(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");

		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));

		dto.setSh_productImgs(Bcomp.setArraysData(dto.getSh_productImg(), "/"));
		if(dto.getSh_tag()==null||dto.getSh_tag().equals("#")) {
			dto.setSh_tags(null);
		}else {
			dto.setSh_tags(Bcomp.setArraysData(dto.getSh_tag(), "#"));
		}
		/* dao.updateHit(sId); */

		String table = "shareServiceBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), sId, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}

		model.addAttribute("board", dto);

		return "pieContents/shareService/shareServiceUpdate";
	}

	/********** 해당 게시물 수정 **********/
	@RequestMapping("/updateShareBoard")
	public String update(HttpServletRequest request, Model model) {
		ShareServiceDto dto = new ShareServiceDto();
		String sh_numID = request.getParameter("num");
		String sh_title = request.getParameter("sh_title");
		String sh_content = request.getParameter("sh_content");
		String sh_personnelMax = request.getParameter("sh_personnelMax");
		String sh_DeadLine = request.getParameter("sh_deadLine");

		int tempNumId = Integer.parseInt(sh_numID);
		
		System.out.println(sh_numID);
		System.out.println(sh_title);
		System.out.println(sh_content);
		System.out.println(sh_personnelMax);
		System.out.println(sh_DeadLine);
		System.out.println(request.getParameter("price_per"));
		System.out.println(request.getParameter("price_total"));
		System.out.println(request.getParameter("sh_files"));
		System.out.println(request.getParameter("pie_tagsOutput"));

		dto.setSh_num(tempNumId);
		dto.setSh_title(sh_title);
		dto.setSh_content(sh_content);
		dto.setSh_pricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setSh_priceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setSh_personnelMax(Integer.parseInt(sh_personnelMax));
		dto.setSh_productImg(request.getParameter("sh_files"));
		dto.setSh_category(request.getParameter("sh_category"));
		dto.setSh_tag(request.getParameter("pie_tagsOutput"));
		dto.setSh_ip(request.getRemoteAddr());
		dto.setSh_deadLine(sh_DeadLine);

		dao.updateBoard(dto);

		return "redirect:/boardList?num=" + sh_numID;
	}

	/********** 해당 게시물 삭제 **********/
	@RequestMapping("/deleteShareService")
	public String delete(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		int id = Integer.parseInt(sId);
		dao.deleteBoard(id);
		return "redirect:/shareServiceBoard";
	}

	/********** 제목, 내용 기반 검색 **********/
	@RequestMapping("/searchTitle")
	public String search(HttpServletRequest request, Model model) {
		String sId = request.getParameter("search");
		model.addAttribute("list", dao.searchTitle(sId));
		return "pieContents/shareService/shareServiceBoard";
	}

	/********** 게시판 작성 페이지 이동 **********/
	@RequestMapping("/writePost")
	public String witePost() {
		return "pieContents/shareService/shareForm";
	}

	/********** 게시판 작성 **********/
	@RequestMapping("/insertBoard")
	public String insert(HttpServletRequest request, Model model) {
		// 다른 파라미터들 처리
		/*
		 * ShareServiceDto dto = new ShareServiceDto(); String sh_category =
		 * request.getParameter("sh_category"); String sh_nickname =
		 * request.getParameter("sh_nickname"); String sh_id =
		 * request.getParameter("sh_id"); String sh_title =
		 * request.getParameter("sh_title"); String sh_content =
		 * request.getParameter("sh_content"); String sh_price =
		 * request.getParameter("sh_price"); String sh_process =
		 * request.getParameter("sh_process"); String sh_personnelMax =
		 * request.getParameter("sh_personnelMax"); int sh_personnelNow = 0; String
		 * sh_DeadLine = request.getParameter("sh_DeadLine");
		 * 
		 * // 파일 업로드 처리 if (!shImg.isEmpty()) { try { byte[] bytes = shImg.getBytes();
		 * String fileName = shImg.getOriginalFilename(); // 파일 저장 경로 String filePath =
		 * "src/main/resources/static/imgs/test/" + fileName; // 파일 저장
		 * Files.write(Paths.get(filePath), bytes); // 저장된 파일 DTO에 설정
		 * dto.setSh_filename(fileName); } catch (IOException e) { e.printStackTrace();
		 * } }
		 * 
		 * dto.setSh_category(sh_category); dto.setSh_nickname(sh_nickname);
		 * dto.setSh_id(sh_id); dto.setSh_title(sh_title);
		 * dto.setSh_content(sh_content); dto.setSh_price(Integer.parseInt(sh_price));
		 * dto.setSh_process(sh_process); dto.setSh_personnelNow(sh_personnelNow);
		 * dto.setSh_personnelMax(Integer.parseInt(sh_personnelMax));
		 * dto.setSh_DeadLine(sh_DeadLine);
		 * 
		 * dao.insertBoard(dto);
		 */

		ShareServiceDto dto = new ShareServiceDto();
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));

		dto.setSh_id(Bcomp.getSession(request, "userId"));

		System.out.println(request.getParameter("sh_category"));
		dto.setSh_category(request.getParameter("sh_category"));

		if (mdto.getPremium().equals("pro")) {
			dto.setSh_premium("1");
		} else {
			dto.setSh_premium("0");
		}

		dto.setSh_nickname(Bcomp.getSession(request, "nickName"));
		dto.setSh_title(request.getParameter("sh_title"));
		dto.setSh_content(request.getParameter("sh_content"));
		dto.setSh_profileImg(Bcomp.getSession(request, "pic"));
		dto.setSh_productImg(request.getParameter("sh_files"));
		dto.setSh_tag(request.getParameter("pie_tagsOutput"));
		dto.setSh_personnelMax(Integer.parseInt(request.getParameter("sh_personnelMax")));
		dto.setSh_pricePer(Integer.parseInt(request.getParameter("price_per")));
		dto.setSh_priceTotal(Integer.parseInt(request.getParameter("price_total")));
		dto.setSh_ip(request.getRemoteAddr());
		dto.setSh_deadLine(request.getParameter("sh_deadLine"));

		dao.insertBoard(dto);

		return "redirect:/shareServiceBoard";
	}

	/********** 내가 작성한 게시글 조회 **********/
	@RequestMapping("/shareServiceApply")
	public String Applyboard(HttpServletRequest request, Model model) {
		// HttpSession 객체 가져오기
		// 세션에서 userId 가져오기
		String sId = Bcomp.getSession(request, "userId");
		// 세션 id가 null일 경우 로그인 페이지로 이동
		if (sId == null) {
			return "pieContents/members/login_form";
		}
		// 모델에 추가
		model.addAttribute("list", dao.myBoard(sId));

		System.out.println(dao.myBoard(sId));
		// 뷰 이름 반환
		return "pieContents/shareService/shareServiceApply";
	}
	// 개인 사용자 구매 내역
	@RequestMapping("/shareServicebuyBoard")
	public String buyList(HttpServletRequest request, Model model) {
		// HttpSession 객체 가져오기
		// 세션에서 userId 가져오기
		String sId = Bcomp.getSession(request, "userId");
		
		// 세션 id가 null일 경우 로그인 페이지로 이동
		// 모델에 추가
		
		
		List<PaymentDTO> list = Pdao.buyList(sId);
		List<String> piclist = Pdao.buyListpic(sId);
		List<String> processList = Pdao.buyListpro(sId);
		List<String> buyNum = Pdao.buyListNum(sId);
		
		
		
		for(int i = 0; i<list.size(); i++) {
			list.get(i).setProductImg(piclist.get(i));
			list.get(i).setProcess(processList.get(i));
			list.get(i).setNum(buyNum.get(i));
			
		}
		
		model.addAttribute("list", list);
		// 뷰 이름 반환
		return "pieContents/shareService/shareServicebuyBoard";
	}
	/**********************admin 게시글 관리**********************/
	@RequestMapping("/shareServiceBoardConsole")
	public String boardConsole(@RequestParam("page") int page, @RequestParam(value="search", required=false) String search, Model model) {
		List<ShareServiceDto> list = dao.getBoardList();
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		System.out.println(templist.size());
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "pieContents/shareService/shareServiceBoardConsole";
	}
	/**********admin 게시물관리 페이지 id,nickname 기반 검색**********/
	@RequestMapping("/searchBoardName")
	public String searchBuyer(HttpServletRequest request, Model model) {
		String search = request.getParameter("search");
		int page = Integer.parseInt(request.getParameter("page"));
		
		List<ShareServiceDto> list = dao.searchBuyer(search);
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		System.out.println(templist.size());
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		
		model.addAttribute("list", templist);
		return"pieContents/shareService/shareServiceBoardConsole";
	}
	/********** admin 게시글 관리 필터 **********/
	/*
	 * @RequestMapping("/consoleFilter") public String goFinish(HttpServletRequest
	 * request, Model model) { String category = request.getParameter("category");
	 * 
	 * int num = 0; String id = null; String nickname = null; String title = null;
	 * String registDay = null; String deadLine = null; String process = null;
	 * 
	 * if(category.equals("Share")) { List<ShareServiceDto> list =
	 * dao.getBoardList(); for(ShareServiceDto dto : list) { num=dto.getSh_num();
	 * id=dto.getSh_id(); nickname=dto.getSh_nickname(); title=dto.getSh_title();
	 * registDay=dto.getSh_registDay(); deadLine=dto.getSh_deadLine();
	 * process=dto.getSh_process(); }
	 * 
	 * }else if(category.equals("Proxy")) {; List<ProxyBuyBoardDto> list =
	 * rdao.listDao(); for(ProxyBuyBoardDto dto : list) {
	 * num=Integer.parseInt(dto.getPr_num()); id=dto.getPr_id();
	 * nickname=dto.getPr_nickname(); title=dto.getPr_title();
	 * registDay=dto.getPr_registDay(); deadLine=dto.getPr_deadLine();
	 * process=dto.getPr_process(); } }
	 * 
	 * model.addAttribute("num",num); model.addAttribute("id",id);
	 * model.addAttribute("nickname",nickname); model.addAttribute("title",title);
	 * model.addAttribute("registDay",registDay);
	 * model.addAttribute("deadLine",deadLine);
	 * model.addAttribute("process",process); return
	 * "pieContents/shareService/shareServiceFinish?category="; }
	 */
}