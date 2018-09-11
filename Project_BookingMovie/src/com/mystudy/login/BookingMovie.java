package com.mystudy.login;

import java.util.Scanner;
import common_util.driverInterface;
import common_util.JDBC_Close;

public class BookingMovie {
	public static void main(String[] args) {
		LogInDAO logDao = new LogInDAO();
		DateDAO dateDao = new DateDAO();
		TheaterDAO theaterDao = new TheaterDAO();
		SeatDAO seatDao = new SeatDAO();
		PayDAO payDao = new PayDAO();
		TicketDAO ticketDao = new TicketDAO();
		CountCustomerDAO cntDao = new CountCustomerDAO();
		
		ManagerMenuDAO d = new ManagerMenuDAO();
//		d.managerMenu();
//		d.giveRank();
//		d.checkAttendence();
		
//		TicketVO.vo.setTheaterName("1관");
//		TicketVO.vo.setCntCustomer(4);
		
//		CheckTicketDAO chDao = new CheckTicketDAO();
//		chDao.CheckTicketYN();
		
		logDao.checkMember();			// 로그인
		MovieDAO movieDao = new MovieDAO();
		movieDao.rounder();				// 영화 선택
		dateDao.selectDate();			// 날짜 선택
		theaterDao.selectTheater();		// 상영관 선택
		cntDao.selPeople();				// 인원 선택
		seatDao.printSeat();			// 좌석 선택
		payDao.printPrice();			// 결제 
		ticketDao.insertTicket();		// 티켓 출력
		
//		ManagerMenuDAO d = new ManagerMenuDAO();
//		d.manageMovieMenu();
		
		
	}
}
