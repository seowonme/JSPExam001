package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	
	private Connection conn; //데이터베이스에 접근하게 해주는 객체
	private ResultSet rs; //어떠한 정보를 담을 수 있는 객체
	
	//mysql에 접속을 하게 해주는 부분
	public BbsDAO() {
		try {
			String dbURL =  "jdbc:mysql://localhost:3306/bbs?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "study";
			String dbPassword = "1234";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() { //현재의 시간을 가져오는 함수
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //SQL을 실행 준비단계로 만들어준다.
			rs = pstmt.executeQuery(); //실제로 실행 했을 때 나오는 결과를 가져올 수 있도록 한다.
			if(rs.next()) { 
				return rs.getString(1); //결과가 있는 경우는 현재의 날짜를 그대로 반환될 수 있도록 한다.
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() { //현재의 시간을 가져오는 함수
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC"; //내침차순을 해서 가장 마지막에 쓰인 글을 가져옴
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //SQL을 실행 준비단계로 만들어준다.
			rs = pstmt.executeQuery(); //실제로 실행 했을 때 나오는 결과를 가져올 수 있도록 한다.
			if(rs.next()) { 
				return rs.getInt(1) + 1; //나온 결과에 1을 더해 다음 게시글에 번호가 들어갈 수 있게 한다.
			}
			return 1; // 첫 번째 게시물인 경우
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)"; //각 항목에 하나씩 넣어줄 수 있도록 한다.
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //SQL을 실행 준비단계로 만들어준다.
			pstmt.setInt(1, getNext()); // 1. 다음번에 쓰여야할 게시글 번호
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1); //
			return pstmt.executeUpdate(); // insert문의 경우는 쿼리문이 아니라 업데이트문으로 작동한다.
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<Bbs> getList(int pageNumber) { //특정한 리스트를 담아 반환할 수 있도록 한다.
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list= new ArrayList<Bbs>(); //Bbs클래스에서 나오는 인스턴스를 보관할 수 있는 list
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //SQL을 실행 준비단계로 만들어준다.
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery(); //실제로 실행 했을 때 나오는 결과를 가져올 수 있도록 한다.
			while(rs.next()) { 
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		return list; //10개를 뽑아온 게시글 리스트가 list에 담겨 반환이 된다.
	}
	
	public boolean nextPage(int pageNumber) { // 게시글이 9개일 때 페이지는 1개, 게시글이 11개면 페이지는 2개
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery(); 
			if(rs.next()) { 
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); 
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery(); 
			if(rs.next()) { 
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
