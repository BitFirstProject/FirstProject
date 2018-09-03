package com.mystudy.swing01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Member_CRUD {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	ResultSetMetaData rsmd;
	
	final static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	final static String user = "madang";
	final static String password = "madang";
	
		Member_CRUD(){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Connection getConnection(Connection conn) {
			try {
				conn = DriverManager.getConnection(url,user,password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}
		/*
		CREATE TABLE PRO_MEMBER (
		        ID VARCHAR2(20) PRIMARY KEY,
		        PASSWORD VARCHAR2(20),
		        NAME VARCHAR2(20),
		        PHONE VARCHAR2(30),
		        ADDRESS VARCHAR2(30)
		        );
		    
		SELECT * FROM PRO_MEMBER;

		INSERT INTO PRO_MEMBER VALUES('qkrrldus93','12345678','諛뺢린�뿰','010-1212-3232','�씤泥쒓킅�뿭�떆');
		*/
		int select(String id, String pw) {
			conn = getConnection(conn);
			/*
			 * result = 1 : Admin , 2 : User , 3: Fail 
			 */
			int result = 0;
			String sql = "SELECT RANK FROM PRO_MEMBER WHERE ID = ? AND PASSWORD = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					String str = rs.getString(1);
					if(str.equals("ADMIN")) result = 1;
					else if(str.equals("USER")) result = 2;
					else result = 3;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return result;
			}
			
		}
		String[] getColumns(String tableName){
			String[] list = null;
			conn = getConnection(conn);
			String result = "";
			String sql = "SELECT * FROM "+tableName+" ";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				int cnt = rsmd.getColumnCount();
				list = new String[cnt];
				for(int i = 1; i <= cnt; i++) {
					list[i-1] = rsmd.getColumnName(i);
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return list;
			}
			
		}
		public int getRowCnt() {
			conn = getConnection(conn);
			int result = 0;
			String sql = "SELECT COUNT(*) FROM PRO_MEMBER";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return result;
			}
		}
		String[][] getData(String tableName){
			String[][] list = null;
			int rowCnt = getRowCnt();
			conn = getConnection(conn);
			String result = "";
			String sql = "SELECT * FROM "+tableName+" ";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				int cnt = rsmd.getColumnCount();
				conn = getConnection(conn);
				list = new String[rowCnt][cnt];
				int x = 0;
				while(rs.next()) {
					for(int i = 1; i <= cnt; i++) {
						list[x][i-1] = rs.getString(i);
					}
					x++;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return list;
			}
			
		}
		String[] getColumns(){
			String[] list = null;
			conn = getConnection(conn);
			String result = "";
			String sql = "SELECT * FROM PRO_MEMBER";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				int cnt = rsmd.getColumnCount();
				list = new String[cnt];
				for(int i = 1; i <= cnt; i++) {
					list[i-1] = rsmd.getColumnName(i);
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return list;
			}
			
		}
		String[][] getData(){
			String[][] list = null;
			int rowCnt = getRowCnt();
			conn = getConnection(conn);
			String result = "";
			String sql = "SELECT * FROM PRO_MEMBER";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rsmd = rs.getMetaData();
				int cnt = rsmd.getColumnCount();
				conn = getConnection(conn);
				list = new String[rowCnt][cnt];
				int x = 0;
				while(rs.next()) {
					for(int i = 1; i <= cnt; i++) {
						list[x][i-1] = rs.getString(i);
					}
					x++;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return list;
			}
			
		}
		
		public String select() {
			conn = getConnection(conn);
			String result = "";
			String sql = "SELECT * FROM PRO_MEMBER";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					result += rs.getString(1)+" \t ";
					result += rs.getString(2)+" \t ";
					result += rs.getString(3)+" \t ";
					result += rs.getString(4)+" \t ";
					result += rs.getString(5)+"";
					result += "\n";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeRsPstmtConn();
				return result;
			}
			
		}
		
		boolean insert(String id, String pw, String name, String phone, String address) {
			conn = getConnection(conn);
			boolean result = false;
			String sql = "INSERT INTO PRO_MEMBER VALUES (?,?,?,?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				pstmt.setString(3, pw);
				pstmt.setString(4, pw);
				pstmt.setString(5, pw);
				int cnt = 0; 
				if((cnt = pstmt.executeUpdate()) == 1) {
					JOptionPane.showMessageDialog(null, "You have joined sucessfully");
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "id媛� �씠誘� 議댁옱�빀�땲�떎");
				e.printStackTrace();
			} finally {
				closePstmtConn();
				return result;
			}
			
		}
		
		void closeRsPstmtConn() {
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		void closePstmtConn() {
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
}
