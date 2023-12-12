<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="register-box">
	<div class="card card-outline card-danger mt-4 mb-4">
		<div class="card-header text-center">
			<a href="" class="h1"><b>DDIT</b>BOARD</a>
		</div>
		<div class="card-body">
			<p class="login-box-msg">회원가입</p>

			<form action="/notice/signup.do" method="post" id="signupForm"
				enctype="multipart/form-data">
				<div class="input-group mb-3 text-center">
					<img class="profile-user-img img-fluid img-circle" id="profileImg"
						src="${pageContext.request.contextPath}/resources/dist/img/AdminLTELogo.png"
						alt="User profile picture" style="width: 150px;">
				</div>
				<div class="input-group mb-3">
					<label for="inputDescription">프로필 이미지</label>
				</div>
				<div class="input-group mb-3">
					<div class="custom-file">
						<input type="file" class="custom-file-input" name="imgFile"
							id="imgFile"> <label class="custom-file-label"
							for="imgFile">프로필 이미지를 선택해주세요</label>
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
						<button type="button" class="btn btn-secondary btn-flat"
							onclick="DaumPostcode()">우편번호 찾기</button>
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


				<!-- 영역 새로 추가 -->
				<div class="input-group mb-3">
					<div id="map" style="width: 100%; height: 300px; display: none;"></div>
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
				
				<sec:csrfInput/>
				
			</form>
		</div>
	</div>
</div>

<!-- 주소 cdn -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 지도 cdn -->
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c47c8b99b8c6a581123bf928fa9705fa&libraries=services"></script>

