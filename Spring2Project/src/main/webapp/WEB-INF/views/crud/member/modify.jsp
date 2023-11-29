<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD MEMBER MODIFY</title>
</head>
<body>

	<h2>MODIFY</h2>
	<form action="/crud/member/modify" method="post" id="member">
		<input type="hidden" id="userNo" name="userNo" value="${member.userNo }"/>
		
		<table border="1">
			<tr>
				<td>userId</td>
				<td>
					<input type="text" name="userId" value="${member.userId }" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>userName</td>
				<td>
					<input type="text" name="userName" id="userName" value="${member.userName }" />
				</td>
			</tr>
			
			<tr>
				<td>auth-1</td>
				<td>
					<select name="authList[0].auth">
						<option value="">---선택---</option>
						<option value="ROLE_USER">사용자</option>
						<option value="ROLE_MEMBER">회원</option>
						<option value="ROLE_ADMIN">관리자</option>
					</select>
				</td>
			</tr>
		
			<tr>
				<td>auth-2</td>
				<td>
					<select name="authList[1].auth">
						<option value="">---선택---</option>
						<option value="ROLE_USER">사용자</option>
						<option value="ROLE_MEMBER">회원</option>
						<option value="ROLE_ADMIN">관리자</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>auth-3</td>
				<td>
					<select name="authList[2].auth">
						<option value="">---선택---</option>
						<option value="ROLE_USER">사용자</option>
						<option value="ROLE_MEMBER">회원</option>
						<option value="ROLE_ADMIN">관리자</option>
					</select>
				</td>
			</tr>
			
			
		</table>
		
		
	</form>


</body>
</html>