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

import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DTO.ProxyBuyBoardDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProxyBuyController {
	@Autowired
	IProxyBuyDao dao;
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
		model.addAttribute("board",dao.getView(num));
		return "pieContents/proxyBuyProduct";
	}
	
	@GetMapping("/proxyWriteForm")
	public String proxyWriteForm() {
		return "pieContents/proxyForm";
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
	public String proxyuploadAction(@RequestParam("pr_title") String title, @RequestParam("pr_content") String content, @RequestParam("pr_files") String pictures) {
		System.out.println(title);
		System.out.println(content);
		System.out.println(pictures);
		
		dao.insertProxyBoard(title,content);
		return "redirect:/proxyBuyProducts";
	}

}
