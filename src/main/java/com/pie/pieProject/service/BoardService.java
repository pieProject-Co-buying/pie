package com.pie.pieProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pie.pieProject.DAO.IFeedDao;
import com.pie.pieProject.DAO.IParticipateCheckDao;
import com.pie.pieProject.DAO.IPaymentDAO;
import com.pie.pieProject.DAO.IProxyBuyDao;
import com.pie.pieProject.DAO.ISearchDao;
import com.pie.pieProject.DAO.IShareServiceDao;
import com.pie.pieProject.DAO.ITownBuyBoardDao;
import com.pie.pieProject.components.BoardComp;

@Service
public class BoardService {
	@Autowired
	IProxyBuyDao pdao;
	@Autowired
	ITownBuyBoardDao tdao;
	@Autowired
	IShareServiceDao sdao;
	@Autowired
	BoardComp bcomp;
	@Autowired
	ISearchDao sedao;
	@Autowired
	IFeedDao fdao;
	@Autowired
	IParticipateCheckDao partiDao;
	@Autowired
	IPaymentDAO paydao;
	
}
