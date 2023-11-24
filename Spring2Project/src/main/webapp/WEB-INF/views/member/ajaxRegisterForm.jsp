<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>

	<h3>Ajax 방식 요청 처리</h3>
	<hr />

	<!-- 비동기 처리할거라 form 태그 액션 지정안하고 안에있는 속성값으로 처리할거임 -->
	<form>
		userId : <input type="text" name="userid" id="userId" /><br />
		password : <input type="text" name="password" id="password" /><br />
	</form>
	<br />


	<p>3) 객체 타입의 JSON 요청 데이터 @RequestBody 어노테이션을 지정하여 자바빈즈 매개변수로 처리한다.</p>
	<button type="button" id="registerBtn03">registerBtn03</button>

	<p>4) 객체 타입의 JSON 요청 데이터는 문자열 매개변수로 처리할 수 없다.</p>
	<button type="button" id="registerBtn04">registerBtn04</button>

	<p>5) 요청 URL에 쿼리 파라미터를 붙여서 전달하면 문자열 매개변수로 처리한다.</p>
	<button type="button" id="registerBtn05">registerBtn05</button>


	<p>7) 객체 배열 타입의 JSON 요청 데이터를 자바빈즈 요소를 가진 리스트 컬렉션 매개변수에 @RequestBody 어노테이션을 지정하여 처리한다.</p>
	<button type="button" id="registerBtn07">registerBtn07</button>

	<p>8) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 중첩된 자바빈즈 매개변수로 처리한다.</p>
	<button type="button" id="registerBtn08">registerBtn08</button>

	<p>9) 중첩된 객체 타입의 JSON 요청 데이터를 @RequestBody 어노테이션을 지정하여 컬렉션 리스트 매개변수로 처리한다. (객체 안에 들어있는 리스트 변수 )</p>
	<button type="button" id="registerBtn09">registerBtn09</button>
	
	
	
</body>

<script type="text/javascript">
	$(function() {
		var registerBtn03 = $("#registerBtn03");
		var registerBtn04 = $("#registerBtn04");
		var registerBtn05 = $("#registerBtn05");
		var registerBtn07 = $("#registerBtn07");
		var registerBtn08 = $("#registerBtn08");
		var registerBtn09 = $("#registerBtn09");

		
		
		registerBtn03.on("click", function() {
			var userId = $("#userId").val();
			var password = $("#password").val();

			var userObject = {
				userId : userId,
				password : password
			}

			$.ajax({
				type : "post",
				url : "/ajax/register03", //데이터를 받는 곳
				data : JSON.stringify(userObject),
				contentType : "application/json; charset=utf-8",
				success : function(result) {
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}
				}

			});

		});

		//success 창 뜨긴 해야함...
		registerBtn04.on("click", function() {
			var userId = $("#userId").val();
			var password = $("#password").val();

			var userObject = {
				userId : userId,
				password : password
			}

			$.ajax({
				type : "post",
				url : "/ajax/register04",
				data : JSON.stringify(userObject),
				contentType : "application/json; charset=utf-8",
				success : function(result) {
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}

				}

			});

		}); // 04 끝
		
		
		
		
		
		registerBtn05.on("click", function() {
			var userId = $("#userId").val();
			var password = $("#password").val();

			var userObject = {
				userId : userId,
				password : password
			}

			$.ajax({
				type : "post", //method 방식
				url : "/ajax/register05?userId=" + userId, //데이터를 받는 곳(경로)
				data : JSON.stringify(userObject), // 보낼 데이터
				contentType : "application/json; charset=utf-8", // 보낼 데이터 MIME 형식
				success : function(result) { //성공에 대한 call back
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}
				}

			});

		}); // 05끝
		
		
		registerBtn07.on("click", function() {
			var userObjectArray = [
				{userId : "name01", password : "pw1"},
				{userId : "name02", password : "pw2"}			
			];
			
			$.ajax({
				type : "post", //method 방식
				url : "/ajax/register07", //데이터를 받는 곳(경로)
				data : JSON.stringify(userObjectArray), // 보낼 데이터
				contentType : "application/json; charset=utf-8", // 보낼 데이터 MIME 형식
				success : function(result) { //성공에 대한 call back
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}
				}

			});

		}); // 07끝
		
		
		registerBtn08.on("click", function() {
			var userId = $("#userId").val();
			var password = $("#password").val();
			
			//자바 단에서 구현하는 방식 알아둬야함
			// { }는 객체, [ ]는 리스트
			var userObject = {
				userId : userId,
				password : password,
				address : {
					postCode : "010908", //vo 객체의 변수명과 일치해야함
					location : "Daejeon"
				}					
			}
			
			
			$.ajax({
				type : "post", //method 방식
				url : "/ajax/register08", //데이터를 받는 곳(경로)
				data : JSON.stringify(userObject), // 보낼 데이터
				contentType : "application/json; charset=utf-8", // 보낼 데이터 MIME 형식
				success : function(result) { //성공에 대한 call back
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}
				}

			});

		}); // 08끝
		
		
		registerBtn09.on("click", function() {
			var userId = $("#userId").val();
			var password = $("#password").val();
			
			//자바 단에서 구현하는 방식 알아둬야함
			// { }는 객체, [ ]는 리스트
			var userObject = {
				userId : userId,
				password : password,
				cardList : [
					{no : "12345" , validMonth : "20221018"},
					{no : "67890" , validMonth : "20221019"}					
				]
			}
			
			
			$.ajax({
				type : "post", //method 방식
				url : "/ajax/register09", //데이터를 받는 곳(경로)
				data : JSON.stringify(userObject), // 보낼 데이터
				contentType : "application/json; charset=utf-8", // 보낼 데이터 MIME 형식
				success : function(result) { //성공에 대한 call back
					console.log(result);
					if (result === "SUCCESS") {
						alert(result);
					}
				}

			});

		}); // 09끝
		
		
		
		
		
		
		
		
		

		
		
	}); //전체 function 끝
</script>


</html>