package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common_util.JDBC_Close;

public class PayDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	Scanner sc = new Scanner(System.in);
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public void printPrice() {
		int price = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT SPECIAL FROM SEAT WHERE SEATNO = ?";
			for (int i=0; i<TicketVO.vo.listSeatNo.size(); i++) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, TicketVO.vo.listSeatNo.get(i));
				rs = pstmt.executeQuery();
				if (rs.next()) {
					if (rs.getObject(1) == null) {
						price += 9000;
					} else {
						price += 10000;
					}
				}
			}
			System.out.println("결제 금액은 "+ price + "원 입니다.");
			String payType = "";
			System.out.println("결제 방법을 선택해 주세요.");
			System.out.println("---------------");
			System.out.println("1. 현금\t2. 카드");
			System.out.println("---------------");
			while (true) {
				System.out.print("결제 선택> ");
				payType = sc.nextLine();
				if (payType.equalsIgnoreCase("1")) {
					System.out.println("현금 결제를 진행합니다.");
					payType = "현금";
					break;
				} else if (payType.equalsIgnoreCase("2")) {
					System.out.println("카드 결제를 진행합니다.");
					payType = "카드";
					break;
				} else {
					System.out.println("1 혹은 2 중에 선택해 주세요.");
				}
			}
			
			System.out.println("");
			System.out.println("예매가 완료되었습니다. 감사합니다.");
			System.out.println();
			TicketVO.vo.setPrice(price);
			
			sql = "INSERT INTO BANK VALUES (?, ?, TO_DATE(?, 'YYYY/MM/DD'))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, payType);
			pstmt.setInt(2, price);
			pstmt.setString(3, TicketVO.vo.getDateNo());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	
}
