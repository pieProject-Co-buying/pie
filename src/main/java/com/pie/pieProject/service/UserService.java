/*
 * package com.pie.pieProject.service;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.stereotype.Service;
 * 
 * import com.pie.pieProject.DAO.IMemberDao; import
 * com.pie.pieProject.DTO.MemberDto;
 * 
 * 
 * @Service public class UserService{
 * 
 * @Autowired private IMemberDao mdao;
 * 
 * public List<MemberDto> getUserList() { return mdao.getUserList(); }
 * 
 * public MemberDto getUserById(String id) { return mdao.find(id); }
 * 
 * public PasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); } }
 */