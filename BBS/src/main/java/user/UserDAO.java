package user;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

//Data Access Object의 약자로 DataBase의 data에 접근을 위한 객체
//DataBase에 접근을 하기위한 로직과 비즈니스 로직을 분리하기 위해서 사용
//ConnectionPool : connection 객체를 미리 만들어 놓고 그것을 가져다 쓰는 것
//다쓰고 반환해 놓는 것

public class UserDAO {
	
	private Connection conn; //데이터베이스에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs; //어떠한 정보를 담을 수 있는 객체
	
	//mysql에 접속을 하게 해주는 부분
	public UserDAO() {
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
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1; //로그인 성공
				else
					return 0; //비밀번호 불일치
			}
			return -1; //아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate(); //해당 statment 실행한 결과를 넣을 수 있도록
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}

}
