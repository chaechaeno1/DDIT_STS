<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

	<h1>Login</h1>
	<h2>${error}</h2>	<!-- 에러발생시, 출력할 메시지 -->
	<h2>${logout}</h2>	<!-- 에러발생시, 출력할 메시지 -->
	
	<!-- security를 적용 후 데이터를 전송 시 csrfInput으로 시큐리티 토큰을 전송해야 한다. -->
	<form method="post" action="/login">
		username : <input type="text" name="username"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="checkbox" name="remember-me"/> Remember Me 
		<input type="submit" value="로그인"/>
		<sec:csrfInput/>
	</form>

</body>
</html>