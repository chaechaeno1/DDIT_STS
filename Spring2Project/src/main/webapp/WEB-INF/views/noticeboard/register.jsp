<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="register-box">
	<div class="card card-outline card-danger mt-4 mb-4">
		<div class="card-header text-center">
			<a href="" class="h1"><b>DDIT</b>BOARD</a>
		</div>
		<div class="card-body">
			<p class="login-box-msg">회원가입</p>

			<form action="/notice/signup.do" method="post" id="signupForm" enctype="multipart/form-data">
				<div class="input-group mb-3 text-center">
					<img class="profile-user-img img-fluid img-circle" id="profileImg"
						src="${pageContext.request.contextPath}/resources/dist/img/AdminLTELogo.png" alt="User profile picture"
						style="width: 150px;">
				</div>
				<div class="input-group mb-3">
					<label for="inputDescription">프로필 이미지</label>
				</div>
				<div class="input-group mb-3">
					<div class="custom-file">
						<input type="file" class="custom-file-input" name="imgFile"id="imgFile"> 
						<label class="custom-file-label" for="imgFile">프로필 이미지를 선택해주세요</label>
					</div>
				</div>
				<div class="input-group mb-3">
					<label for="inputDescription">프로필 정보</label>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memId" name="memId"
						placeholder="아이디를 입력해주세요"> <span
						class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat"
							id="idCheckBtn">중복확인</button>
					</span> <span class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPw" name="memPw"
						placeholder="비밀번호를 입력해주세요"> <span
						class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memName" name="memName"
						placeholder="이름을 입력해주세요"> <span
						class="error invalid-feedback" style="display: block;"></span>
				</div>
				<div class="input-group mb-3">
					<div class="form-group clearfix">
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderM" name="memGender" value="M"
								checked="checked"> <label for="memGenderM">남자&nbsp;</label>
						</div>
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderF" name="memGender" value="F">
							<label for="memGenderF">여자 </label>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memEmail"
						name="memEmail" placeholder="이메일을 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPhone"
						name="memPhone" placeholder="전화번호를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPostCode"
						name="memPostCode"> <span class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat">우편번호
							찾기</button>
					</span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memAddress1"
						name="memAddress1" placeholder="주소를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memAddress2"
						name="memAddress2" placeholder="상세주소를 입력해주세요">
				</div>
				<div class="row">
					<div class="col-8">
						<div class="icheck-primary">
							<input type="checkbox" id="memAgree" name="memAgree" value="Y">
							<label for="memAgree">개인정보처리방침</label>
						</div>
					</div>
					<div class="col-4">
						<button type="button" class="btn btn-dark btn-block"
							id="signupBtn">가입하기</button>
					</div>
					<button type="button" class="btn btn-secondary btn-block mt-4"
						onclick="javascript:location.href='/notice/login.do'">뒤로가기</button>
				</div>
			</form>
		</div>
	</div>
</div>



<script type="text/javascript">
$(function(){
	// 로그인 페이지에서 사용할 배경 이미지 설정
	$("body").css("background-image","url('${pageContext.request.contextPath}/resources/dist/img/background04.jpg')")
		.css("background-size", "cover");
	
	
	var signupForm = $("#signupForm"); // 폼 태그 Element
	var signupBtn = $("#signupBtn"); // 가입하기 버튼 Element
	var idCheckBtn = $("#idCheckBtn"); // 중복확인 버튼 Element
	var idCheckFlag = false;			//중복확인 flag
	
	var imgFile = $("#imgFile"); // 파일 선택 Element
	
	
	idCheckBtn.on("click",function(){
		var id = $("#memId").val();
		
		if(id == null || id == ""){
			alert("아이디를 입력해주세요!");
			return false;
			
		}
		
		
		var data = {
			memId : id				
		}
		
		$.ajax({
			type : "post",
			url : "/notice/idCheck.do", //맨 앞쪽 '/' 빼먹지 말기...
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			success : function(res){
				console.log("중복확인 후 넘겨받은 결과 : " + res);
				
				if(res == "NOTEXIST"){ //아이디 사용 가능
					alert("사용 가능한 아이디 입니다!");
					idCheckFlag = true; //중복 확인 했다는 flag 설정
				}else{					// 아이디 중복
					alert("이미 사용중인 아이디 입니다!");
				}
					
				}
				
			
		});
		
		
		
	});
	
	//imgFile -> input
	//profileImg -> img
	
	imgFile.on("change", function(event){
		var file = event.target.files[0];
		
		if(isImagefile(file)){ //이미지 파일이 맞다면!
			var reader = new FileReader();
			reader.onload = function(e){
				$("#profileImg").attr("src", e.target.result); //프로필 이미지 출력단에 src를 타켓결과로 출력
			}
			reader.readAsDataURL(file); 			
		}else{ //이미지 파일이 아닐 때
			alert("이미지 파일을 선택해주세요!");
		}
	});
});


// 이미지 파일인지 체크 
function isImagefile(file){
	var ext = file.name.split(".").pop().toLowerCase(); // 파일명에서 확장자를 가져온다.
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true; 
	// 이미지 파일이 아닌 경우(jpg, jpeg, gif, png가 아닌 경우)에  -1 을 출력 -> false 출력
	// inArray안에 있는 확장자 중 하나라도 맞으면 true 출력
	
	
}















</script>

