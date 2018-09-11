package com.mystudy.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class ManagerMenuDAO {
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	Scanner sc = new Scanner(System.in);
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 관리자 모드
	
	// 영화 추가/ 삭제					클리어
	// 매출보기 (날짜별, 영화별)			
	// 전체 혹은 영화별 관객수 보기			
	// 관리자 권한 부여					
	
	// 비회원 구매기록 삭제
	
	public void managerMenu() {
		System.out.println("[관리자 모드]");
		System.out.println("------------------------------------------");
		System.out.println("1. 영화 관리\t2. 매출 조회\t3. 관객수 조회");
		System.out.println("4. 관리자 권한 부여\t5. 종료");
		System.out.println("------------------------------------------");
		System.out.print("입력> ");		//입력창
		while(true) {
			String sel = sc.nextLine();
			if (sel.equalsIgnoreCase("1")) {
				System.out.println();
				manageMovieMenu();
				break;
			} else if (sel.equalsIgnoreCase("2")) {
				System.out.println();
				salesMenu();
				break;
			} else if (sel.equalsIgnoreCase("3")) {
				System.out.println();
				checkAttendence();
				break;
			} else if (sel.equalsIgnoreCase("4")) {
				System.out.println();
				giveRank();
				break;
			} else if (sel.equalsIgnoreCase("5")) {
				System.exit(0);
			} else {
				System.out.println("1 ~ 5 중에 선택해 주세요");
			}
		}
	}
	
	
	public void manageMovieMenu() {
		System.out.println("[영화 관리]");
		System.out.println("------------------------------------------");
		System.out.println("1. 영화 추가\t2. 영화 삭제");
		System.out.println("------------------------------------------");
		System.out.print("입력> ");		//입력창
		while(true) {
			String sel = sc.nextLine();
			System.out.println();
			if (sel.equalsIgnoreCase("1")) {
				System.out.println("");
				insertMovie();
				break;
			} else if (sel.equalsIgnoreCase("2")) {
				System.out.println("");
				delMovie();
				break;
			} else {
				System.out.println("");
				System.out.println("1 ~ 2 중에 선택해 주세요");
			}
		}
	}
	
	public void insertMovie() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "";
			sql = "INSERT INTO MOVIE "
					+ " (MOVIENO, MOVIENAME, MOVIEGENRE, MOVIERATING, DIRECTOR, DESCRIPTION, COUNTRY, RELEASEDATE, RUNNINGTIME) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), ?) ";
			pstmt = conn.prepareStatement(sql);
			Outer :
			while (true) {
				System.out.println("영화 코드를 입력해주세요");
				System.out.print("입력> ");
				String mvNo = sc.nextLine();
				pstmt.setString(1, mvNo);
				System.out.println("");
				System.out.println("영화 제목을 입력해주세요");
				System.out.print("입력> ");
				String mvName = sc.nextLine();
				pstmt.setString(2, mvName);
				System.out.println("");
				System.out.println("영화 장르를 입력해주세요");
				System.out.print("입력> ");
				String mvGen = sc.nextLine();
				pstmt.setString(3, mvGen);
				System.out.println("");
				System.out.println("관람 등급을 입력해주세요");
				System.out.print("입력> ");
				String mvRat = sc.nextLine();
				pstmt.setString(4, mvRat);
				System.out.println("");
				System.out.println("감독을 입력해주세요");
				System.out.print("입력> ");
				String dir = sc.nextLine();
				pstmt.setString(5, dir);
				System.out.println("");
				System.out.println("간단한 설명을 입력해주세요");
				System.out.print("입력> ");
				String des = sc.nextLine();
				pstmt.setString(6, des);
				System.out.println("");
				System.out.println("제작 국가를 입력해주세요");
				System.out.print("입력> ");
				String cntry = sc.nextLine();
				pstmt.setString(7, cntry);
				System.out.println("");
				System.out.println("개봉일을 입력해주세요 (ex. 2018/09/07)");
				System.out.print("입력> ");
				String rDate = sc.nextLine();
				pstmt.setString(8, rDate);
				System.out.println("");
				System.out.println("러닝타임을 입력해주세요");
				System.out.print("입력> ");
				String rTime = sc.nextLine();
				pstmt.setString(9, rTime);
				System.out.println("");
				
				System.out.println("입력하신 정보는 ");
				System.out.println("영화 코드 : "+ mvNo);
				System.out.println("영화 제목 : "+ mvName);
				System.out.println("영화 장르 : "+ mvGen);
				System.out.println("관람 등급 : "+ mvRat);
				System.out.println("감독 : "+ dir);
				System.out.println("설명 : "+ des);
				System.out.println("제작 국가 : "+ cntry);
				System.out.println("개봉일 : "+ rDate);
				System.out.println("러닝타임 : "+ rTime);
				Inner :
				while(true) {
					System.out.println("입력하시겠습니까? (Y/N)");
					System.out.println("메뉴로 돌아가시려면 'M' 을 입력하세요");
					System.out.print("입력> ");
					String yn = sc.nextLine();
					System.out.println("");
					if (yn.equalsIgnoreCase("y")) {
						pstmt.executeUpdate();
						System.out.println("입력이 완료되었습니다");
						break Outer;
					} else if (yn.equalsIgnoreCase("n")) {
						break;
					} else if (yn.equalsIgnoreCase("m")) {
						break Outer;
					}
					else {
						System.out.println("다시 입력해주세요");
					}
				}	// inner end
			}	// outer end
			
			managerMenu();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	public boolean delMovie() {
		boolean check = false;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "";
			ArrayList<String> listNo = new ArrayList<>();
			ArrayList<String> listName = new ArrayList<>();
			sql = "SELECT MOVIENO, MOVIENAME FROM MOVIE ORDER BY MOVIENO";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				listNo.add(rs.getString(1));
				listName.add(rs.getString(2));
			}
			System.out.println("삭제하실 영화를 선택해주세요");
			System.out.println("번호   영화코드                  제목");
			System.out.println("==========================");
			for (int i=0; i<listNo.size(); i++) {
				System.out.println((i+1)+ " : "+ listNo.get(i) + "\t" + listName.get(i));
			}
			System.out.println("==========================");
			int num = 0;
			Outer :
			while (true) {
				Inner :
				while (true) {
					System.out.print("입력> ");
					String sel = sc.nextLine();
					System.out.println("");
					try {
						num = Integer.parseInt(sel);
						if (num >= 1 && num <= listNo.size()) {
							break Outer;
						} else {
							System.out.println("1 ~ "+ listNo.size()+ "중에 선택해주세요");
						}
						break;
					} catch (NumberFormatException e) {
						System.out.println("숫자만 입력해주세요");
					}
				}
			}
			
			int selNum = num-1;
			System.out.println("선택하신 영화는 ["+ listName.get(selNum)+  "] 입니다");
			System.out.println("삭제하시겠습니까? (Y/N)");
			while (true) {
				System.out.print("입력> ");
				System.out.println();
				String yn = sc.nextLine();
				if (yn.equalsIgnoreCase("y")) {
					System.out.println("삭제를 진행합니다");
					check = true;
					break;
				} else if (yn.equalsIgnoreCase("n")) {
					System.out.println("메뉴로 돌아갑니다");
					managerMenu();
					return check;
				} else System.out.println("'Y' 혹은 'N' 중에 입력해주세요!");
			}
			sql = "DELETE FROM MOVIE WHERE MOVIENAME IN ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, listName.get(selNum));
			pstmt.executeUpdate();
			System.out.println("삭제가 완료되었습니다.");
			managerMenu();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return check;
		
	}
	
	public void salesMenu() {				// 매출 조회 초기창
		System.out.println("[매출 관리]");
		System.out.println("------------------------------------------");
		System.out.println("1. 날짜별 조회\t2. 영화별 조회");
		System.out.println("------------------------------------------");
		System.out.print("입력> ");		//입력창
		System.out.println();
		while(true) {
			String sel = sc.nextLine();
			if (sel.equalsIgnoreCase("1")) {
				System.out.println("");
				checkSalesPerDate();
				break;
			} else if (sel.equalsIgnoreCase("2")) {
				System.out.println("");
				checkSalesPerMovie();
				break;
			} else {
				System.out.println("1 ~ 2 중에 선택해 주세요");
			}
		}
		
	}
	
	public void checkSalesPerDate() {		// 날짜별 매출 조회
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT TO_CHAR(ORDER_DATE, 'YYYY/MM/DD'), SUM(PRICE), PAYTYPE FROM BANK GROUP BY ORDER_DATE, PAYTYPE ORDER BY ORDER_DATE DESC ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int sum = 0;
			System.out.println("날짜\t\t수익\t결제 방법 ");
			System.out.println("====================================");
			while (rs.next()) {
				System.out.println(rs.getString(1)+ "\t"+ rs.getInt(2)+ "\t"+rs.getString(3));
				sum += rs.getInt(2);
			}
			System.out.println("====================================");
			System.out.println("총 매출 : "+ sum+ "원");
			System.out.println();
			System.out.println("'Y'를 입력하면 메뉴로 돌아갑니다");
			while (true) {
				System.out.print("입력> ");
				System.out.println();
				String yn = sc.nextLine();
				if (yn.equalsIgnoreCase("y")) {
					System.out.println("메뉴로 돌아갑니다");
					managerMenu();
					break ;
				} else {
					System.out.println("메뉴로 돌아가려면 'Y'를 입력해 주세요");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	public void checkSalesPerMovie() {		// 영화별 수익 조회
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT MOVIENO, MOVIENAME, SUM(PRICE) FROM SALES_MOVIE "
					+ " GROUP BY MOVIENO, MOVIENAME "
					+ " ORDER BY MOVIENO";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("영화코드\t영화제목\t\t\t수익");
			System.out.println("==========================================");
			int len = 0;
			int sum = 0;
			while (rs.next()) {
				len = rs.getString(2).length();
				if (len >= 7 && len < 14) {
					System.out.println(rs.getString(1)+ "\t"+ rs.getString(2)+ "\t\t"+rs.getInt(3)+ "원");
				}else if (len < 7) {
					System.out.println(rs.getString(1)+ "\t"+ rs.getString(2)+ "\t\t\t"+rs.getInt(3)+ "원");
//				} else if(len <= 3) {
//					System.out.println(rs.getString(1)+ "\t"+ rs.getString(2)+ "\t\t\t"+rs.getInt(3)+ "원");
				} else {
					System.out.println(rs.getString(1)+ "\t"+ rs.getString(2)+ "\t"+rs.getInt(3)+ "원");
				}
				sum += rs.getInt(3);
			}
			System.out.println("==========================================");
			System.out.println("총 매출 : "+ sum+ "원");
			System.out.println();
			System.out.println("'Y'를 입력하면 메뉴로 돌아갑니다");
			while (true) {
				System.out.print("입력> ");
				System.out.println();
				String yn = sc.nextLine();
				if (yn.equalsIgnoreCase("y")) {
					System.out.println("메뉴로 돌아갑니다");
					managerMenu();
					break ;
				} else {
					System.out.println("메뉴로 돌아가려면 'Y'를 입력해 주세요");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public void checkAttendence() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql = "SELECT MOVIENAME, COUNT(*) FROM SALES_MOVIE "
					+ " GROUP BY MOVIENAME ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println(" 영화 제목\t\t\t관객수");
			System.out.println("==========================================");
			int total = 0;
			int len = 0;
			while (rs.next()) {
				len = rs.getString(1).length();
				if (len >= 7 && len <14) {
					System.out.println(" "+rs.getString(1)+ "\t\t"+ rs.getString(2));
				} else
				if (len < 7) {
					System.out.println(" "+rs.getString(1)+ "\t\t\t"+ rs.getString(2));
//				} else if(len <= 3) {
//					System.out.println(" "+rs.getString(1)+ "\t\t\t"+ rs.getString(2));
//				} else if (len == 13) {
//					System.out.println(" "+rs.getString(1)+ "\t\t"+ rs.getString(2));
				} else {
					System.out.println(" "+rs.getString(1)+ "\t"+ rs.getString(2));
				}
				total += Integer.parseInt(rs.getString(2));
			}
			System.out.println("------------------------------------------");
			System.out.println("총 관객수\t\t\t"+ total);
			System.out.println("==========================================");
			System.out.println("'Y'를 입력하면 메뉴로 돌아갑니다");
			while (true) {
				System.out.print("입력> ");
				System.out.println();
				String yn = sc.nextLine();
				if (yn.equalsIgnoreCase("y")) {
					System.out.println("메뉴로 돌아갑니다");
					managerMenu();
					break ;
				} else {
					System.out.println("메뉴로 돌아가려면 'Y'를 입력해 주세요");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	public void giveRank() {
			ManagerDAO dao = new ManagerDAO();
			Outer :
			while (true) {
				System.out.println("관리자 권한을 부여할 ID를 입력해주세요");
				System.out.print("입력> ");
				System.out.println();
				String input = sc.nextLine();
				if (dao.checkId(input)) {
					System.out.println("-------------------------------");
					printInfoMember(input);
					System.out.println("-------------------------------");
					System.out.println("관리자 권한을 부여하시겠습니까? (Y/N)");
					Inner :
					while (true) {
						System.out.print("입력> ");
						String yn = sc.nextLine();
						if (yn.equalsIgnoreCase("y")) {
							updateRank(input);
							System.out.println("관리자 권한 부여를 완료했습니다.");
							managerMenu();
							break Outer;
						} else if (yn.equalsIgnoreCase("n")){
							System.out.println("메뉴로 돌아갑니다.");
							managerMenu();
							break Outer;
						} else {
							System.out.println("'Y' 혹은 'N' 중에 입력해주세요!");
						}
					}
					
				} else {
					System.out.println("일치하는 ID가 없습니다");
					System.out.println("메뉴로 돌아갑니다");
					managerMenu();
				}
			}
	}
	
	public void printInfoMember(String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT MEMBERID, NAME, PHONE, ADDRESS FROM MEMBER WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("ID : "+ rs.getString(1)+ "\t"+ "이름 : "+ rs.getString(2)+ "\n전화번호 : "+ rs.getString(3)+ "\t주소 : "+ rs.getString(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	public void updateRank(String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "UPDATE MEMBER SET RANK = '1' WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
}
