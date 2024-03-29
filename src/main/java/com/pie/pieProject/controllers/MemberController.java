package com.pie.pieProject.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pie.pieProject.DAO.IMemberAuthDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.Secure.SHA256;
import com.pie.pieProject.DTO.KakaoApi;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.components.BoardComp;
import com.pie.pieProject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {
	@Autowired
	UserService userService;

//	유저 프로필사진 저장 경로
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\static\\imgs\\profiles";

//	로그인
	@GetMapping("/login")
	public String loginPage(HttpServletRequest request, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * social Login 시도 흔적 model.addAttribute("kakaoApiKey",
		 * kakaoApi.getKakaoApiKey()); model.addAttribute("redirectUri",
		 * kakaoApi.getKakaoRedirectUri());
		 */

		if (authentication instanceof AnonymousAuthenticationToken) {
			return "pieContents/members/login_form";
		}
		return "redirect:/";
	}

//	로그인 실패시 이동경로
	@GetMapping("/loginForm")
	public String loginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception, Model model) {

		/*
		 * social Login 시도 흔적 model.addAttribute("kakaoApiKey",
		 * kakaoApi.getKakaoApiKey()); model.addAttribute("redirectUri",
		 * kakaoApi.getKakaoRedirectUri());
		 */
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);

		return "pieContents/members/login_form";
	}

//	@PostMapping("/loginAction")
//	public String loginProcess(HttpServletRequest request, Model model) {
////		String id = request.getParameter("id");
////		String salt = dao.getSalt(id);
////
////		String password = request.getParameter("password");
////		String password_salt = SHA256.encrypt(password, salt);
////
////		MemberDto dto = null;
////		dto = dao.login(id, password_salt);
////		if (dto != null) {
////			HttpSession session = request.getSession(true);
////			session.setAttribute("pic", dto.getProfile_pic());
////			session.setAttribute("userId", id);
////			session.setAttribute("nickName", dto.getNickname());
////			return "redirect:/";
////		} else {
//			return "redirect:/";
////		}
//	}

//	회원가입 페이지
	@GetMapping("/join")
	public String signupPage(Model model, CsrfToken token) { // 회원 가입 페이지
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("_csrf", token);
			return "pieContents/members/join_form";
		}
		return "redirect:/login";
	}

