package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class test {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	Scanner sc = new Scanner(System.in);
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	public static void main(String[] args) {
	
		
		
		System.out.println("**  **   *****   ***** ");
		System.out.println("** **       **   **   **");
		System.out.println("****        **   **    **");
		System.out.println("** **   **  **   **   **");
		System.out.println("**  **   ***     *****");
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		
		
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			boolean check = false;
			String sql = "";
//			sql = "SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE FROM ALL_CONSTRAINTS WHERE TABLE_NAME = ?";
//			sql = "SELECT DATENO FROM TICKETING";
//			sql = "SELECT TK.* "
//					+ " FROM TICKET_VIEW TK, MEMBER MB "
//					+ " WHERE TK.MEMBERID = MB.MEMBERID "
//					+ " AND MB.PASSWORD IS NULL ";
//			sql = "UPDATE MOVIE SET MOVIERATING = '15세관람가' WHERE MOVIENO = '0006'";
			sql = "UPDATE MOVIE SET MOVIENAME = '도라에몽 : 진구의 보물섬' WHERE MOVIENO = '0008'";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
//			pstmt.setString(1, "TICKETING");
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				System.out.println(rs.getString(1)+ "\t" +rs.getString(2));
//				System.out.println(rs.getString(1));
//			}
			
			
			System.out.println(check);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static int test() {
		int a = 0;
		try {
			System.out.println("트라이");
			return a;
		} catch (Exception e) {
			System.out.println("캐치");
		} finally {
			System.out.println("파이널리");
		}
		return a;
	}

}
