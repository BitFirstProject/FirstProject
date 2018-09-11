package com.mystudy.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TicketVO {
	private String ticketingNo;
	private String theaterNo;
	private String seatNo;
	private String movieNo;
	private String memberId;
	private String timeNo;
	private String dateNo;
	private String theaterName;
	private String seatRow;
	private String seatColumn;
	private String movieName;
	private String movieRating;
	private String startTime;
	private String endTime;
	private int price;
	private String payType;
	private int cntCustomer = 1;
	
	ArrayList<String> listSeatNo = new ArrayList<>();
	ArrayList<String> listSeatRow = new ArrayList<>();
	ArrayList<String> listSeatColumn = new ArrayList<>();
	
	
	
	static TicketVO vo = new TicketVO();
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.182:1521:xe"; 
	private static final String USER = "project1"; 
	private static final String PASSWORD = "project"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public int getCntCustomer() {
		return cntCustomer;
	}
	public void setCntCustomer(int cntCustomer) {
		this.cntCustomer = cntCustomer;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTicketingNo() {
		return ticketingNo;
	}
	public void setTicketingNo(String ticketingNo) {
		this.ticketingNo = ticketingNo;
	}
	public String getTheaterNo() {
		return theaterNo;
	}
	public void setTheaterNo(String theaterNo) {
		this.theaterNo = theaterNo;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public String getMovieNo() {
		return movieNo;
	}
	public void setMovieNo(String movieNo) {
		this.movieNo = movieNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getTimeNo() {
		return timeNo;
	}
	public void setTimeNo(String timeNo) {
		this.timeNo = timeNo;
	}
	public String getDateNo() {
		return dateNo;
	}
	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public String getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}
	public String getSeatColumn() {
		return seatColumn;
	}
	public void setSeatColumn(String seatColumn) {
		this.seatColumn = seatColumn;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}
