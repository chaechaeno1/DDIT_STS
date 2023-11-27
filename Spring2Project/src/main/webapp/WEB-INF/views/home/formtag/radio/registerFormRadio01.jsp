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
	
	<form:form modelAttribute="member" method="post" action="/formtag/radio/result">
		<table	border="1">
			<tr>
				<td>성별</td>
				<td>
					<form:radiobutton path="gender" value="male" label="Male"/>
					<form:radiobutton path="gender" value="female" label="Female"/>
					<form:radiobutton path="gender" value="other" label="Other"/>
				</td>
			</tr>

		</table>
		<form:button name="register">등록</form:button>	
	</form:form>
</body>
</html>