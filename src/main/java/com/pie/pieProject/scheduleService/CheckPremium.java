package com.pie.pieProject.scheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pie.pieProject.DAO.IMemberDao;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;

@Component
public class CheckPremium {
	@Autowired
	IMemberDao dao;
	@Autowired
	IProxyBuyDao pdao;
	@Autowired
	ITownBuyBoardDao tdao;
	@Autowired
	IShareServiceDao sdao;

	@Scheduled(fixedDelay = 60000)
	public void checkPre() {
		dao.expireSub();
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void run() {
		pdao.expireProxyBuyBoard();
		tdao.expireTownBuyBoard();
		sdao.expireShareServiceBoard();
		System.out.println("완료");
	}

}
