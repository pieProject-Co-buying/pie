package com.pie.pieProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.pie.pieProject.components.CustomAutenticationSuccess;
import com.pie.pieProject.components.LoginFailHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAutenticationSuccess customAutenticationSuccess) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests
				.requestMatchers("/", "/main","/css/**", "/pieFragments/**", "/imgs/**", "/js/**").permitAll()
				.requestMatchers("/townBuySearch","/townBuyproduct","/updateTownProductForm", "/deleteTownProduct","/searchTownBuy","/townBuyingCategoryChoice","/writeTownBoard","/townBuying").authenticated()
				.requestMatchers("/viewProxyBoard","/proxyWriteForm", "/uploadAction","/updateProxyForm","/updateProxyAction","/deleteProxyAction").authenticated()
				.requestMatchers("/updateShareBoardForm","/boardList", "/updateShareBoard","/deleteShareService","/writePost","/insertBoard","/shareServiceApply","/shareServiceFinish").authenticated()
				.requestMatchers("/updateForm","/updateAction", "/subScribe","/reSubScribe","/deleteSubScribe","/outMember").authenticated()
				.requestMatchers("*").permitAll()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/auth")    // POST 요청 (login 창에 입력한 데이터를 처리)
						.usernameParameter("id")
						.passwordParameter("password")
						.defaultSuccessUrl("/")
						.successHandler(customAutenticationSuccess)
						.failureHandler(loginFailHandler())//로그인 실패 시 처리하는 핸들러 등록.
						.permitAll()
						)
				.logout(logout -> logout
						.logoutUrl("/logout")
		                .logoutSuccessUrl("/")
		                )	// logout에 성공하면 /로 redirect
		.sessionManagement(session -> session
        .maximumSessions(1) //세션 최대 허용 수 
        .maxSessionsPreventsLogin(false)); // false이면 중복 로그인하면 이전 로그인이 풀린다.


		return http.build();
	}
	
	@Bean
    public LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }
}
