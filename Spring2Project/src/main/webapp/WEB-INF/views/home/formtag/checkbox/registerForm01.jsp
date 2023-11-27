<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- modelAttribute에 member는 컨트롤러에서 선언한 key값이어야함. 내가 넘기고 있는 프로퍼티도 같아야 함 -->

	<form:form modelAttribute="member" method="post" action="/formtag/checkbox/result">
		<table	border="1">
			<tr>
				<td>개발자 여부</td>
				<td>
					<form:checkbox path="developer" value="Y"/>
				</td>
			</tr>
			<tr>
				<td>외국인 여부</td>
				<td>
					<form:checkbox path="foreigner" value="true"/>
				</td>
			</tr>
			<tr>
				<td>취미(hobby)</td>
				<td>
					<form:checkbox path="hobby" value="Sports" label="Sports"/>
					<form:checkbox path="hobby" value="Music" label="Music"/>
					<form:checkbox path="hobby" value="Movie" label="Movie"/>
				</td>
			</tr>
			<tr>
				<td>취미(hobbyArray)</td>
				<td>
					<form:checkbox path="hobbyArray" value="Sports" label="Sports"/>
					<form:checkbox path="hobbyArray" value="Music" label="Music"/>
					<form:checkbox path="hobbyArray" value="Movie" label="Movie"/>
				</td>
			</tr>
			<tr>
				<td>취미(hobbyList)</td>
				<td>
					<form:checkbox path="hobbyList" value="Sports" label="Sports"/>
					<form:checkbox path="hobbyList" value="Music" label="Music"/>
					<form:checkbox path="hobbyList" value="Movie" label="Movie"/>
				</td>
			</tr>

		</table>
		<form:button name="register">등록</form:button>	
	</form:form>
</body>
</html>