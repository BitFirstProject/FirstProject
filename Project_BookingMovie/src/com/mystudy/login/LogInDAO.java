package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import common_util.JDBC_Close;

public class LogInDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	Scanner sc = new Scanner(System.in);
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	//static 초기화 구문
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	// ----------------------------------- 로그인  시작 --------------------------------------------
	public void checkMember() {
		while (true) {
			System.out.println();
			System.out.println("\t**  **   *****   ***** ");
			System.out.println("\t** **       **   **   **");
			System.out.println("\t****        **   **    **");
			System.out.println("\t** **   **  **   **   **");
			System.out.println("\t**  **   ***     *****");
			System.out.println();
			System.out.println("        KJD 시네마에 오신걸 환영합니다!");
			System.out.println("                   원하시는 서비스를 선택해주세요!");
			System.out.println("------------------------------------------");
			System.out.println("1. 로그인\t\t2. 회원가입\t3. 비회원 예매");
			System.out.println("4. 예매 확인\t5. 관리자모드\t6. 종료");
			System.out.println("------------------------------------------");
			System.out.print("입력> ");
			String sel = "";
			sel = sc.nextLine();
			int selNum = 0;
			try {
				selNum = Integer.parseInt(sel);
			} catch (NumberFormatException e) {
				System.out.println("1 ~ 6 중에 선택해주세요!");
			}
			System.out.println("");
			if (selNum == 1) {
				checkId();
				break;
			} else if (selNum == 2) {
				signUpId();
				break;
			} else if (selNum == 3) {
				nonMember();
				break;
			} else if (selNum == 4) {
				CheckTicketDAO chDao = new CheckTicketDAO();
				chDao.CheckTicketYN();
				break;
			} else if (selNum == 5) {
				ManagerDAO mDao = new ManagerDAO();
				mDao.managerSignIn();
				break;
			} else if (selNum == 6) {
				System.out.println("다음에 또 이용해주세요!");
				System.exit(0);
			} else {
				System.out.println("1 ~ 6 중에 선택해주세요!");
			}
			
		}
		
		
	}
	
	public String checkId() {
		String inputId = "";
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT MEMBERID ");
			sb.append("FROM MEMBER ");
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			System.out.println("[로그인]");
			System.out.print("아이디 : ");
			inputId = sc.nextLine();
			while (rs.next()) {
				if (rs.getString(1).equals(inputId)) {
					checkPassword(inputId);
					return inputId;
				}
			}
			System.out.println("");
			System.out.println("일치하는 ID가 없습니다. ");
			System.out.println("회원가입을 하시겠습니까? (y/n)");
			while (true) {
				String yesNo = sc.nextLine();
				System.out.println("");
				if (yesNo.equalsIgnoreCase("y")) {
					signUpId();
					break;
				} else if (yesNo.equalsIgnoreCase("n")) {
					System.out.println("메인 메뉴로 돌아갑니다.");
					checkMember();
					break;
				} else {
					System.out.println("'Y' 혹은 'N' 중에 입력해주세요");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return inputId;		
	}
	
	public String checkPassword (String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.print("비밀번호 : ");
			String inputPw = sc.nextLine();
			
			String sql = "";
			sql = "SELECT PASSWORD FROM MEMBER WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (inputPw.equals(rs.getString(1))) {
					System.out.println("");
					System.out.println(id+ " 고객님 환영합니다~!");
					TicketVO.vo.setMemberId(id);
					return id;
				}
			} 
			System.out.println("비밀번호가 일치하지 않습니다.");
			checkPassword(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return id;
	}
	
	public String signUpId() {
		ArrayList<String> list = new ArrayList<>();
		String id = "";
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "";
			sql = "SELECT MEMBERID FROM MEMBER";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			
			System.out.println("[회원가입]");
			System.out.println("ID를 입력해 주세요.");
			System.out.print("ID : ");
			boolean check = false;
			
			while (true) {
				check = false;
				id = sc.nextLine();
				for (int i=0; i<list.size(); i++) {
					if (list.get(i).equalsIgnoreCase(id)) {
						check = true;
					}
				}
				if (check) {
					System.out.println("이미 존재하는 ID입니다");
					System.out.println("다른 ID를 입력해주세요");
				} else {
					break;
				}
			}
			
			System.out.println("ID 입력이 완료되었습니다.");
			System.out.println("");
			signUp(id);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return id;
	}
	
	
	public void signUp (String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "";
			sql = "INSERT INTO MEMBER (MEMBERID, PASSWORD, NAME, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			String pw = "";
			String pw2 = "";
			while (true) {
				System.out.println("사용하실 비밀번호를 입력해 주세요.");
				System.out.print("비밀번호 : ");
				pw = sc.nextLine();
				System.out.println("");
				System.out.println("비밀번호를 한 번더 입력해 주세요.");
				System.out.print("비밀번호 : ");
				pw2 = sc.nextLine();
				System.out.println("");
				if (pw.equals(pw2)) break;
				System.out.println("비밀번호가 일치하지 않습니다.");
				System.out.println("");
			}
			System.out.println("이름을 입력해 주세요.");
			System.out.print("이름 : ");
			String name = sc.nextLine();
			System.out.println("");
			System.out.println("전화번호를 입력해 주세요.");
			System.out.print("전화번호 : ");
			String phone = sc.nextLine();
			System.out.println("");
			System.out.println("주소를 입력해 주세요.");
			System.out.print("주소 : ");
			String add = sc.nextLine();
			System.out.println("");
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.setString(5, add);
			
			pstmt.executeUpdate();
			System.out.println("회원가입이 완료되었습니다.");
			System.out.println("로그인 페이지로 이동합니다.");
			System.out.println("");
			checkId();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public void nonMember() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Random ran = new Random();
			ArrayList list = new ArrayList();
			
			String sql = "";
			sql = "SELECT MEMBERID FROM MEMBER";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			String ranId = "";
			Outer :
			while (true) {
				ranId = String.valueOf(ran.nextInt(99999));
				for (int i=0; i<list.size(); i++) {
					if (!list.get(i).equals(ranId)) {
						break Outer;
					}
				}
			}
			
			sql = "INSERT INTO MEMBER (MEMBERID, NAME, PHONE, RANK) VALUES (?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			System.out.println("[비회원 예매]");
			System.out.println("이름을 입력해 주세요.");
			System.out.print("이름 : ");
			String name = sc.nextLine();
			pstmt.setString(1, ranId);
			pstmt.setString(2, name);
			System.out.println("");
			System.out.println("전화번호를 입력해 주세요.");
			System.out.print("전화번호 : ");
			String phone = sc.nextLine();
			pstmt.setString(3, phone);
			pstmt.setString(4, null);
			
			pstmt.executeUpdate();
			TicketVO.vo.setMemberId(ranId);
			System.out.println("");
			System.out.println("비회원 설정이 완료되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	// ----------------------------------- 로그인  끝 --------------------------------------------
	
}