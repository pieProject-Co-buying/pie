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
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ProxyApplyBoardDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;
import com.pie.pieProject.DTO.TownBuyBoardDto;
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
	IParticipateCheckDao paDao;
	@Autowired
	IProxyBuyDao rdao;


	/********** 전체 게시물 조회 **********/
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(@RequestParam(value="category", required = false)String category,@RequestParam(value="search", required = false) String key, Model model) {
		List<ShareServiceDto> list = new ArrayList<>();
		
		if(category==null||category.equals("")||key==null||key.equals("")) {
			list = dao.getBoardList();
		}else if((category==null||category.equals(""))&&key!=null&&!key.equals("")) {
			list = dao.searchBoard(key);
		}else if(category!=null&&!category.equals("")&&(key==null||key.equals(""))) {
			
		}else if(category!=null&&!category.equals("")&&key!=null&&!key.equals("")) {
			list= dao.searchTitle(category, key);
		}
			
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
		
//		다른 게시글 리스트
		List<ShareServiceDto> list = Bcomp.translateShareList(dao.getBoardList());
		
//		조회수
		dao.updateHit(num);
		
		dto.setSh_productImgs(Bcomp.setArraysData(dto.getSh_productImg(), "/"));
		if(dto.getSh_tag()==null||dto.getSh_tag().equals("#")) {
			dto.setSh_tags(null);
		}else {
			dto.setSh_tags(Bcomp.setArraysData(dto.getSh_tag(), "#"));
		}
		
		if(dto.getSh_personnelMax()<=dto.getSh_personnelNow()) {
			dao.maxChk(Integer.parseInt(sId));
		}
		
//		좋아요
		String table = "shareServiceBoard";
		if (ldao.checkLike(Bcomp.getSession(request, "userId"), sId, table) > 0) {
			model.addAttribute("like", true);
		} else {
			model.addAttribute("like", false);
		}
		
//	    참여멤버 목록
	    List<MemberDto> partilist = paDao.getPartiMem(num, "Share");
	    
		model.addAttribute("partiMem", partilist);
		model.addAttribute("partiMemTotal", partilist.size());
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

	/********** 제목, 내용, 카테고리 기반 검색 **********/
	@RequestMapping("/searchTitle")
	public String search(HttpServletRequest request, Model model) {
		String sId = request.getParameter("search");
		String category="";
		
		if(request.getParameter("OTT").equals("OTT")) {
			category="OTT";
		}else if(request.getParameter("game").equals("game")) {
			category="game";
		}else if(request.getParameter("bookAndMusic").equals("bookAndMusic")) {
			category="game";
		}
		model.addAttribute("list", dao.searchTitle(sId,category));
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

		ShareServiceDto dto = new ShareServiceDto();
		MemberDto mdto = mdao.find(Bcomp.getSession(request, "userId"));

		dto.setSh_id(Bcomp.getSession(request, "userId"));

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
		
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		
		model.addAttribute("list", templist);
		return"pieContents/shareService/shareServiceBoardConsole";
	}
	/**********admin 게시물관리 페이지 진행여부**********/
	@RequestMapping("/processRemote")
	public String processRemote(HttpServletRequest request, Model model) {
		String sId = request.getParameter("num");
		String search = request.getParameter("search");
		int page = Integer.parseInt(request.getParameter("page"));
		ShareServiceDto dto = dao.selectBoard(Integer.parseInt(sId));
		
		if(dto.getSh_process().equals("1")) {			
			dao.stopChk(Integer.parseInt(sId));
		}else if(dto.getSh_process().equals("2")) {
			dao.minChk(Integer.parseInt(sId));			
		}
		
		List<ShareServiceDto> list = dao.searchBuyer(search);
		
		int pageLimit = 10;
		int pageNum = (int) Math.ceil((double) list.size() / pageLimit);
		
		List<ShareServiceDto> templist = new ArrayList<>();

		int minPage = (page - 1) * pageLimit;
		int maxPage = Math.min(page * pageLimit, list.size());
		
		for (int i = minPage; i < maxPage; i++) {
			templist.add(list.get(i));
		}
		
		
		model.addAttribute("list", templist);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		
		model.addAttribute("list", templist);
		return "redirect:/shareServiceBoardConsole?page=1";
	}
}