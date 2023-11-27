<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>7장 JSP</h3>
	<p>4) c:catch </p>

	<!-- EL문 안에서 발생하는 에러는 하나하나씩 잘라서 확인해봐야함 -->
	<c:catch var="ex">
		${member.hobbyArray[3] }
	</c:catch>
	
	<table border="1">
		<tr>
			<td>에러 발생 내용</td>
			<td>
				<c:if test="${ex != null}">
					${ex }
				</c:if> 
			</td>
		</tr>	
	</table>
	


</body>
</html>