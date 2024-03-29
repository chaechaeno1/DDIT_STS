<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD REGISTER</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>

	<h2>REGISTER</h2>
	<form:form modelAttribute="registerForm" action="/crud/board/register" method="post">
		<c:if test="${status eq 'u' }">
			<input type="hidden" name="boardNo" value="${board.boardNo }"/>
		</c:if>
		<table border="1">
			<tr>
				<td>제목</td>
				<td>
				<form:input path="title2"/>
				<%-- <input type="text" name="title" id="title" value="${board.title }"/> --%>
				</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="writer" id="writer" value="${board.writer }"/></td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea rows="10" cols="40" name="content" id="content">${board.content }</textarea>
				</td>
			</tr>
		</table>
		<div>
			<c:set value="등록" var="btnText"/>
			
			<c:if test="${status eq 'u' }">
				<c:set value="수정" var="btnText"/>
			</c:if>
			<input type="button" id="registerBtn" value="${btnText}"/>
			
			<c:if test="${status eq 'u' }">
				<input type="button" id="cancelBtn" value="취소"/>
			</c:if>
			
			<c:if test="${status ne 'u' }">
				<input type="button" id="listBtn" value="목록"/>
			</c:if>
		</div>
	
	</form:form>

</body>

<script type="text/javascript">
$(function(){
	var registerBtn = $("#registerBtn");
	var listBtn = $("#listBtn");
	var cancelBtn = $("#cancelBtn");
	var registerForm = $("#registerForm");
	
	//클릭이벤트
	registerBtn.on("click", function(){
		var title = $("#title").val();
		var writer = $("#writer").val();
		var content = $("#content").val();
		
		if(title == null || title == ""){
			alert("제목을 입력해주세요!");
			return false;
		}
		
		if(writer == null || writer == ""){
			alert("작성자를 입력해주세요!");
			return false;
		}
		
		if(content == null || content == ""){
			alert("내용을 입력해주세요!");
			return false;
		}
		
		
		//수정일 때 수정 경로로 변경한다.
		if($(this).val() == "수정"){
			registerForm.attr("action", "/crud/board/modify");
		}
		
		//등록
		registerForm.submit(); //전송!!!!!!!!!
		
		
	});
	
	listBtn.on("click", function(){
		location.href = "/crud/board/list";
		
	});
	
	cancelBtn.on("click", function(){
		location.href = "/crud/board/read?boardNo=${board.boardNo}";
		
	});
	
	
	
	
});

</script>



</html>