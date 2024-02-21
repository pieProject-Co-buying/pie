
package com.pie.pieProject.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pie.pieProject.components.CustomAutenticationSuccess;
import com.pie.pieProject.components.LoginFailHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			CustomAutenticationSuccess customAutenticationSuccess) throws Exception {
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.setMatchingRequestParameterName(null);

		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/main", "/css/**", "/pieFragments/**", "/imgs/**", "/js/**").permitAll()
				.requestMatchers("/join", "/joinAction").permitAll()
				.requestMatchers("/updateForm", "/updateAction", "/subScribe", "/reSubScribe", "/deleteSubScribe",
						"/outMember")
				.authenticated()
				.requestMatchers("/townBuySearch", "/townBuyproduct", "/updateTownProductForm", "/deleteTownProduct",
						"/searchTownBuy", "/townBuyingCategoryChoice", "/writeTownBoard", "/townBuying",
						"/viewProxyBoard", "/proxyWriteForm", "/uploadAction", "/updateProxyForm", "/updateProxyAction",
						"/deleteProxyAction", "/updateShareBoardForm", "/boardList", "/updateShareBoard",
						"/deleteShareService", "/writePost", "/insertBoard", "/shareServiceApply",
						"/shareServiceFinish", "/imageUploading", "/imageUpdating", "/updateHeart", "/map",
						"/proxyBuyApply", "/payCheck")
				.authenticated()
				.requestMatchers("/proxyBuyProducts", "/proxyBuyMain", "/proxyBuyBest", "/shareServiceBoard",
						"/shareService")
				.permitAll().requestMatchers("/room", "chat", "/getRoom", "/createRoom", "/moveChating", "/chating/**")
				.authenticated()).formLogin(login -> login.loginPage("/login") // 처리)
						.usernameParameter("id").passwordParameter("password").loginProcessingUrl("/auth") // POST 요청
						.defaultSuccessUrl("/").successHandler(customAutenticationSuccess)
						.failureHandler(loginFailHandler())// 로그인 실패 시 처리하는
															// // 핸들러 등록.
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/")
						.addLogoutHandler(new LogoutHandler() {
							@Override
							public void logout(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) {
								HttpSession session = request.getSession();
								session.invalidate();
							}
						}).logoutSuccessHandler(new LogoutSuccessHandler() {
							@Override
							public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) throws IOException, ServletException {
								response.sendRedirect("/login");
							}
						}).deleteCookies("remember-me"))
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/room", "chat", "/getRoom", "/createRoom", "/moveChating", "/chating/**", "/socket", "/error")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				/*
				 * .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/login", "POST"))
				 * .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/join", "POST"))//
				 * logout에 성공하면 /로 // // redirect .requireCsrfProtectionMatcher(new
				 * AntPathRequestMatcher("/updateForm", "POST"))
				 * .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/logout", "POST"))
				 */
						)// logout에 성공하면
																										// /로 //
																										// redirect
				.sessionManagement(session -> session.maximumSessions(1) // 세션 최대 허용 수
						.maxSessionsPreventsLogin(false))
				.requestCache(cache -> cache.requestCache(requestCache)); // redirect

		// false이면 중복 로그인하면 이전 로그인이 풀린다.
		return http.build();
	}

	@Bean
	public LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}
}

