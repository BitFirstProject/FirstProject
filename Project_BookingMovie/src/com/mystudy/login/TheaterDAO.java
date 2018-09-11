package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class TheaterDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	Scanner sc = new Scanner(System.in);
	ArrayList<String> listSp = new ArrayList<>();
		
	//상영관 정보 불러오기
	public ArrayList<String> getTheater() {
	ArrayList<String> list = null;
	//String movieno = TicketVO.vo.getMovieNo();
	try {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		String sql = "";
		sql += "SELECT TH.NAME, TH.SPECIAL ";
		sql += "FROM MOVIE MO, THEATER TH ";
		sql += "WHERE MO.MOVIENO = TH.MOVIENO ";
		sql += "AND MO.MOVIENO = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, TicketVO.vo.getMovieNo());
		
		rs = pstmt.executeQuery();
		
		list = new ArrayList<String>();
		while(rs.next()) {
			list.add(rs.getString("name"));
			listSp.add(rs.getString(2));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
	}
	return list;
	}
	
	//상영시간 정보 불러오기 
	public ArrayList<String> getStartTime() {
	ArrayList<String> list = null;
	try {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		String sql = "";
		sql = "SELECT STARTTIME FROM TIME "
				+ " WHERE THEATERNO = ? ORDER BY TIMENO ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, TicketVO.vo.getTheaterNo());
		rs = pstmt.executeQuery();
		
		list = new ArrayList<String>();
		while(rs.next()) {
			list.add(rs.getString("starttime"));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
	}
	return list;
	}
	public ArrayList<String> getEndTime() {
		ArrayList<String> list = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT ENDTIME FROM TIME "
					+ " WHERE THEATERNO = ? ORDER BY TIMENO ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, TicketVO.vo.getTheaterNo());
			rs = pstmt.executeQuery();
			
			list = new ArrayList<String>();
			while(rs.next()) {
				list.add(rs.getString("endtime"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return list;
	}
	
	//상영관 선택 화면
	public String selectTheater() {
		
		//리턴값 생성
		String inputTheater = null;
		
		//상영관 선택 화면 출력
		ArrayList<String> theaterList = getTheater();
		
		while (true) {
			System.out.println(" 상영관을 선택하세요.");
			System.out.println("---------------------------------------------");
			int i=0;
			for(i=0; i<theaterList.size(); i++) {
				System.out.println(" "+ (i+1) + ". " + theaterList.get(i) + "\t\t("+ listSp.get(i)+ ")");
			}
			System.out.println();
			System.out.println("---------------------------------------------");
			System.out.print(" 상영관 선택> ");
			try {
				String sel = sc.nextLine();
				System.out.println("");
				int selNum = Integer.parseInt(sel);
				inputTheater = theaterList.get(selNum - 1);
				
				int size = theaterList.size();
				if (size == 1) {
					if (selNum == 1) {
						break;
					}
				} else if (size == 2) {
					if(selNum == 1 || selNum == 2) {
						break;
					}
				} else if (size == 3) {
					if (selNum == 1 || selNum == 2 || selNum == 3) {
						break;
					}
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("다시 입력해주세요.");
				System.out.println("");	
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해주세요.");
				System.out.println("");
			}
			
		}
		TicketVO.vo.setTheaterName(inputTheater);
		setTheaterNo(inputTheater);
		
		selectTime();
		return inputTheater;
	}
	
	// 상영관 번호 
	public void setTheaterNo(String inputTheater) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT TH.THEATERNO "
					+ " FROM THEATER TH, MOVIE MO "
					+ " WHERE TH.MOVIENO = MO.MOVIENO "
					+ " AND TH.NAME = ?"
					+ " AND TH.MOVIENO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputTheater);
			pstmt.setString(2, TicketVO.vo.getMovieNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				TicketVO.vo.setTheaterNo(rs.getString(1));
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	//시간 선택 화면
	public String selectTime() {
		//리턴값 생성
		String inputStartTime = null;
		String inputEndTime = null;
		
		//시간 선택 화면 출력
		ArrayList<String> startTimeList = getStartTime();
		ArrayList<String> endTimeList = getEndTime();
		
		
		while (true) {
			System.out.println("상영 시간을 선택하세요.");
			System.out.println("---------------------------------------------");
			System.out.println("[" + TicketVO.vo.getTheaterName() + "]");
			for(int i=0; i<startTimeList.size(); i++) {
				System.out.println(i+1 + ". " + startTimeList.get(i) + " ~ " + endTimeList.get(i));
			}
			//System.out.println();
			System.out.println("---------------------------------------------");
			System.out.print("시간 선택> ");
			String sel = sc.nextLine();
			System.out.println();
			try {
				int selNum = Integer.parseInt(sel);
				inputStartTime = startTimeList.get(selNum - 1);
				inputEndTime = endTimeList.get(selNum - 1);
				
				int size = startTimeList.size();
				if (size <= startTimeList.size()) {
					if (selNum <= startTimeList.size() || selNum > 0) {
						break;
					}
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println("다시 입력해 주세요.");
				System.out.println("");
			} catch (NumberFormatException e) {
				System.out.println("다시 입력해 주세요.");
				System.out.println("");
				
			}
		}
		TicketVO.vo.setStartTime(inputStartTime);
		TicketVO.vo.setEndTime(inputEndTime);
		setTimeNo(inputStartTime);
		return inputStartTime;
	}
	
	
	//setTimeno
	public void setTimeNo(String inputTime) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT TIMENO FROM TIME WHERE STARTTIME = ? AND THEATERNO = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputTime);
			pstmt.setString(2, TicketVO.vo.getTheaterNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				TicketVO.vo.setTimeNo(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	
}