// 회원가입
	@PostMapping("/joinAction")
	public String signup(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password,
			@RequestParam(value = "name") String name, @RequestParam(value = "nickname") String nickname,
			@RequestParam(value = "gender") String gender, @RequestParam(value = "email") String email,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "postCode") String postCode,
			@RequestParam(value = "address_main") String address_main,
			@RequestParam(value = "address_sub") String address_sub,
			@RequestParam(value = "agreement") String agreementChk, @RequestParam("profile_pic") MultipartFile file) { // 회원

		// 프로필 사집 업로드
		try {
			String profile_pic;
			if (file != null && file.isEmpty()) {
				System.out.println("test");
				profile_pic = "default.png";
			} else {
				StringBuilder fileNames = new StringBuilder();
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename()); // => Returns a
																								// {@codePath} by
																								// converting a path
																								// string => 이미지가 저장되는
																								// 경로
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes();
				Files.write(fileNameAndPath, fileSize);

				profile_pic = fileNames.toString();
			}

			boolean agreement = (agreementChk != null);

			// 확인용 출력

			/*
			 * System.out.println(id); System.out.println(password);
			 * System.out.println(name); System.out.println(nickname);
			 * System.out.println(gender); System.out.println(profile_pic);
			 * System.out.println(email); System.out.println(phone);
			 * System.out.println(postCode); System.out.println(address_main);
			 * System.out.println(address_sub); System.out.println(agreement);
			 */

			/*
			 * SHA 256 암호화 String salt = SHA256.createSalt(password); String password_salt =
			 * SHA256.encrypt(password, salt); dto.setSalt(salt);
			 */

			/* 패스워드 인코딩 */
			PasswordEncoder pe = new BCryptPasswordEncoder();
			String encoded_password = pe.encode(password);

			MemberDto dto = new MemberDto();

			dto.setId(id);
			dto.setPassword(encoded_password);

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

			userService.setJoinUser(dto);
			userService.setMemberAuth(id);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		return "redirect:/login";
	}

	/*
	 * @PostMapping("/joinAction") public String joinProcess(@RequestParam(value =
	 * "id") String id, @RequestParam(value = "password") String password,
	 * 
	 * @RequestParam(value = "name") String name, @RequestParam(value = "nickname")
	 * String nickname,
	 * 
	 * @RequestParam(value = "gender") String gender, @RequestParam(value = "email")
	 * String email,
	 * 
	 * @RequestParam(value = "phone") String phone, @RequestParam(value =
	 * "postCode") String postCode,
	 * 
	 * @RequestParam(value = "address_main") String address_main,
	 * 
	 * @RequestParam(value = "address_sub") String address_sub,
	 * 
	 * @RequestParam(value = "agreement") String
	 * agreementChk, @RequestParam("profile_pic") MultipartFile file, Model model) {
	 * 
	 * System.out.println(file);
	 * 
	 * try {
	 * 
	 * String profile_pic; if(file!=null && file.isEmpty()) {
	 * System.out.println("test"); profile_pic = "default.png"; }else {
	 * StringBuilder fileNames = new StringBuilder(); Path fileNameAndPath =
	 * Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename()); // => Returns a
	 * {@code Path} by converting a path string => 이미지가 저장되는 경로
	 * fileNames.append(file.getOriginalFilename()); byte[] fileSize =
	 * file.getBytes(); Files.write(fileNameAndPath, fileSize);
	 * 
	 * profile_pic = fileNames.toString(); }
	 * 
	 * boolean agreement = (agreementChk != null);
	 * 
	 * // 확인용 출력
	 * 
	 * System.out.println(id); System.out.println(password);
	 * System.out.println(name); System.out.println(nickname);
	 * System.out.println(gender); System.out.println(profile_pic);
	 * System.out.println(email); System.out.println(phone);
	 * System.out.println(postCode); System.out.println(address_main);
	 * System.out.println(address_sub); System.out.println(agreement);
	 * 
	 * 
	 * String salt = SHA256.createSalt(password); String password_salt =
	 * SHA256.encrypt(password, salt);
	 * 
	 * MemberDto dto = new MemberDto();
	 * 
	 * dto.setId(id); dto.setPassword(password_salt); dto.setSalt(salt);
	 * dto.setName(name); dto.setNickname(nickname); dto.setGender(gender);
	 * dto.setProfile_pic(profile_pic); dto.setEmail(email); dto.setPhone(phone);
	 * dto.setPostCode(postCode); dto.setAddress_main(address_main);
	 * dto.setAddress_sub(address_sub); dto.setAgreement(agreement);
	 * 
	 * dao.join(dto);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return "redirect:/"; }
	 */

	/*
	 * @GetMapping("/logout") public String logoutProcess(HttpServletRequest
	 * request) { HttpSession session = request.getSession(); session.invalidate();
	 * return "redirect:/"; }
	 */

//	업데이트 폼
	@GetMapping("/updateForm")
	public String updateFormPage(HttpServletRequest request, Model model) {
		model.addAttribute("mem", userService.getUserNow(request));
		return "/pieContents/members/update_form";
	}

