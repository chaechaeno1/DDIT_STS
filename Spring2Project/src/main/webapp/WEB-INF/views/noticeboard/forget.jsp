<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="">
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p class="h4">
				<b>아이디찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memEmail" id="memEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" name="memName" id="memName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 아이디는 [<font id="id" color="red" class="h2"></font>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="idFindBtn">아이디찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<br />
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<p href="" class="h4">
				<b>비밀번호찾기</b>
			</p>
		</div>
		<div class="card-body">
			<p class="login-box-msg">비밀번호 찾기는 아이디, 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
			<form action="" method="post">
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memId" name="memId" placeholder="아이디를 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memEmail2" name="memEmail" placeholder="이메일을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memName2" name="memName" placeholder="이름을 입력해주세요.">
				</div>
				<div class="input-group mb-3">
					<p>
						회원님의 비밀번호는 [<font color="red" class="h2" id="password"></font>] 입니다.
					</p>
				</div>
				<div class="row">
					<div class="col-12">
						<button type="button" class="btn btn-primary btn-block" id="pwFindBtn">비밀번호찾기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<br/>
	<div class="card card-outline card-secondary">
		<div class="card-header text-center">
			<h4>MAIN MENU</h4>
			<button type="button" class="btn btn-secondary btn-block" id="loginBtn">로그인</button>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	// 로그인 페이지를 요청 시, background에 이미지 삽입
	$("body").css("background-image", "url('${pageContext.request.contextPath}/resources/dist/img/background04.jpg')").css("background-size", "cover");
});




$(function(){

	//아이디 찾기 버튼 element
	var idFindBtn = $("#idFindBtn");
	
	//비밀번호 찾기 버튼 element
	var pwFindBtn = $("#pwFindBtn");
	
	

	//아이디 찾기 버튼 눌렀을 때 
	idFindBtn.on("click", function(){
		var memEmail = $('#memEmail').val();
	 	var memName = $('#memName').val();

	 	var findIdData = {
	 		memEmail : memEmail,
	 		memName : memName
	 	}

	 	  
	 	  if (memEmail == "") {
	 	   alert("이메일을 입력하세요");
	 	  $('#memEmail').focus();
	 	   return;
	 	  }

	 	  if (memName == "") {
	 	   alert("이름을 입력하세요");
	 	  $('#memName').focus();
	 	   return;
	 	  }
	 	
	 	
		
 	 	$.ajax({
	        url:'/notice/findId.do',
	        type:'POST',
	        data: JSON.stringify(findIdData),
	        contentType: "application/json; charset=utf-8",
	        // beforeSend 콜백 함수 : AJAX 요청이 서버로 전송되기 전에 호출
	        // = 클라이언트가 서버로 AJAX 요청을 보내기 전에 실행되는 함수
	        // beforeSend는 AJAX 요청을 실제로 서버로 보내기 전에 선행 작업을 수행할 수 있는 콜백 함수
	        // 이 함수에서는 HTTP 요청 헤더에 특정 정보를 추가합니다.
	        beforeSend : function(xhr){
	        	//요청 헤더에 header라는 이름의 헤더 필드를 추가하고, 그 값으로 token을 설정합니다. 
	        	//이는 서버에서 요청을 인증하기 위한 토큰을 헤더에 포함하는 일반적인 방법입니다.
	        	xhr.setRequestHeader(header, token);
	        },	
	        success:function(data){
	        	console.log(data);
	        	$("#id").text(data);
	        	
	        },
	        
	        
	    }); 
	    
	});
	
	
	//비밀번호 찾기 버튼 눌렀을 때 
	pwFindBtn.on("click", function(){
		var memId = $('#memId').val();
		var memEmail = $('#memEmail2').val();
	 	var memName = $('#memName2').val();
	 	
	 	var findPwData = {
	 		memId : memId,
	 		memEmail : memEmail,
	 		memName : memName
	 	}

	 	  if (memId == "") {
	 	   alert("아이디를 입력하세요");
	 	  $('#memId').focus();
	 	   return;
	 	  }
	 	
	 	  if (memEmail == "") {
	 	   alert("이메일을 입력하세요");
	 	  $('#memEmail').focus();
	 	   return;
	 	  }

	 	  if (memName == "") {
	 	   alert("이름을 입력하세요");
	 	  $('#memName').focus();
	 	   return;
	 	  }
	 	  
	 	  
	 	$.ajax({
		      url:'/notice/findPw.do',
		      type:'POST',
		      data: JSON.stringify(findPwData),
		      contentType: "application/json; charset=utf-8",
	          beforeSend : function(xhr){
	        	xhr.setRequestHeader(header, token);
	          },	
		      success:function(data){
		    	  $("#password").text(data);
        	

        },
    }); 
	    
	 	  
		
		
		
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});
	
	
	

















</script>