package com.pie.pieProject.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DTO.ShareServiceDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShareServiceController {
	 @Autowired
	 IShareServiceDao dao;
	
	 @RequestMapping("/shareServiceApply") 
	 public String Applyboard(HttpServletRequest request, Model model){ 
	     HttpSession session = request.getSession(); // HttpSession 객체 가져오기
	     String sId = (String) session.getAttribute("userId"); // 세션에서 userId 가져오기
	     if(sId==null) {
	    	 model.addAttribute("alertMessage", "로그인 후 이용하세요.");
	    	 return "pieContents/members/login_form";
	     }
	     model.addAttribute("list", dao.myBoard(sId)); // 모델에 추가
	     return "pieContents/shareServiceApply"; // 뷰 이름 반환
	 }
	 
	 @RequestMapping("/boardList") 
	 public String showBoard(HttpServletRequest request,Model model){ 
		 String sId=request.getParameter("sh_numID");
		 model.addAttribute("board", dao.selectBoard(Integer.parseInt(sId)));
		 return "pieContents/shareServiceProduct"; 
	 }
	@RequestMapping("/shareServiceBoard")
	public String showBoardList(Model model){ 
		model.addAttribute("list", dao.getBoardList());
		return "pieContents/shareServiceBoard";
	}
	@RequestMapping("/writePost")
	public String witePost(){ 
		return "pieContents/shareForm";
	}
	@RequestMapping("/modifyForm")
	public String modify(HttpServletRequest request,Model model){ 
		String sId=request.getParameter("sh_numID");
		model.addAttribute("board", dao.selectBoard(Integer.parseInt(sId)));
		return "pieContents/shareServiceModify";
	}
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,Model model){
		String sId=request.getParameter("sh_numID");
		int id = Integer.parseInt(sId);
		dao.deleteBoard(id);
		return "redirect:/shareServiceBoard";
	}
	@RequestMapping("/updateBoard")
	public String update(HttpServletRequest request,Model model) {
		ShareServiceDto dto=new ShareServiceDto();
		String sh_numID=request.getParameter("sh_numID");
		String sh_title=request.getParameter("sh_title");
		String sh_content=request.getParameter("sh_content");
		String sh_price=request.getParameter("sh_price");
		String sh_personnelMax=request.getParameter("sh_personnelMax");
		String sh_DeadLine=request.getParameter("sh_DeadLine");
		
		int tempNumId = Integer.parseInt(sh_numID);
		
		dto.setSh_numID(tempNumId);
		dto.setSh_title(sh_title);
		dto.setSh_content(sh_content);
		dto.setSh_price(Integer.parseInt(sh_price));
		dto.setSh_personnelMax(Integer.parseInt(sh_personnelMax));
		dto.setSh_DeadLine(sh_DeadLine);
		
		dao.updateBoard(dto);
		
		return "redirect:/boardList?sh_numID="+sh_numID;
	}
	@RequestMapping("/searchTitle")
	public String search(HttpServletRequest request,Model model) {
		String sId=request.getParameter("search");
		model.addAttribute("list", dao.searchTitle(sId));
		 return "pieContents/shareServiceBoard"; 
	}
	@RequestMapping("/insertBoard")
    public String insert(HttpServletRequest request, Model model, @RequestParam("sh_filename") MultipartFile shImg) {
        // 다른 파라미터들 처리
        ShareServiceDto dto = new ShareServiceDto();
        String sh_category = request.getParameter("sh_category");
        String sh_nickname = request.getParameter("sh_nickname");
        String sh_id = request.getParameter("sh_id");
        String sh_title = request.getParameter("sh_title");
        String sh_content = request.getParameter("sh_content");
        String sh_price = request.getParameter("sh_price");
        String sh_process = request.getParameter("sh_process");
        String sh_personnelMax = request.getParameter("sh_personnelMax");
        String sh_DeadLine = request.getParameter("sh_DeadLine");

        // 파일 업로드 처리
        if (!shImg.isEmpty()) {
            try {
                byte[] bytes = shImg.getBytes();
                String fileName = shImg.getOriginalFilename();
                // 파일 저장 경로
                String filePath = "src/main/resources/static/imgs/test/" + fileName;
                // 파일 저장
                Files.write(Paths.get(filePath), bytes);
                // 저장된 파일 DTO에 설정
                dto.setSh_filename(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dto.setSh_category(sh_category);
        dto.setSh_nickname(sh_nickname);
        dto.setSh_id(sh_id);
        dto.setSh_title(sh_title);
        dto.setSh_content(sh_content);
        dto.setSh_price(Integer.parseInt(sh_price));
        dto.setSh_process(sh_process);
        dto.setSh_personnelMax(Integer.parseInt(sh_personnelMax));
        dto.setSh_DeadLine(sh_DeadLine);

        dao.insertBoard(dto);

        return "redirect:/shareServiceBoard";
    }
}
