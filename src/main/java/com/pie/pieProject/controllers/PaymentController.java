package com.pie.pieProject.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.PaymentDTO;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;
import com.pie.pieProject.DTO.ShareServiceDto;

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
	
    @PostMapping("/payCheck")
    public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
    	System.out.println("=========================");
    	System.out.println(dto.getBuyer_addr());
    	System.out.println(dto.getBuyer_email());
    	System.out.println(dto.getBuyer_name());
    	System.out.println(dto.getBuyer_nickname());
    	System.out.println(dto.getBuyer_postcode());
    	System.out.println(dto.getPay_uid());
    	System.out.println(dto.getPay_method());
    	System.out.println(dto.getPayMerchant_uid());
    	System.out.println(dto.getPayName());
    	System.out.println(dto.getPayAmount());
    	System.out.println(dto.getPay_category());
    	
    	
    	
    	//HttpServletRequest request = null;
    	//String url=request.getContextPath();
		
		//if(url.equals("boardList")) { String sId = request.getParameter("num"); }
		 
    	dao.insertPayment(dto);
    	    
    	// PaymentDTO method=dao.insertPayment(dto);
    	// model.addAttribute("pay",method);
    	// ShareServiceDto Sdto = new ShareServiceDto();
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
		String cate = request.getParameter("cate");
		
		/*if(Integer.parseInt(msg)==1) {
			model.addAttribute("pay",Pdao.findPay(uId));
		}else {
			return "history.go(-1)";
		}*/
		String productImg = null;
		String title = null;
		String payDay = null;
		int price = 0;		
		
		if(cate.equals("Share")) {
			ShareServiceDto bl = Sdao.selectBoard(Integer.parseInt(nid));
			productImg = bl.getSh_productImg();
			title = bl.getSh_title();
			payDay = "2024-02-21";
			price = bl.getSh_pricePer();
			
		}else if(cate.equals("Proxy")) {
			ProxyBuyBoardDto bl = Pdao.getView(nid);
			productImg = bl.getPr_productImg();
			title = bl.getPr_title();
			payDay = "2024-02-21";
			price = bl.getPr_pricePer();			
		}

		model.addAttribute("find", mdao.find(uId));
		model.addAttribute("productImg" , productImg);
		model.addAttribute("title", title);
		model.addAttribute("payDay", payDay);
		model.addAttribute("price", price);
		
		return "pieContents/shareService/shareServiceFinish";
	}
}
