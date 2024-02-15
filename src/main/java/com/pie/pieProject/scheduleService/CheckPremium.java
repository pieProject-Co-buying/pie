package com.pie.pieProject.scheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pie.pieProject.DAO.IMemberDao;


@Component
public class CheckPremium {
	@Autowired
	IMemberDao dao;

	@Scheduled(fixedDelay = 60000)
	public void checkPre() {
		dao.expireSub();
	}
	

}
