<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD MEMBER REGISTER</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>

	<h2>REGISTER</h2>
	<form action="/crud/member/register" method="post" id="member">
		<table border="1">
			<tr>
				<td>userId</td>
				<td>
					<input type="text" name="userId" id="userId"/>
				</td>
			</tr>
			<tr>
				<td>userPw</td>
				<td>
					<input type="text" name="userPw" id="userPw"/>
				</td>
			</tr>
			<tr>
				<td>userName</td>
				<td>
					<input type="text" name="userName" id="userName"/>
				</td>
			</tr>
		</table>
		<input type="button" id="registerBtn" value="register"/>
		<input type="button" id="listBtn" value="list"/>
	</form>


</body>


<script type="text/javascript">
$(function(){
	var member = $("#member"); // form
	var registerBtn = $("#registerBtn");
	var listBtn = $("#listBtn");
	
	registerBtn.on("click",function(){
		var userId = $("#userId").val();
		var userPw = $("#userPw").val();
		var userName = $("#userName").val();
		
		if(userId == null || userId == ""){
			alert("아이디를 입력해주세요!");
			return false;
		}
		
		if(userPw == null || userPw == ""){
			alert("비밀번호를 입력해주세요!");
			return false;
		}
		
		if(userName == null || userName == ""){
			alert("이름을 입력해주세요!");
			return false;
		}
		
		member.submit(); //정상적인 데이터가 입력되었을 때 submit 한다 ( 서버 전송 )
		
		
	});
	
	listBtn.on("click",function(){
		location.href="/crud/member/list";
		
	});
	
});

</script>










</html>