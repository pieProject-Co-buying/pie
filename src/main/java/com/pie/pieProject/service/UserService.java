
package com.pie.pieProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pie.pieProject.DAO.IMemberAuthDao;
import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DTO.MemberDto;

@Service
public class UserService {

	@Autowired
	private IMemberDao mdao;
	@Autowired
	private IMemberAuthDao adao;

	public List<MemberDto> getUserList() {
		return mdao.getUserList();
	}

	public MemberDto getUserById(String id) {
		return mdao.find(id);
	}

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public String getAuth(String id) {
		return adao.findAuth(id);
	}
}

