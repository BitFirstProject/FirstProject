package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class SeatDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	int cntCol = 0;
	ArrayList<String> list = new ArrayList();
	ArrayList<String> spc = new ArrayList();
	
	Scanner sc = new Scanner(System.in);
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public int cntCol (String name) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "SELECT COUNT(*) FROM SEAT WHERE SEATROW = ? AND NAME = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "A");
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cntCol = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return cntCol;
	}
	
	public void listSeat (String name) {
		String sql = "";
		String seat = "";
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			sql = "SELECT SEATROW, SEATCOLUMN, SPECIAL FROM SEAT WHERE NAME = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TicketVO.vo.getTheaterName());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getString(3) == null) {
					seat = rs.getString(1) + rs.getString(2);
				} else {
					seat = rs.getString(1) + rs.getString(2);
					spc.add(rs.getString(1)+ rs.getString(2));
				}
				list.add(seat);
			}
			
			sql = "SELECT S.SEATROW, S.SEATCOLUMN FROM BOOKING_SEAT BS, SEAT S WHERE BS.SEATNO = S.SEATNO "
		 		    + " AND BOOKING_DATE = ?"
			 		+ " AND BS.TIMENO = ? "
			 		+ " AND S.NAME = ? "
			 		+ " ORDER BY SEATROW, SEATCOLUMN";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TicketVO.vo.getDateNo());
			pstmt.setString(2, TicketVO.vo.getTimeNo());
			pstmt.setString(3, TicketVO.vo.getTheaterName());
			rs = pstmt.executeQuery();
			String bSeat = "";
			while (rs.next()) {
				bSeat = rs.getString(1) + rs.getString(2);
				for (int i=0; i<list.size(); i++) {
					if (list.get(i).equals(bSeat)) {
						list.set(i, "X");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public void print() {
		System.out.println();
		System.out.println("\t\t      스크린");
		System.out.println("---------------------------------------------");
		int cnt = 0;
		for (int i=0; i<list.size(); i++) {
			++cnt;
			if (cnt % cntCol == 0) {
				System.out.print(" "+ list.get(i)+ "\n");
			} else {
				System.out.print(" "+ list.get(i)+ "\t");
			}
		}
		System.out.println("---------------------------------------------");
		System.out.print("(");
		for (int i=0; i<spc.size(); i++) {
			if (i == spc.size() - 1) {
				System.out.print(spc.get(i)+ ")\n");
			} else {
				System.out.print(spc.get(i)+ ", ");
			}
		}
		System.out.println("위 좌석은 특별석 (일반석  +1000원)\n");
	}
	
	public void printSeat() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			int cntCol = cntCol(TicketVO.vo.getTheaterName());
			listSeat(TicketVO.vo.getTheaterName());
			
			
			
			String selSeat = "";
			String check = "";


			for (int i=1; i<=TicketVO.vo.getCntCustomer(); i++) {
				print();
				System.out.println("원하시는 좌석을 입력해 주세요. (예 : A3 혹은 a3)");
				Outer :
				while (true) {
					System.out.print("좌석 선택> ");
					selSeat = sc.nextLine().toUpperCase();
					if (selSeat.equalsIgnoreCase("X")) {
						System.out.println("이미 예약된 좌석입니다. 다른 좌석을 선택해주세요!");
						print();
						
					} else if (checkSeat(selSeat) == true) {
						System.out.println("");
						System.out.println("선택하신 좌석은 ["+ selSeat+ "]입니다. ");
						System.out.println("예매를 계속 하시려면 'Y' 를");
						System.out.println("좌석을 다시 선택하시려면 'N' 을 입력해 주세요.");
						Inner :
						while (true) {
							System.out.print("입력> ");
							check = sc.nextLine();
							System.out.println();
							if (check.equalsIgnoreCase("y")) {
								for (int j=0; j<list.size(); j++) {
									if(list.get(j).equalsIgnoreCase(selSeat)) {
										list.set(j, "X");
									}
								}
//								print();
								break Outer;
							} else if (check.equalsIgnoreCase("n")) {
								print();
								break;
							} else {
								System.out.println("");
								System.out.println("'Y' 혹은 'N' 중에 입력해주세요!");
							}
						}
					} else if (checkSeat(selSeat) == false) {
						System.out.println("");
						System.out.println("유효한 좌석을 입력해주세요.");
					}
					
				}
			char[] seatArr = selSeat.toCharArray();
			String seatRow = "";
			String seatCol = "";
			seatRow = String.valueOf(seatArr[0]);
			seatCol = String.valueOf(seatArr[1]);
			
			TicketVO.vo.listSeatColumn.add(seatCol);
			TicketVO.vo.listSeatRow.add(seatRow);
			
			TicketVO.vo.setSeatRow(seatRow);
			TicketVO.vo.setSeatColumn(seatCol);
			setSeatNo();
			}
			System.out.println("좌석 선택이 완료되었습니다.");
			System.out.println("");
			
//			for (int i=0; i<TicketVO.vo.listSeatNo.size(); i++) {
//				System.out.print(TicketVO.vo.listSeatNo.get(i));
//				System.out.print(TicketVO.vo.listSeatRow.get(i));
//				System.out.println(TicketVO.vo.listSeatColumn.get(i));
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public boolean checkSeat(String selSeat) {
		boolean check = false;
		for (int i=0; i<list.size(); i++) {
			if (selSeat.equalsIgnoreCase(list.get(i))) {
				check = true;
			}
		}
		return check;
	}
	
	public void setSeatNo() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT SEATNO FROM SEAT WHERE SEATROW = ? AND SEATCOLUMN = ? AND NAME = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TicketVO.vo.getSeatRow());
			pstmt.setString(2, TicketVO.vo.getSeatColumn());
			pstmt.setString(3, TicketVO.vo.getTheaterName());
			rs = pstmt.executeQuery();
			
			String seatNo = "";
			if (rs.next()) {
				seatNo = rs.getString(1);
			}
			TicketVO.vo.listSeatNo.add(seatNo);
			TicketVO.vo.setSeatNo(seatNo);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
}
