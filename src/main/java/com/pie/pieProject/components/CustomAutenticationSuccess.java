package com.pie.pieProject.components;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DTO.MemberDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAutenticationSuccess extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private IMemberDao mdao;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        // Authentication 객체에서 사용자 정보 가져오기
        String username = authentication.getName();

        // 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        MemberDto dto = mdao.find(username);
        
        session.setAttribute("userId", username);
        session.setAttribute("nickName", dto.getNickname());
        session.setAttribute("pic", dto.getProfile_pic());

        // 로그인 후 리다이렉트할 URL 설정
        // 기본적으로 부모 클래스에서는 SavedRequest에 저장된 URL로 리다이렉트됩니다.
        // 여기서는 직접 설정하거나, 부모 클래스의 메서드를 호출하여 사용할 수 있습니다.
        // setDefaultTargetUrl("/custom-url");
    }
}