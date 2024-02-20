/*
 * package com.pie.pieProject.paymentService;
 * 
 * 
 * import org.springframework.web.servlet.ModelAndView; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import com.pie.pieProject.DAO.PaymentDAO; import
 * com.pie.pieProject.DTO.PaymentDTO;
 * 
 * import jakarta.servlet.http.HttpSession;
 * 
 * public class SharePayService { public String insertMPay(PaymentDTO pay,
 * HttpSession session, RedirectAttributes rttr) { String view = null; String
 * msg = null;
 * 
 * try { PaymentDAO dao; dao.insertMPay(pay);
 * 
 * view = "shareServiceFinish?num=" + pay.getPaynum(); } catch (Exception e){
 * e.printStackTrace(); } return view;
 * 
 * } //결제완료 페이지 컨트롤 public ModelAndView paymentContents(String mpaynum,
 * HttpSession session) { ModelAndView mv = new ModelAndView();
 * 
 * //주문정보 가져오기 SharePayService mpay = mPDao.selectPayment(mpaynum);
 * mv.addObject("mpay", mpay); int gymnum = mpay.getGymnum();
 * 
 * } }
 */