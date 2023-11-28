<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CRUD REGISTER</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>

	<h2>REGISTER</h2>
	<form action="/crud/board/register" method="post" id="registerForm">
		<table border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" id="title" value=""/></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="writer" id="writer" value=""/></td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea rows="10" cols="40" name="content" id="content"></textarea>
				</td>
			</tr>
		</table>
		<div>
			<input type="button" id="registerBtn" value="등록"/>
			<input type="button" id="listBtn" value="목록"/>
			<input type="button" id="cancelBtn" value="취소"/>
		</div>
	
	</form>

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
		
		registerForm.submit(); //전송!!!!!!!!!
		
		
	});
	
	listBtn.on("click", function(){
		location.href = "/crud/board/list";
		
	});
	
	cancelBtn.on("click", function(){
		
	});
	
	
	
	
});

</script>



</html>