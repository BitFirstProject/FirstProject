package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import common_util.JDBC_Close;

public class TicketDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	String seatNos;
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public void insertTicket() {
		ArrayList<String> list = new ArrayList<String>();
		Random ran = new Random();
		System.out.println("티켓을 출력중입니다.");
		System.out.println();
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String ticNo = "";
			String sql = "";
			String ranNo = "";
			sql = "SELECT TICKETINGNO FROM TICKETING";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			
			
			sql = "INSERT INTO TICKETING (TICKETINGNO, THEATERNO, SEATNO, MOVIENO, MEMBERID, TIMENO, PRICE, DATENO) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY/MM/DD'))";
			pstmt = conn.prepareStatement(sql);
			for (int i=0; i<TicketVO.vo.listSeatNo.size(); i++) {
				Outer :
				while (true) {
					ranNo = String.valueOf(ran.nextInt(9999999));
					for (int j=0; j<list.size(); j++) {
						if (!list.get(j).equals(ranNo)) {
							break Outer;
						}
					}
				}
				TicketVO.vo.setTicketingNo(ranNo);
				pstmt.setString(1, ranNo);
				pstmt.setString(2, TicketVO.vo.getTheaterNo());
				pstmt.setString(3, TicketVO.vo.listSeatNo.get(i));
				pstmt.setString(4, TicketVO.vo.getMovieNo());
				pstmt.setString(5, TicketVO.vo.getMemberId());
				pstmt.setString(6, TicketVO.vo.getTimeNo());
				pstmt.setInt(7, TicketVO.vo.getPrice());
				pstmt.setString(8, TicketVO.vo.getDateNo());
				pstmt.executeUpdate();
			}
				
			
			insertBooking_Seat();
			printTicket();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public void insertBooking_Seat() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "INSERT INTO BOOKING_SEAT (BOOKING_DATE, TIMENO, SEATNO) "
					+ " VALUES (TO_DATE(?, 'YYYY/MM/DD'), ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for (int i=0; i<TicketVO.vo.listSeatNo.size(); i++) {
				pstmt.setString(1, TicketVO.vo.getDateNo());
				pstmt.setString(2, TicketVO.vo.getTimeNo());
				pstmt.setString(3, TicketVO.vo.listSeatNo.get(i));
				pstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmt(conn, pstmt);
		}
	}
	
	public void setData() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "";
			
			
			sql = "SELECT MOVIENAME, MOVIERATING FROM MOVIE WHERE MOVIENO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TicketVO.vo.getMovieNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				TicketVO.vo.setMovieName(rs.getString("MOVIENAME"));
				TicketVO.vo.setMovieRating(rs.getString("MOVIERATING"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	
	public void printTicket() {
		int ns = TicketVO.vo.getMovieName().length();
		System.out.println("===================================");
		System.out.println(" Movie Ticket\t\tNo." + TicketVO.vo.getTicketingNo());
		System.out.println("-----------------------------------");
		if (ns > 7) {
			System.out.println("  " + TicketVO.vo.getMovieName() + "\t" + TicketVO.vo.getMovieRating());
		} else if (ns < 3 || ns == 5) {
			System.out.println("  " + TicketVO.vo.getMovieName() + "\t\t\t" + TicketVO.vo.getMovieRating());
		} else if (ns <= 7) {
			System.out.println("  " + TicketVO.vo.getMovieName() + "\t\t" + TicketVO.vo.getMovieRating());
		}
		
		System.out.println("-----------------------------------");
		System.out.println(" " + TicketVO.vo.getDateNo() + "           " + TicketVO.vo.getStartTime() + " ~ " + TicketVO.vo.getEndTime());
		System.out.print(" " + TicketVO.vo.getTheaterName());
		for (int i=0; i<TicketVO.vo.listSeatColumn.size(); i++) {
			System.out.println("      " + TicketVO.vo.listSeatRow.get(i) + "열 " + 
					TicketVO.vo.listSeatColumn.get(i) + "번");
		}
		System.out.println("-----------------------------------");
		System.out.println(" " + TicketVO.vo.getMemberId() + " 님" + "\t\t요금 : " + TicketVO.vo.getPrice() + "원");
		System.out.println("===================================");
	}
	
	
	
	
	
}
