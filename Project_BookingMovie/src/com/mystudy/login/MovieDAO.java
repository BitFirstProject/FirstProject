package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class MovieDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	Scanner sc = new Scanner(System.in);
	ArrayList<String> mvNo = new ArrayList<>();
	ArrayList<String> mvName = new ArrayList<>();
	ArrayList<String> mvRat = new ArrayList<>();
	ArrayList<String> mvDet = new ArrayList<>();
	ArrayList<String> mvGen = new ArrayList<>();
	
	int sel = 0;
	
	String mNum;
//	private String movieNo;
//	private String movieName;
//	private String movieRating;
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] 드라이버 로딩 실패!!!");
		}
	}
	
	MovieDAO () {			// 생성자
		movieList();
	}
	
	//영화리스트보기
	public String movieList() {
		sel = 0;
		String result = "";
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			
			String sql = "SELECT MOVIENO, MOVIENAME, MOVIEGENRE, MOVIERATING, DIRECTOR, DESCRIPTION, COUNTRY, RUNNINGTIME  FROM MOVIE ORDER BY MOVIENO";
		
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
//			SELECT MOVIENO, MOVIENAME, MOVIEGENRE, MOVIERATING FROM MOVIE
//			System.out.print("영화감독: " + list.get(0) + " \t " );
//			System.out.print("영화설명: " + list.get(1) + " \t ");
//			System.out.print("제작국가: " + list.get(2));
			while (rs.next()) {
				mvNo.add(rs.getString(1));
				mvName.add(rs.getString(2));
				mvGen.add(rs.getString(3));
				mvRat.add(rs.getString(4));
//				if (rs.getString(2).length() > 7) {
//					System.out.print(mvNum+ " : "+ rs.getString(2)+ "\t"+ rs.getString(3));
//				} else if (rs.getString(2).length() < 4) {
//					System.out.print(mvNum+ " : "+ rs.getString(2)+ "\t\t\t"+ rs.getString(3));
//				} else if (rs.getString(2).length() <= 7)
//					System.out.print(mvNum+ " : "+ rs.getString(2)+ "\t\t"+ rs.getString(3));
//				
//				if (rs.getString(3).length() >= 15) {
//					System.out.print("\t"+ rs.getString(4));
//				} else if (rs.getString(3).length() < 15 && rs.getString(3).length() > 6) {
//					System.out.print("\t\t"+ rs.getString(4));
//				} else if (rs.getString(3).length() <=6) {
//					System.out.print("\t\t\t"+ rs.getString(4));
//				}
//				System.out.println();
				mvDet.add("영화 감독 : "+ rs.getString(5)+ "\t영화 설명 : "+rs.getString(6) + "\n제작 국가 : " + rs.getString(7) + "\t상영 시간 : "+ rs.getString(8));
			}
		
			
//			System.out.println(list.get(1) + " \t ");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return result;
	}
	
	public void printList() {
		int mvNum = 1;
		System.out.println("");
		System.out.println("====================== [상영중인 영화] ======================");
		for (int i=0; i<mvGen.size(); i++) {
			if (mvName.get(i).length() > 7) {
				System.out.print(mvNum+ " : "+ mvName.get(i)+ "\t"+ mvGen.get(i));
			} else if (mvName.get(i).length() < 3) {
				System.out.print(mvNum+ " : "+ mvName.get(i)+ "\t\t\t"+ mvGen.get(i));
			} else if (mvName.get(i).length() <= 7)
				System.out.print(mvNum+ " : "+ mvName.get(i)+ "\t\t"+ mvGen.get(i));
			
			if (mvGen.get(i).length() >= 15) {
				System.out.print("\t"+ mvRat.get(i));
			} else if (mvGen.get(i).length() < 15 && mvGen.get(i).length() > 6) {
				System.out.print("\t\t"+ mvRat.get(i));
			} else if (mvGen.get(i).length() <=6) {
				System.out.print("\t\t\t"+ mvRat.get(i));
			}
			System.out.println();
			mvNum++;
		}
		System.out.println("=========================================================");
		System.out.println("관람하실 영화 번호를 입력해 주세요.");
	}
	
	//영화 선택하기
	public int movieSelection (int sel) {
		TicketVO.vo.setMovieNo(mvNo.get(sel));
		TicketVO.vo.setMovieName(mvName.get(sel));
		TicketVO.vo.setMovieRating(mvRat.get(sel));
	
		System.out.println("");
		System.out.println("선택하신 영화는 [" + mvName.get(sel) + "] 입니다.");
		System.out.println("");

		return sel;
	}
	
	
	//상세정보 보기
	public void moviesDetail(int sel) {
//		String result = "";
				
//				System.out.println("영화감독"+ "\t"+"\t"+  "영화설명"+ "\t"+"\t"+ "제작국가" );
				System.out.println("================================================");
				System.out.println(mvDet.get(sel));
				System.out.println("================================================");
				System.out.println();
				
		
//		return result;
	}
	
	
	
	public void booking(int sel) {
//		String result = "";
		//System.out.println("예매하기를 선택하셨습니다.");

				System.out.println("[" + mvName.get(sel) + "] 예매를 진행합니다.");
				System.out.println("");
			
//		result = "";
//		return result;
	}
	
	//반복분
	public void rounder() {
		Outer :
		while (true) {
//			movieList();		// List add작업
			printList();		// 영화 목록 출력
			
			int btn = 0;
			Inner :
			while (true) {
				try {
				System.out.print("입력> ");
				btn = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("숫자만 입력해 주세요.");
				}
				if (btn>=1 && btn<=mvNo.size()) {
					break;
				} else System.out.println("1 ~ "+ mvNo.size()+ " 숫자만 입력해 주세요.");
			}
			
			int sel = btn-1;
			movieSelection(sel);
			selecMenu(sel);
			break Outer;
		} //while 바깥쪽
	
	}
	
	
	public void selecMenu(int sel) {
		
		boolean flag = true;
		int input = 0;
		Outer1 :
		while (true) {
			System.out.println("영화 상세정보 보기는 1번, 영화를 예매하시려면 2번을 입력해 주세요");
			System.out.print("입력> ");
			try {
				input = Integer.parseInt(sc.nextLine());
				if (input != 1 && input !=2 ) {
					System.out.println("1 ~ 2 숫자만 입력해 주세요.");
					System.out.println("");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("1 ~ 2 숫자만 입력해 주세요.");
				System.out.println("");
			}
		}
		
		
		Outer2 :
		while(true) {
			if (input == 1) {
				System.out.println("영화 상세정보 보기를 선택하셨습니다.");
				moviesDetail(sel);
				Inner :
				while(true) {
					System.out.println("예매 하시겠습니까? (Y/N) ");
					String input1 = "";
					
					System.out.print("입력> ");
					input1 = sc.nextLine();
					System.out.println("");
					
					if (input1.equalsIgnoreCase("y")) {
						booking(sel);
						break Outer2;
					} else if (input1.equalsIgnoreCase("n")) {
						rounder();
						break Outer2;
					} else {
						System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
						System.out.println("");
					}
				}
			} else if (input == 2) {
				booking(sel);
				break Outer2;
			} 
		}//while
	}//method
}
