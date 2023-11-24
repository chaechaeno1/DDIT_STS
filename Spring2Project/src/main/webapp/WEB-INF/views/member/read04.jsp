<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>READ04 - Result</h3>
	
	<h4>carArray</h4>
	<hr/>
	<c:forEach items="${carArray }" var="car">
		<c:out value="${car }"/>&nbsp;	
	</c:forEach>
	<br/><hr/>
	
	<h4>carList</h4>
	<hr/>
	<c:forEach items="${carList }" var="car">
		<c:out value="${car }"/>&nbsp;	
	</c:forEach>
	<br/><hr/>
	
	<h4>hobbyArray</h4>
	<hr/>
	<c:forEach items="${hobbyArr}" var="hobby">
		<c:out value="${hobby }"/>&nbsp;	
	</c:forEach>
	<br/><hr/>

	<h4>hobbyList</h4>
	<hr/>
	<c:forEach items="${hobbyList}" var="hobby">
		<c:out value="${hobby}"/>&nbsp;	
	</c:forEach>
	<br/><hr/>
	
	
	

</body>
</html>