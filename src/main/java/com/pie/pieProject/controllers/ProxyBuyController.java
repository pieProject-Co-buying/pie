package com.pie.pieProject.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProxyBuyController {
	@Autowired
	IProxyBuyDao dao;
	@Autowired
	IMemberDao mdao;
	
	public static String UPLOAD_DIRECTORY2 = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\imgs\\test";

	
	@GetMapping("/proxyBuyProducts")
	public String getList(Model model) {
		List<ProxyBuyBoardDto> list1 = new ArrayList<>();
		List<ProxyBuyBoardDto> list2 = new ArrayList<>();
		
		int max1 = 3;
		int max2 = 3;
		
		if(list1.size()<3) {
			max1=dao.listDaoByNewer().size();
			max2=dao.listDaoByCategory("육아").size();
		}
		
		
		for(int i=0; i<max1; i++) {
			list1.add(dao.listDaoByNewer().get(i));
		}
	
		
		for(int i=0; i<max2; i++) {
			list2.add(dao.listDaoByCategory("육아").get(i));
		}
		
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		model.addAttribute("allList",dao.listDao());
		return "pieContents/proxyBuyProducts"; 
	}
	
	@GetMapping("/viewProxyBoard")
	public String getView(@RequestParam("num") String num, HttpServletRequest request, Model model) {
		ProxyBuyBoardDto dto = dao.getView(num);
		
		dto.setPr_productImgs(setArraysData(dto.getPr_productImg(), "/"));
		dto.setPr_tags(setArraysData(dto.getPr_tag(), "#"));
		dao.updateHit(num);
		
		
		String table = "proxyBuyBoard";
		if(dao.checkLike(getSession(request, "userId"), num, table)>0) {
			model.addAttribute("like", true);
		}else {
			model.addAttribute("like", false);
		}
		
		model.addAttribute("board",dto);
		return "pieContents/proxyBuyProduct";
	}
	
	@GetMapping("/proxyWriteForm")
	public String proxyWriteForm() {
		return "pieContents/proxyForm";
	}
	
	@PostMapping("/updateHeart")
	public ResponseEntity<Integer> updateHeartAction(@RequestParam("num") String num, @RequestParam("tableName")String tableName, HttpServletRequest request) {
		System.out.println(num);
		
		String table = null;
		
		if(tableName.equals("p")) {
			table = "proxyBuyBoard";
		}
		
		if(dao.checkLike(getSession(request, "userId"), num, table)>0) {
			dao.LikeMinus(getSession(request, "userId"),num, table);
		}else {
			dao.LikePlus(getSession(request, "userId"),num, table);
		}
		dao.countLike(num, table);
		System.out.println(table);
		System.out.println(getSession(request, "userId"));
		System.out.println(dao.getView(num).getPr_like());
				
		return ResponseEntity.ok(dao.getView(num).getPr_like());
	}
	
	@PostMapping("/testUpload")
	public ResponseEntity<String> proxyuploadAction(@RequestParam("attach_file") MultipartFile[] files) {
		
		StringBuilder fileData = new StringBuilder();
		 try {
			 for (MultipartFile file : files) {
		            System.out.println("Uploaded File Name: " + file.getOriginalFilename());
		            StringBuilder fileNames = new StringBuilder();
					Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY2, file.getOriginalFilename());
					// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
					fileNames.append(file.getOriginalFilename());
					byte[] fileSize = file.getBytes();
					Files.write(fileNameAndPath, fileSize);
					System.out.println(fileNames+"업로드완료");
					
					fileData.append(file.getOriginalFilename());
					fileData.append("/");
		        }
		 }catch(Exception e) {
			 e.getStackTrace();    
		 }
		
		return ResponseEntity.ok(fileData.toString());
	}
	
	@PostMapping("/uploadAction")
	public String proxyuploadAction(@RequestParam("pr_title") String title, @RequestParam("pr_content") String content, @RequestParam("pr_files") String pictures, @RequestParam("pie_tagsOutput")String tags,  @RequestParam("pr_deadLine")String deadLine, @RequestParam("pr_personnelMax")String pr_personnelMax, @RequestParam("price_total")String pr_priceTotal, @RequestParam("price_per")String pr_pricePer,@RequestParam("pr_category") String pr_category, HttpServletRequest request) {
		
		ProxyBuyBoardDto dto = new ProxyBuyBoardDto();
		MemberDto mdto = mdao.find(getSession(request,"userId"));
		
		dto.setPr_id(getSession(request,"userId"));
		dto.setPr_category(pr_category);
		if(mdto.getPremium().equals("pro")) {
			dto.setPr_premium("1");
		}else {
			dto.setPr_premium("0");
		}
		dto.setPr_nickname(getSession(request,"nickName"));
		dto.setPr_title(title);
		dto.setPr_content(content);
		dto.setPr_profileImg(getSession(request,"pic"));
		dto.setPr_productImg(pictures);
		

		dto.setPr_tag(tags);
		dto.setPr_deadLine(deadLine);
		dto.setPr_personnelMax(Integer.parseInt(pr_personnelMax));
		dto.setPr_priceTotal(Integer.parseInt(pr_priceTotal));
		dto.setPr_pricePer(Integer.parseInt(pr_pricePer));
		dto.setPr_ip(request.getRemoteAddr());
		dao.insertProxyBoard(dto);
		
		return "redirect:/proxyBuyProducts";
	}

	private String getSession(HttpServletRequest request, String key) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(key);
	}
	
	private String[] setArraysData(String key, String wallWord) {
		String[] str_imgs = key.split(wallWord);
		for(String s : str_imgs) {
			s.replace(wallWord, "");
		}
		return str_imgs;
	}
	
}


