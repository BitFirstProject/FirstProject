package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class ManagerDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	Scanner sc = new Scanner(System.in);
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	
	ArrayList<String> list;
	
	//static 초기화 구문
//	static {
//		try {
//			Class.forName(DRIVER);
//		} catch (ClassNotFoundException e) {
//			e.getMessage();
//		}
//	}
	
	// 관리자 모드
	// 관리자 로그인
	// 영화 추가/ 삭제
	// 매출 보기(날짜별, 영화별, 
	
	public void managerSignIn() {
		String inputId = "";
		String inputPw = "";
		
		
		System.out.println("[관리자 로그인]");
		System.out.print("아이디 : ");
		while (true) {
			inputId = sc.nextLine();
			if (checkId(inputId)) {
				checkPassword(inputId);
				break;
			} else {
				System.out.println("");
				System.out.println("일치하는 관리자ID가 존재하지 않습니다.");
				System.out.println("처음 화면으로 돌아갑니다.");
				System.out.println("");
				LogInDAO lDao = new LogInDAO();
				lDao.checkMember();
				break;
			}
		}
		
		
	}
	
	
	public boolean checkId(String inputId) {
		boolean check = false;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			list = new ArrayList<String>();
			String sql = "";
			sql = "SELECT MEMBERID FROM MEMBER ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			
			for (int i=0; i<list.size(); i++) {
				if (list.get(i).equals(inputId)) {
					check = true;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return check;
	}
	
	
	public String checkPassword (String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.print("비밀번호 : ");
			String inputPw = sc.nextLine();
			
			String sql = "";
			sql = "SELECT PASSWORD, RANK FROM MEMBER WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (inputPw.equals(rs.getString(1)) && rs.getString(2).equalsIgnoreCase("1")) {
					System.out.println("");
					System.out.println("로그인이 완료되었습니다!");
					System.out.println("");
					ManagerMenuDAO dao = new ManagerMenuDAO();
					dao.managerMenu();
					return id;
				} else if (inputPw.equals(rs.getString(1)) && !rs.getString(2).equalsIgnoreCase("1")) {
					System.out.println("");
					System.out.println("관리자 권한이 없습니다.");
					System.out.println("");
					LogInDAO lDao = new LogInDAO();
					lDao.checkMember();
					return id;
				}
			}
			System.out.println("");
			System.out.println("비밀번호가 일치하지 않습니다.");
			System.out.println("");
			checkPassword(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return id;
	}
	
	
	
	
	
}
