package com.pie.pieProject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DAO.ISubscribeDAO;
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
	
    @PostMapping("/payCheck")
    @PreAuthorize("ADMIN")
    public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
    	dao.insertPayment(dto);
    	System.out.println("pay_refund : "+dto.getPay_refund());
        // 응답 반환
        return new ResponseEntity<>(dto.getPay_category(), HttpStatus.OK);
    }
    /********** 결제완료 페이지 이동 **********/
	@RequestMapping("/shareServiceFinish")
	public String goFinish(HttpServletRequest request, Model model) {
	//@RequestParam(name = "msg", required = false) String msg{
		HttpSession session = request.getSession();
		String uId = (String) session.getAttribute("userId");
		String nid = request.getParameter("num");
		String merchant = request.getParameter("merchant_uid");
		String category = request.getParameter("category");
		
		String productImg = null;
		String title = null;
		int price = 0;		
		
		if(category.equals("Share")) {
			ShareServiceDto bl = Sdao.selectBoard(Integer.parseInt(nid));
			productImg = bl.getSh_productImg();
			title = bl.getSh_title();
			price = bl.getSh_pricePer();
			Sdao.updateNow(Integer.parseInt(nid));
			
			bl = Sdao.selectBoard(Integer.parseInt(nid));
			if(bl.getSh_personnelMax()<=bl.getSh_personnelNow()) {
				Sdao.maxChk(Integer.parseInt(nid));
			}
			
		}else if(category.equals("Proxy")) {
			ProxyBuyBoardDto bl = Pdao.getView(nid);
			productImg = bl.getPr_productImg();
			title = bl.getPr_title();
			price = bl.getPr_pricePer();
			Pdao.updateNow(Integer.parseInt(nid));
			bl = Pdao.getView(nid);
			if(bl.getPr_personnelMax()<=bl.getPr_personnelNow()) {
				Pdao.maxChk(Integer.parseInt(nid));
			}
		}

		model.addAttribute("find", mdao.find(uId));
		model.addAttribute("productImg" , productImg);
		model.addAttribute("title", title);
		model.addAttribute("price", price);
		model.addAttribute("pay", dao.payBoard(Integer.parseInt(merchant),category));
		
		return "pieContents/shareService/shareServiceFinish";
	}
	// 정기결제 (구독)
	@PostMapping("/complete")
	public ResponseEntity<String> insertSubScribe(@RequestBody SubScribeDTO dto){
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
	/**********admin 결제내역 페이지 id,nickname 기반 검색**********/
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
	/**********환불요청**********/
	@RequestMapping("/refundPay")
	public String refundPay(HttpServletRequest request,Model model) {
		PaymentDTO dto=new PaymentDTO();
		String num = request.getParameter("num");
		System.out.println("num="+num);
		
		dto.setPay_refund(1);
		dao.refundPay(Integer.parseInt(num));
		return "redirect:/shareServicebuyBoard";
		
	}
}

