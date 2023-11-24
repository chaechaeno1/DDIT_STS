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

	<h3>READ05 - Result</h3>
	userId : ${user.userId }<br/>
	password : ${user.password}<br/>
	email : ${user.email}<br/>
	userName : ${user.userName}<br/>
	birthDay : ${user.birthDay}<br/>
	gender : ${user.gender}<br/>
	hobby : ${user.hobby}<br/>
	
		<p>[hobbyArray]</p>
		<c:forEach items="${user.hobbyArray }" var="hobby">
			<c:out value="${hobby }"/>
		</c:forEach>
		
		<p>[hobbyList]</p>	
		<c:forEach items="${user.hobbyList }" var="hobby">
			<c:out value="${hobby }"/>
		</c:forEach>
	<br/>	
	<br/>
		
	developer : ${user.developer}<br/>
	foreigner : ${user.foreigner}<br/>
	nationality : ${user.nationality}<br/>
	address.postCode : ${user.address.postCode}<br/>
	address.location : ${user.address.location}<br/>
	
		<p>[cardList]</p>	
		<c:forEach items="${user.cardList }" var="card">
			<c:out value="${card.no } // ${card.validMonth } // " />
		</c:forEach>	
	
	<br/>
	
		<p>[carList]</p>	
		<c:forEach items="${user.carList }" var="car">
			<c:out value="${car }" />
		</c:forEach>	
	<br/>	
	<br/>
	introduction : ${user.introduction}<br/>
	dateOfBirth : ${user.dateOfBirth}<br/>
	

	
	

</body>
</html>