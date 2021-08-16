package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	

}
