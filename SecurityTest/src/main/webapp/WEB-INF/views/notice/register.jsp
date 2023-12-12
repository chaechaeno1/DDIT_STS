<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NOTICE LIST</title>
</head>
<body>
	<h3>NOTICE LIST : access to admin</h3>
	
	<sec:authentication property="principal.member.userName" var="name"/>
	<sec:authentication property="principal.member.userId" var="id"/>
	<sec:authentication property="principal.member.userPw" var="pw"/>
	
	<p>
		사용자명 : ${name } <br/>
		아이디 : ${id } <br/>
		비밀번호 : ${pw } <br/>
	</p>
	
	<p>principal : <sec:authentication property="principal"/></p>
	<p>principal.member : <sec:authentication property="principal.member"/></p>
	
	
	<p>
		<sec:authorize access="hasRole('ROLE_MEMBER')">
			${name}님의 역할명은 '회원'입니다!
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			${name}님의 역할명은 '관리자'입니다!
		</sec:authorize>
	</p>

</body>
</html>


