<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
     <% 
       session.invalidate(); //현재 이 페이지에 접속한 회원의 세션을 빼앗아 로그아웃 시켜줌
     
     %>
     <script>
         location.href = 'main.jsp';
     </script>
</body>
</html>
