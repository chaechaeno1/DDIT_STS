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
	<p>c:forTokens</p>


	<c:forTokens items="${member.hobby }" delims="," var="hobby">
		${hobby }<br/>
	</c:forTokens>	


</body>
</html>