<script type="text/javascript">
	$(function() {
		// 로그인 페이지에서 사용할 배경 이미지 설정
		$("body")
				.css("background-image",
						"url('${pageContext.request.contextPath}/resources/dist/img/background04.jpg')")
				.css("background-size", "cover");

		var signupForm = $("#signupForm"); // 폼 태그 Element
		var signupBtn = $("#signupBtn"); // 가입하기 버튼 Element
		
		
		var idCheckBtn = $("#idCheckBtn"); // 중복확인 버튼 Element
		var idCheckFlag = false; //중복확인 flag

		var imgFile = $("#imgFile"); // 파일 선택 Element
		
		var memAddress2 = $("#memAddress2"); // 상세주소 Element

		//KaKaoMap API 사용 자원들
		var mapContainer = null;
		var map = null;
		var geocoder = null;

		idCheckBtn.on("click", function() {
			var id = $("#memId").val();

			if (id == null || id == "") {
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
	          	beforeSend : function(xhr){
		        	xhr.setRequestHeader(header, token);
		        },	
				contentType : "application/json;charset=utf-8",
				success : function(res) {
					console.log("중복확인 후 넘겨받은 결과 : " + res);

					if (res == "NOTEXIST") { //아이디 사용 가능
						alert("사용 가능한 아이디 입니다!");
						idCheckFlag = true; //중복 확인 했다는 flag 설정
					} else { // 아이디 중복
						alert("이미 사용중인 아이디 입니다!");
					}

				}

			});

		}); // idCheckBtn click 이벤트 끝

		//imgFile -> input
		//profileImg -> img

		// imgFile 요소에 대한 change 이벤트 핸들러를 등록
		// (이벤트: 파일이 선택되면 이 핸들러가 실행됨)
		imgFile.on("change", function(event) {
			// 선택된 파일 정보를 가져옴
			var file = event.target.files[0];

			// isImagefile 함수를 사용하여 선택된 파일이 이미지 파일인지 확인
			if (isImagefile(file)) { //이미지 파일이 맞다면!
				// FileReader 객체를 생성
				var reader = new FileReader();
				// 파일을 읽었을 때 실행되는 이벤트 핸들러
				reader.onload = function(e) {
					// 프로필 이미지를 표시하는 <img> 요소의 src 속성을 읽은 파일의 데이터 URL로 설정
					$("#profileImg").attr("src", e.target.result); //프로필 이미지 출력단에 src를 타켓결과로 출력
				}
				// 선택된 이미지 파일을 데이터 URL로 변환하여 읽음
				reader.readAsDataURL(file);
			} else { //이미지 파일이 아닐 때
				alert("이미지 파일을 선택해주세요!");
			}
		});

		memAddress2.focusout(function() {
					var address1 = $("#memAddress1").val();
					var address2 = $("#memAddress2").val();

					if ((address1 != null && address1 != "")|| (address2 != null && address2 != "")) {
						//map 공간에 지도를 출력
						mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapContainer.style.display = "block"; //안뜨는 오류 해결
						mapOption = {
							center : new kakao.maps.LatLng(33.450701,
									126.570667), // 지도의 중심좌표
							level : 3
						// 지도의 확대 레벨
						};

						// 지도를 생성합니다    
						map = new kakao.maps.Map(mapContainer, mapOption);

						// 주소-좌표 변환 객체를 생성합니다
						geocoder = new kakao.maps.services.Geocoder();

						// 주소로 좌표를 검색합니다(입력받은 주소로 최종 주소 만듦)
						geocoder.addressSearch(
										address1 + " " + address2,
										function(result, status) {

											// 정상적으로 검색이 완료됐으면 
											if (status === kakao.maps.services.Status.OK) {

												var coords = new kakao.maps.LatLng(
														result[0].y,
														result[0].x);

												// 결과값으로 받은 위치를 마커로 표시합니다
												var marker = new kakao.maps.Marker(
														{
															map : map,
															position : coords
														});

												// 인포윈도우로 장소에 대한 설명을 표시합니다
												var infowindow = new kakao.maps.InfoWindow(
														{
															content : '<div style="width:150px;text-align:center;padding:6px 0;">HOME</div>'
														});
												infowindow.open(map, marker);

												// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
												map.setCenter(coords);
											}
										}); // addressSearch 끝

						$("#card-signup").css("top", "140px"); //지도가 생성될 경우 윗 배경이 틀어지는 것을 방지하기 위해 css 추가

					}

				});

		signupBtn.on("click", function() {
			//아이디, 비밀번호, 이름까지만 일반적인 데이터 검증
			// 개인정보 처리방침 동의를 체크했을때만 가입하기가 가능하도록
			// 중복확인 처리가 true 일때만 가입하기가 가능하도록

			var agreeFlag = false;
			var memId = $("#memId").val();
			var memPw = $("#memPw").val();
			var memName = $("#memName").val();

			if (memId == null || memId == "") {
				alert("아이디를 입력해주세요.");
				return false;
			}
			if (memPw == null || memPw == "") {
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			if (memName == null || memName == "") {
				alert("이름을 입력해주세요.");
				return false;
			}

			//개인정보 처리방침을 동의하게 되면 Y값이 넘어옴, 동의 여부는 true로 설정한다.
			var memAgree = $("#memAgree:checked").val();
			if (memAgree == "Y") {
				agreeFlag = true;
			}

			//개인정보 처리방침을 동의했습니까?
			//아이디 중복 체크를 이행하고 오셨습니까?
			//오셨다구요? 그럼 가입하기를 진행하겠습니다.
			// 예? 안했다구요? 그럼 가입하기를 할 수 없습니다!

			if (agreeFlag) { //처리방침 Y
				if (idCheckFlag) {
					signupForm.submit();
				} else {
					alert("아이디 중복체크를 해주세요!");
				}
			} else { //처리방침 안했음..
				alert("개인정보 동의를 체크해주세요!");
			}

		});

	});

	// 이미지 파일인지 체크 
	function isImagefile(file) {
		var ext = file.name.split(".").pop().toLowerCase(); // 파일명에서 확장자를 가져온다.
		return ($.inArray(ext, [ "jpg", "jpeg", "gif", "png" ]) === -1) ? false
				: true;
		// 이미지 파일이 아닌 경우(jpg, jpeg, gif, png가 아닌 경우)에  -1 을 출력 -> false 출력
		// inArray안에 있는 확장자 중 하나라도 맞으면 true 출력

	}

	function DaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if (data.userSelectedType === 'R') {
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', '
								+ data.buildingName : data.buildingName);
					}
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('memPostCode').value = data.zonecode;
				document.getElementById("memAddress1").value = addr;

				// 커서를 상세주소 필드로 이동한다. (직접입력하는 칸으로 focus)
				document.getElementById("memAddress2").focus();
			}
		}).open();
	}
</script>

