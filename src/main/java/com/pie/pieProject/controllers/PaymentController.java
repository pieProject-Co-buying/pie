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
import com.pie.pieProject.DAO.IParticipateCheckDao;
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
	@Autowired
	IParticipateCheckDao paDao;
	
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
			
			// 작성자가 아닐 경우에만 현재 인원 카운트
			if(!bl.getSh_id().equals(uId)) {				
				Sdao.updateNow(Integer.parseInt(nid));
			}
			
			bl = Sdao.selectBoard(Integer.parseInt(nid));
			if(bl.getSh_personnelMax()<=bl.getSh_personnelNow()) {
				Sdao.maxChk(Integer.parseInt(nid));
			}else if(bl.getSh_personnelMax()>bl.getSh_personnelNow()) {
				Sdao.minChk(Integer.parseInt(nid));
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
		if(pageNum<=0) pageNum=1;
		
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
	/**********환불요청(페이지 내부)**********/
	@RequestMapping("/refundPayInPage")
	public String refundPayInPage(HttpServletRequest request,Model model) {
		
		String num = request.getParameter("num");
		String pnum = request.getParameter("pnum");
		String category = request.getParameter("category");
		System.out.println(category);

		PaymentDTO dto=dao.payBoard(Integer.parseInt(pnum), "Share");
		
		dao.refundPay(Integer.parseInt(pnum));
		paDao.cancelBuying(dto.getBuyer_id(), category, num);
		return "redirect:/boardList?num="+num;
	}
	
	/**********환불요청**********/
	@RequestMapping("/refundPay")
	public String refundPay(HttpServletRequest request,Model model) {
		PaymentDTO dto=new PaymentDTO();
		String num = request.getParameter("num");

		dto.setPay_refund("1");
		
		dao.refundPay(Integer.parseInt(num));
		return "redirect:/shareServicebuyBoard";
	}
	/**********환불요청 확인**********/
	 @RequestMapping("/refundPayCheck")
	 public String refundPayCheck(@RequestParam("page") int page,HttpServletRequest request,Model model) { 
		
		 ShareServiceDto sdto =new ShareServiceDto();
		 
		 String num = request.getParameter("num");
		 String pnum = request.getParameter("pnum");
		 PaymentDTO dto=dao.payBoard(Integer.parseInt(pnum), "Share");
		 
		 
		 Sdao.refundNowPerson(Integer.parseInt(pnum));
		 dao.refundPayCheck(Integer.parseInt(num));
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
	 /**********기간만료로 인한 전체 환불**********/
	 @RequestMapping("/allRefund")
	 public String allRefund(@RequestParam("page") int page,HttpServletRequest request,Model model) { 
		
		 
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
	 public ResponseEntity<PaymentDTO> insertPayment(@RequestParam("num") String num, @RequestParam("category") String category) {
	        return ResponseEntity.ok(dao.payBoard(Integer.parseInt(num),category));
	    }
}

