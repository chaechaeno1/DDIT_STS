<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RegisterForm</title>
</head>
<body>
	<h3>REGISTER FORM</h3>
	<hr/><br/>
	
	<h4>2. 요청 처리</h4>
	<hr/>
	
	<p>1) URL 경로 상의 쿼리 파라미터 정보로부터 요청 데이터를 취득할 수 있다.</p>
	<a href="/register?userId=hongkd&password=1234">Button1</a>
	<br/>
	
	<p>2) URL 경로 상의 경로 변수로부터 요청 데이터를 취득할 수 있다.</p>
	<a href="register/hongkd">Button2</a>
	
	<p>
		3) HTML Form 필드명과 컨트롤러 매개변수명이 일치하면 요청 데이터를 취득할 수 있다.<br/>	
	</p>
	<form action="/register01" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<p>4) HTML Form 필드 값이 숫자일 경우에는 숫자로 타입 변환되어 데이터를 취득할 수 있다. </p>
	<form action="/register02" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<h4>3. 요청 데이터 처리 어노테이션</h4>
	<hr/>
	
	<p>1) URL 경로 상의 경로 변수가 여러 개일 때, @PathVariable 어노테이션을 사용하여 특정한 경로 변수명을 지정해준다.</p>
	<a href="/register/hongkd/100">Button3</a><br/>
	
	
	<p>2) @RequestParam 어노테이션을 사용하여 특정한 HTML Form의 필드명을 지정하여 요청을 처리 한다. </p>
	<form action="/register0202" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	

</body>
</html>