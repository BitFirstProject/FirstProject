package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common_util.JDBC_Close;

public class CheckTicketDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	LogInDAO lDao = new LogInDAO();
	Scanner sc = new Scanner(System.in);
	
	public void CheckTicketYN() {
		int sel = 0;
		System.out.println("[예매확인]");
		System.out.println("------------------------------");
		System.out.println(" 1. 회원 예매 확인\t2. 비회원 예매 확인");
		System.out.println(" 3. 메인메뉴로");
		System.out.println("------------------------------");
		
		while (true) {
			System.out.print("입력> ");
			String yesNo = sc.nextLine();
			try {
				sel = Integer.parseInt(yesNo);
				if (sel >= 1 && sel <= 3) {
					break;
				} else {
					System.out.println("1 ~ 3 중에 선택해 주세요");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력해 주세요");
			}
		}
		
		switch(sel) {
			case 1 :
				CheckTicketInputID();
				break;
			case 2 :
				nonMemberCheck();
				break;
			case 3 :
				LogInDAO logDao = new LogInDAO();
				logDao.checkMember();
				break;
		}
		
	}
	
	public String CheckTicketInputID() {
		String inputId = "";
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT MEMBERID ";
			sql += "FROM TICKET_VIEW ";
			pstmt = conn.prepareStatement(sql);			
			
			rs = pstmt.executeQuery();
			System.out.println("아이디를 입력하세요.");
			System.out.print("아이디 : ");
			inputId = sc.nextLine();
			System.out.println("");
			while(rs.next()) {
				if (rs.getString(1).equals(inputId)) {
					CheckTicketView(inputId);
					return inputId;
				}
			}
			System.out.println("예약된 영화가 없습니다.");
			System.out.println("다시 입력하시겠습니까? (Y/N)");
			System.out.println("");
			while (true) {
				System.out.print("입력> ");
				String yesNo = sc.nextLine();
				if (yesNo.equalsIgnoreCase("y")) {
					CheckTicketInputID();
					break;
				} else if (yesNo.equalsIgnoreCase("n")) {
					System.out.println("처음 화면으로 돌아갑니다.");
					System.out.println("");
					lDao.checkMember();
				} else {
					System.out.println("다시 입력해주세요.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return inputId;
	}
	
	public void CheckTicketView(String memberid) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "SELECT TICKETINGNO, MOVIENAME, MOVIERATING, TO_CHAR(DATENO, 'YYYY/MM/DD'), STARTTIME, ENDTIME, NAME, SEATROW, SEATCOLUMN " + 
					"FROM TICKET_VIEW  " + 
					"WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String str = "";
				str += "------------------------------------------\n";
				str += "No." + rs.getString("TICKETINGNO") + "\n";
				str += rs.getString("MOVIENAME") + "      ";
				str += rs.getString("MOVIERATING") + "\n";
				str += rs.getString(4) + "\n";
				str += rs.getString("STARTTIME") + " ~ ";
				str += rs.getString("ENDTIME") + "\n";
				str += rs.getString("NAME") + " ";
				str += rs.getString("SEATROW") + "열 ";
				str += rs.getString("SEATCOLUMN") + "번";
				str += "\n------------------------------------------";
				System.out.println(str);
				System.out.println("");
			}
			while (true) {
				System.out.println("'Y' 를 입력하면 메인 메뉴로 돌아갑니다.");
				String yes = sc.nextLine();
				if (yes.equalsIgnoreCase("y")) {
					lDao.checkMember();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public boolean nonMemberCheck() {
		boolean check = false;
		String inputName = "";
		String inputPhone = "";
		System.out.println("이름을 입력하세요.");
		System.out.print("이름> ");
		inputName = sc.nextLine();
		System.out.println();
		System.out.println("전화번호를 입력하세요.");
		System.out.print("전화번호> ");
		inputPhone = sc.nextLine();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT TK.TICKETINGNO, TK.MOVIENAME, TK.MOVIERATING, TO_CHAR(TK.DATENO, 'YYYY/MM/DD'), TK.STARTTIME, TK.ENDTIME, TK.NAME, TK.SEATROW, TK.SEATCOLUMN  "
					+ " FROM TICKET_VIEW TK, MEMBER MB "
					+ " WHERE TK.MEMBERID = MB.MEMBERID "
					+ " AND MB.PASSWORD IS NULL "
					+ " AND MB.NAME = ? "
					+ " AND MB.PHONE = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputName);
			pstmt.setString(2, inputPhone);
			rs = pstmt.executeQuery();
			String yesNo = "";
			while (rs.next()) {
				if (rs.getString(1) == null) {
					System.out.println("일치하는 정보가 없습니다");
					System.out.println("다시 입력하시겠습니까? (Y)");
					System.out.print("입력> ");
					yesNo = sc.nextLine();
					if (yesNo.equalsIgnoreCase("y")) {
						nonMemberCheck();
						return check;
					} else {
						System.out.println("메인 메뉴로 돌아갑니다.");
						lDao.checkMember();
						return check;
					}
				} else {
					check = true;
					String str = "";
					str += "------------------------------------------\n";
					str += "No." + rs.getString("TICKETINGNO") + "\n";
					str += rs.getString("MOVIENAME") + "      ";
					str += rs.getString("MOVIERATING") + "\n";
					str += rs.getString(4) + "\n";
					str += rs.getString("STARTTIME") + " ~ ";
					str += rs.getString("ENDTIME") + "\n";
					str += rs.getString("NAME") + " ";
					str += rs.getString("SEATROW") + "열 ";
					str += rs.getString("SEATCOLUMN") + "번";
					str += "\n------------------------------------------";
					System.out.println(str);
					System.out.println("");
				}
			}
			while (true) {
				System.out.println("'Y' 를 입력하면 메인 메뉴로 돌아갑니다.");
				String yes = sc.nextLine();
				if (yes.equalsIgnoreCase("y")) {
					lDao.checkMember();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return check;
	}
	
	
	
}
