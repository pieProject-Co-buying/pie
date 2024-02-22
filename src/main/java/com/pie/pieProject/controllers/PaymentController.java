package com.pie.pieProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("ADMIN")
    public ResponseEntity<String> insertPayment(@RequestBody PaymentDTO dto) {
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
}


