package com.pie.pieProject.controllers;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pie.pieProject.DAO.IMemberSocialDao;
import com.pie.pieProject.DAO.Secure.SHA256;
import com.pie.pieProject.DTO.KakaoApi;
import com.pie.pieProject.DTO.MemberDto;

@Controller
public class SNSLoginController {
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\imgs\\profiles";
	
	@Autowired
	KakaoApi kakaoApi;
	
	@Autowired
	IMemberSocialDao dao;
	
	@GetMapping("/loginOauth2")
	public String kakaoLogin(@RequestParam("code") String code, Model model) {
		// 1. 인가 코드 받기 (@RequestParam String code)

		// 2. 토큰 받기
		String accessToken = kakaoApi.getAccessToken(code);
		System.out.println("chkpoint1");

		// 3. 사용자 정보 받기
		Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

		String email = (String) userInfo.get("email");
		String nickname = (String) userInfo.get("nickname");

		System.out.println("email = " + email);
		System.out.println("nickname = " + nickname);
		System.out.println("accessToken = " + accessToken);
		
		model.addAttribute("nickname", nickname);
		model.addAttribute("accessToken",accessToken);		
		
		if(dao.find(accessToken)!=null) {
			return "/socialLoginAction";
		}
		return "pieContents/members/social_join_form";
	}
	
	
	@PostMapping("/socialJoinAction")
	public String signup(@RequestParam(value = "accessToken") String accessToken,
			@RequestParam(value = "name") String name, @RequestParam(value = "nickname") String nickname,
			@RequestParam(value = "gender") String gender, @RequestParam(value = "email") String email,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "postCode") String postCode,
			@RequestParam(value = "address_main") String address_main,
			@RequestParam(value = "address_sub") String address_sub,
			@RequestParam(value = "agreement") String agreementChk, @RequestParam("profile_pic") MultipartFile file) { // 회원
																														// 가입
		try {
			String profile_pic;
			if (file != null && file.isEmpty()) {
				System.out.println("test");
				profile_pic = "default.png";
			} else {
				StringBuilder fileNames = new StringBuilder();
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename()); // => Returns a {@code
																								// Path} by converting a
																								// path string => 이미지가
																								// 저장되는 경로
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes();
				Files.write(fileNameAndPath, fileSize);

				profile_pic = fileNames.toString();
			}

			boolean agreement = (agreementChk != null);

			// 확인용 출력


			System.out.println(name);
			System.out.println(nickname);
			System.out.println(gender);
			System.out.println(profile_pic);
			System.out.println(email);
			System.out.println(phone);
			System.out.println(postCode);
			System.out.println(address_main);
			System.out.println(address_sub);
			System.out.println(agreement);



			/**/
			String salt = SHA256.createSalt("aa");
			/*
			 * String password_salt = SHA256.encrypt(password, salt);
			 */


			MemberDto dto = new MemberDto();

			dto.setId(accessToken);
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
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/login";
	}
}
