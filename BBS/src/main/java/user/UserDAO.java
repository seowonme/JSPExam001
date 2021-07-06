package user;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class UserDAO {
	
	private Connection conn; //데이터베이스에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs; //어떠한 정보를 담을 수 있는 객체
	
	//mysql에 접속을 하게 해주는 부분
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/bbs?severTimezone=UTC;";
			String dbID = "study";
			String dbPassword = "1234";
			Class.forName("com.mysql.jdbc.cj.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL); 
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) { 
					//결과로 나온userPassword를 받아서 접속을 시도한 userPassword와 동일하다면 1을 반환 한다.
					return 1; //로그인 성공
				}else 
					return 0; //비밀번호 불일치
			}
			return -1; //아이디가 없음
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return -2; //데이터베이스 오류를 의미
	}

}
