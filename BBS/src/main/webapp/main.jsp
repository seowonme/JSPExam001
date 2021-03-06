<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="wide=device-width", initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>

     <%
        //로그인이 된 사람들은 로그인 정보를 담을 수 있게 만들어준다.
        String userID = null;
        if (session.getAttribute("userID") != null){
        	userID = (String) session.getAttribute("userID");
        }
     %>
     
     <nav class="navbar navbar-default">
        <div class="navbar-header">
             <button type="button" class="navbar-toggle collased"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expanded="false">>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="main.jsp">JSP 게시판 웹 사이트</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
             <li class="active"><a href="main.jsp">메인</a></li>
             <li><a href="bbs.jsp">게시판</a></li>
          </ul>
          <%
            //로그인이 되어있지 않은 경우만 나올 수 있도록 해줌
            if ( userID == null){
          %>  	
            
            <ul class="nav navbar-nav navbar-rigth">
             <li class="dropdown">
                 <a href="#" class="dropdown-toggle"
                    data-toggle="dropdown" role="button" aria-haspopup="true"
                    aria-expanded="false">접속하기<span class="caret"></span></a>
                 <ul class="dropdown-menu">
                    <li><a href="login.jsp">로그인</a></li>
                    <li><a href="join.jsp">회원가입</a></li>
                 </ul>
             </li>
          </ul>
            
           <%
            }else{ //로그인이 된 사람들은 회원관리 페이지가 보이게 만들어줌
           %>
           <ul class="nav navbar-nav navbar-rigth">
             <li class="dropdown">
                 <a href="#" class="dropdown-toggle"
                    data-toggle="dropdown" role="button" aria-haspopup="true"
                    aria-expanded="false">회원관리<span class="caret"></span></a>
                 <ul class="dropdown-menu">
                    <li><a href="logoutAction.jsp">로그아웃</a></li>
                 </ul>
             </li>
          </ul>
           <%
             }
           %>
          
        </div>
     </nav>
     <div class="container">
        <div class="jumbotron">
           <div class="container">
               <h1>웹 사이트 소개</h1>
               <p>이 웹 사이트는 부트스트랩으로 만든 JSP 웹 사이트입니다. 로그인 기능과 게시판 수정, 삭제 기능을 구현하였습니다.</p>
               <p><a class="btn btn-primary btn-pull" href="https://github.com/seowonme/JSPExam001">GitHub로 이동</a></p>
           </div>
        </div>
     </div>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="js/bootstrap.js"></script>
</body>
</html>