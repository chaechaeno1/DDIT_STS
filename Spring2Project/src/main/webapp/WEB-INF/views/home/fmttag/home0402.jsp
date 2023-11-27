<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<h4>11) dateStyle 속성을 각 스타일로 지정하여 문자열을 Date 객체로 변환한다. </h4>
		
		<p>dateStyle 속성을 short로 지정하여 문자열을 Date 객체로 변환한다.</p>
		<p>dateValueShort : ${dateValueShort }</p>
		<fmt:parseDate value="${dateValueShort }" type="date" dateStyle="short" var="dateShort"/>
		<p>date : ${dateShort }</p><br/>

		<p>dateStyle 속성을 medium로 지정하여 문자열을 Date 객체로 변환한다.</p>
		<p>dateValueMedium: ${dateValueMedium }</p>
		<fmt:parseDate value="${dateValueMedium }" type="date" dateStyle="medium" var="dateMedium"/>
		<p>date : ${dateMedium }</p><br/>

		<p>dateStyle 속성을 long로 지정하여 문자열을 Date 객체로 변환한다.</p>
		<p>dateValueLong : ${dateValueLong }</p>
		<fmt:parseDate value="${dateValueLong }" type="date" dateStyle="long" var="dateLong"/>
		<p>date : ${dateLong  }</p><br/>

		<p>dateStyle 속성을 full로 지정하여 문자열을 Date 객체로 변환한다.</p>
		<p>dateValueFull : ${dateValueFull }</p>
		<fmt:parseDate value="${dateValueFull }" type="date" dateStyle="full" var="dateFull"/>
		<p>date : ${dateFull }</p><br/>
		



</body>
</html>