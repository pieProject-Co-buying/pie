
package com.pie.pieProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pie.pieProject.DAO.IFriendDao;
import com.pie.pieProject.DAO.IMemberAuthDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DTO.MemberDto;
import com.pie.pieProject.components.BoardComp;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private IMemberDao mdao;
	@Autowired
	private IMemberAuthDao adao;
	@Autowired
	private IFriendDao fdao;
	@Autowired
	BoardComp bcomp;

//	sessionId
	public String nowUser(HttpServletRequest request) {
		return bcomp.getSession(request, "userId");
	}
	
//	유저목록 가져오기
	public List<MemberDto> getUserList() {
		return mdao.getUserList();
	}

//	회원 추가
	public void setJoinUser(MemberDto dto) {
		mdao.join(dto);
	}

//	유저 ID로 검색
	public MemberDto getUserById(String id) {
		return mdao.find(id);
	}

// 현재 접속한 사람 정보반환
	public MemberDto getUserNow(HttpServletRequest request) {
		return mdao.find(bcomp.getSession(request, "userId"));
	}

//	내용 업데이트
	public void setUpdateUser(MemberDto dto) {
		mdao.updateMember(dto);
	}

//	패스워드 인코딩
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	구독 설정
	public void setUserNowSub(HttpServletRequest request) {
		mdao.sub(bcomp.getSession(request, "userId"));
	}

// 구독취소
	public void setUserNowDesub(HttpServletRequest request) {
		mdao.unSub(bcomp.getSession(request, "userId"));
	}

//	재구독 설정
	public void setUserNowReSub(HttpServletRequest request) {
		mdao.resub(bcomp.getSession(request, "userId"));
	}

//	회원탈퇴
	public void deleteUser(HttpServletRequest request) {
		String id = bcomp.getSession(request, "userId");
		fdao.outMemberFollow(id);
		adao.deleteAuth(id);
		mdao.deleteMember(id);
//		탈퇴한 회원 게시글 처리 및 결제 처리 해결해야함
	}

//	중복검사
	public int getChkResult(String category, String key) {
		int result = 0;
		if (category.equals("id")) {
			result = mdao.chkDuplicate(key);
		} else if (category.equals("name")) {
			result = mdao.chkNDuplicate(key);
		} else if (category.equals("phone")) {
			result = mdao.chkPDuplicate(key);
		} else if (category.equals("email")) {
			result = mdao.chkEDuplicate(key);
		}
		return result;
	}

// 아이디 찾기
	public String getFindingId(String email, String phone) {
		String id = null;
		if (email != null && !email.equals("")) {
			id = mdao.findByEmail(email);
		} else if (phone != null && !phone.equals("")) {
			System.out.println(formatPhoneNumber(phone));
			id = mdao.findByPhone(formatPhoneNumber(phone));
		}

		if (id != null)
			return bcomp.masking(id);
		else
			return null;
	}
	
	public static String formatPhoneNumber(String phoneNumber) {
        // 정규 표현식을 사용하여 숫자만 추출
        String numbers = phoneNumber.replaceAll("\\D", "");

        // 010 다음에 오는 번호 부분을 3자리, 4자리로 나눔
        String formattedNumber = numbers.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");

        return formattedNumber;
    }
	

	// 비밀번호 찾기
	public int getFindingPw(String id, String email, String phone) {
		int su = 0;
		if (email != null && !email.equals("")) {
			su = mdao.findByEmailId(email, id);
		} else if (phone != null && !phone.equals("")) {
			su = mdao.findByPhoneId(formatPhoneNumber(phone), id);
		}

		return su;
	}
	
//	비밀번호 초기화
	public void initPassword(String id, String password) {
		mdao.initPassword(passwordEncoder().encode(password), id);
	}

//	유저 권한 검색 (ID)
	public String getAuth(String id) {
		return adao.findAuth(id);
	}

//	유저 권한 설정 (ID)
	public void setMemberAuth(String id) {
		adao.joinUser(id);
	}
}
