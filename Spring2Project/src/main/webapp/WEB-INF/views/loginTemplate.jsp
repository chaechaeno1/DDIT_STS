<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AdminLTE 3 | Log in</title>


<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/fontawesome-free/css/all.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/plugins/icheck-bootstrap/icheck-bootstrap.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dist/css/adminlte.min.css">

<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>

</head>
<c:if test="${not empty message }">
<script type="text/javascript">
alert("${message}");
</script>
<c:remove var="message" scope="request"/> <!-- 리다이렉트로 진행해서 어차피 일회성이지만 한번 더 remove 처리하는 것  -->
<c:remove var="message" scope="session"/>

</c:if>


<body class="hold-transition ${bodyText}">
	<!-- content 영역 -->
	<tiles:insertAttribute name="content"/>



	<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.min.js"></script>
</body>
</html>