//	업데이트 처리
	@PostMapping("/updateAction")
	public String updateProcess(@RequestParam("name") String name, @RequestParam("nickname") String nickname,
			@RequestParam("gender") String gender, @RequestParam("email") String email,
			@RequestParam("phone") String phone, @RequestParam("postCode") String postCode,
			@RequestParam("address_main") String address_main, @RequestParam("address_sub") String address_sub,
			@RequestParam("profile_pic") MultipartFile file, Model model, HttpServletRequest request)
			throws IOException {
		/*
		 * System.out.println(file); System.out.println(email);
		 */

		MemberDto dto = new MemberDto();
		MemberDto now = userService.getUserNow(request);

		try {
			StringBuilder fileNames = new StringBuilder();
			if (file != null && file.isEmpty()) {
				/* System.out.println("test"); */
				dto.setProfile_pic(now.getProfile_pic());
			} else {
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
				// => Returns a {@code Path} by converting a path string => 이미지가 저장되는 경로
				fileNames.append(file.getOriginalFilename());
				byte[] fileSize = file.getBytes();
				Files.write(fileNameAndPath, fileSize);

				String profile_pic = fileNames.toString();
				dto.setProfile_pic(profile_pic);
			}

			dto.setId(userService.nowUser(request));
			dto.setName(name);
			dto.setNickname(nickname);
			dto.setGender(gender);
			dto.setEmail(email);
			dto.setPhone(phone);
			dto.setPostCode(postCode);
			dto.setAddress_main(address_main);
			dto.setAddress_sub(address_sub);

			userService.setUpdateUser(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("pic", dto.getProfile_pic());
		session.setAttribute("nickName", dto.getNickname());

		return "redirect:/updateForm";
	}

//	접속중인 사람 구독처리
	@GetMapping("/subScribe")
	public String subScribe(HttpServletRequest request, Model model) {
		userService.setUserNowSub(request);
		MemberDto dto = userService.getUserNow(request);

		if (dto.getPremium().equals("pro")) {
			model.addAttribute("msg", 1);
		} else {
			model.addAttribute("msg", 2);
		}
		return "redirect:/updateForm";
	}

// 구독취소 재구독 처리
	@GetMapping("/reSubScribe")
	public String resubScribe(HttpServletRequest request, Model model) {
		userService.setUserNowReSub(request);
		MemberDto dto = userService.getUserNow(request);

		if (dto.getPremium().equals("pro")) {
			model.addAttribute("msg", 1);
		} else {
			model.addAttribute("msg", 2);
		}
		return "redirect:/updateForm";
	}

//	구독취소
	@GetMapping("/deleteSubScribe")
	public String deleteSubScribe(HttpServletRequest request, Model model) {
		userService.setUserNowDesub(request);
		return "redirect:/updateForm";
	}

//	회원탈퇴
	@GetMapping("/outMember")
	public String deleteMember(HttpServletRequest request, Model model, CsrfToken token) {
		userService.deleteUser(request);
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	/* 중복처리 */
//	아이디 중복
	@PostMapping("/checkId")
	public ResponseEntity<Boolean> checkId(@RequestParam("chkId") String chkId) {
		return ResponseEntity.ok(userService.getChkResult("id", chkId) > 0);
	}

// 닉네임 중복
	@PostMapping("/checkNickName")
	public ResponseEntity<Boolean> checkNickName(@RequestParam("chkName") String chkName) {
		/* System.out.println(chkName); */
		return ResponseEntity.ok(userService.getChkResult("id", chkName) > 0);
	}

//	핸드폰 중복
	@PostMapping("/checkPhone")
	public ResponseEntity<Boolean> chkPhone(@RequestParam("chkPhone") String chkPhone) {
		/* System.out.println(chkPhone); */
		return ResponseEntity.ok(userService.getChkResult("id", chkPhone) > 0);
	}

// 	이메일 중복
	@PostMapping("/chkEmail")
	public ResponseEntity<Boolean> chkEmail(@RequestParam("chkEmail") String chkEmail) {
		/* System.out.println(chkEmail); */
		return ResponseEntity.ok(userService.getChkResult("id", chkEmail) > 0);
	}

// 아이디 찾기 폼
	@GetMapping("/findID")
	public String findID(Model model) {
		return "/pieContents/members/findID_form";
	}

// 비밀번호 찾기 폼
	@GetMapping("/findPW")
	public String findPW(Model model) {
		return "/pieContents/members/findPw_form";
	}

//	아이디 찾기
	@PostMapping("/findIdAction")
	public String findIdAction(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone, Model model) {
		String id = userService.getFindingId(email, phone);
		System.out.println(id);

		int msg = 0;
		if (id != null)
			msg = 1;

		/*
		 * System.out.println(email); System.out.println(phone);
		 */
		/*
		 * if (email != null && !email.equals("")) { id = dao.findByEmail(email); } else
		 * if (phone != null && !phone.equals("")) { id = dao.findByPhone(phone); }
		 * 
		 * if (id != null && !id.equals("")) { id = bcomp.masking(id); msg = 1; }
		 */

		/* System.out.println(id); */

		model.addAttribute("msg", msg);
		model.addAttribute("id", id);
		return "/pieContents/members/findResult";
	}

//	패스워드찾기
	@PostMapping("/findPwAction")
	public String findPwAction(@RequestParam("id") String id,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone, Model model) {
		/*
		 * System.out.println(email); System.out.println(phone);
		 */
		/* int su = 0; */

		/*
		 * if (email != null && !email.equals("")) { su = dao.findByEmailId(email, id);
		 * } else if (phone != null && !phone.equals("")) { su =
		 * dao.findByPhoneId(phone, id); }
		 */

		int msg = 0;
		model.addAttribute("id", id);
		if (userService.getFindingPw(id, email, phone) > 0) {
			return "/pieContents/members/ChangePw_form";
		}

		model.addAttribute("msg", msg);
		return "/pieContents/members/findResult";
	}

//	패스워드 변경화면
	@GetMapping("changePw_form")
	public String changePw() {
		return "/pieContents/members/ChangePw_form";
	}

// 패스워드 변경
	@PostMapping("/changePwAction")
	public String findPwAction(@RequestParam("id") String id, @RequestParam("password") String pw, Model model) {
		userService.initPassword(id, pw);
		model.addAttribute("msg", 2);
		return "/pieContents/members/findResult";
	}

}