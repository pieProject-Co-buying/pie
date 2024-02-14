package com.pie.pieProject.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.Secure.SHA256;
import com.pie.pieProject.DTO.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	IMemberDao dao;
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\imgs\\profiles";

	@PostMapping("/loginAction")
	public String loginProcess(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String salt = dao.getSalt(id);
		
		String password = request.getParameter("password");
		String password_salt = SHA256.encrypt(password, salt);
		
		MemberDto dto = null;
		dto = dao.login(id, password_salt);
		if (dto != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("pic", dto.getProfile_pic());
			session.setAttribute("userId", id);
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}

	@PostMapping("/joinAction")
	public String joinProcess(@RequestParam(value="id") String id, @RequestParam(value="password") String password, @RequestParam(value="name") String name, @RequestParam(value="nickname") String nickname, @RequestParam(value="gender") String gender, @RequestParam(value="email") String email, @RequestParam(value="phone") String phone, @RequestParam(value="postCode") String postCode, @RequestParam(value="address_main") String address_main, @RequestParam(value="address_sub") String address_sub, @RequestParam(value="agreement") String agreementChk, @RequestParam("profile_pic") MultipartFile file, Model model) {
		
		System.out.println("test");
		
		try {
			StringBuilder fileNames = new StringBuilder();
			Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
			// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
			fileNames.append(file.getOriginalFilename());
			byte[] fileSize = file.getBytes();
			Files.write(fileNameAndPath, fileSize);
		

		String profile_pic= fileNames.toString();

		boolean agreement = (agreementChk!=null);
		
		// 확인용 출력
		/*
		 * System.out.println(id); System.out.println(password);
		 * System.out.println(name); System.out.println(nickname);
		 * System.out.println(gender); System.out.println(profile_pic);
		 * System.out.println(email); System.out.println(phone);
		 * System.out.println(postCode); System.out.println(address_main);
		 * System.out.println(address_sub); System.out.println(agreement);
		 */
		
		String salt = SHA256.createSalt(password);
		String password_salt = SHA256.encrypt(password, salt);
		
		MemberDto dto = new MemberDto();
		
		dto.setId(id);
		dto.setPassword(password_salt);
		dto.setSalt(salt);
		dto.setName(name);
		dto.setNickname(nickname);
		dto.setGender(gender);
		dto.setProfile_pic(profile_pic);
		dto.setEmail(email);
		dto.setPhone(phone);
		dto.setPostCode(postCode);
		dto.setAddress_main(address_main);
		dto.setAddress_sub(address_sub);
		dto.setAgreement(agreement);
		
		dao.join(dto);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logoutProcess(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

}
