<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form:form modelAttribute="member" method="post" action="/formtag/selectbox/result">
		<table>
			<tr>
				<td>국적</td>
				<td>
					<form:select path="nationality" items="${nationalityCodeMap}" multiple="true"/>
				</td>
			</tr>
		</table>
		<form:button name="register">등록</form:button> <!-- form:button의 기본타입 submit -->
	</form:form>
</body>
</html>