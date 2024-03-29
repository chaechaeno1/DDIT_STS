<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- csrf meta 정보를 등록 -->
<meta id="_csrf" name="_csrf" content="${_csrf.token}">
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}">


<title>AdminLTE 3 | Simple Tables</title>


<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/plugins/fontawesome-free/css/all.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dist/css/adminlte.min.css">
	<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>


<script type="text/javascript">
var token = "";
var header = "";
$(function(){
	token = $("meta[name='_csrf']").attr("content");
	header = $("meta[name='_csrf_header']").attr("content");
});
</script>



</head>



<c:if test="${not empty message }">
<script type="text/javascript">
alert("${message}");
</script>
<c:remove var="message" scope="request"/> <!-- 리다이렉트로 진행해서 어차피 일회성이지만 한번 더 remove 처리하는 것  -->
<c:remove var="message" scope="session"/>

</c:if>







<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<!-- header 영역 -->
		<tiles:insertAttribute name="header"/>
		

		<div class="content-wrapper">
			<!-- content 영역  -->	
			<tiles:insertAttribute name="content"/>
	
		</div>

		<!-- footer 영역 -->
		<tiles:insertAttribute name="footer"/>
	
	</div>
		
	
	<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>	
	<script src="${pageContext.request.contextPath}/resources/plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>		
	<script src="${pageContext.request.contextPath}/resources/dist/js/adminlte.min.js"></script>
	<script type="text/javascript">
	$(function(){
		bsCustomFileInput.init(); // 부트스트랩 openFile 이벤트 설정(템플릿 안에서 가용되는것, 반드시 정해져있는 건 아님)
	});
	
	</script>
</body>
</html>














