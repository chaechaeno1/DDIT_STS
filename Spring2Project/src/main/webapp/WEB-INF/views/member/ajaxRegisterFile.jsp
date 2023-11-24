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


	<h3>10. 파일 업로드 Ajax 방식 요청 처리</h3>
	<hr/>
	
	<p>Ajax 방식으로 전달할 파일 요소값을 스프링 MVC가 지원하는 MultipartFile 매개변수로 처리한다.</p>
	<div>
		<input type="file" id="inputFile"/><br/>
		<hr/>
		<img id="preview"/>
	</div>

</body>

<script type="text/javascript">

$(function(){
	// 이 코드는 문서가 준비되었을 때 실행됩니다.
	
	var inputFile = $("#inputFile");
	
	inputFile.on("change", function(event){
		// 이 코드는 파일 입력 값이 변경되었을 때 실행됩니다.
		console.log("change...!");
		
		
		//이 부분은 선택된 파일의 정보를 변수에 저장하여 나중에 사용하기 위한 것입니다. 이 정보에는 파일의 이름, 크기, 유형 등이 포함되어 있습니다.
		var files = event.target.files; // 파일 입력 필드에서 선택한 파일(들)의 정보를 가져옵니다. 
		var file = files[0]; //위에서 가져온 파일 목록에서 첫 번째 파일을 선택합니다. 
		//대부분의 경우 파일 입력 필드는 단일 파일 선택을 허용하므로 배열에서 첫 번째 파일을 가져오는 것이 일반적입니다. 
		//이 파일은 이후의 처리에 사용됩니다.
		
		console.log("file : "+file);
		
		
		//★★★★★비동기 방식에서 파일 전송할 때 무조건 formData 사용★★★★★
		//formData는 key = value 형식으로 데이터가 저장된다. (append 메서드 이용해서 작성한다.)
		//append 메서드를 사용하여 FormData 객체에 새로운 키-값 쌍을 추가합니다. 
		
		//dataType : 응답(response) 데이터를 내보낼 때 보내줄 데이터 타입
		//★processData : 데이터 파라미터를 data라는 속성으로 넣는데, 제이쿼리 내부적으로 쿼리스트링을 구성함. 파일 전송의 경우 쿼리스트링을 사용하지 않으므로 해당 설정을 false로 비활성화.
		//★contentType : Content-Type을 설정 시, 사용하는데 해당 설정의 기본 값은
		//				'application/x-www-form-urlencoded; charset=utf-8' 입니다.
		//				하여, 기본값으로 나가지 않고, 'multipart/form-data'로 나갈 수 있도록 설정을 false 로 합니다.
		//				request 요청에서 Content-Type을 확인해보면 'multipart/form-data; boundary=====WebkitFormBoundary[HashKey]'와
		//				같은 값으로 전송된 것을 확인할 수 있음.
		
		
		if(isImageFile(file)){ // 이미지 파일일 때
			var formData = new FormData(); // 새로운 FormData 객체를 생성합니다. FormData는 HTML 폼의 데이터를 쉽게 수집하고 전송할 수 있는 객체입니다.
			formData.append("file", file);			
			// 여기서 "file"은 서버에서 해당 파일을 식별할 수 있는 키(key)이고, file은 앞에서 선택한 파일 변수입니다. 
			//이렇게 하면 이후에 AJAX 요청으로 이 데이터를 서버에 보낼 수 있게 됩니다.
			
			
			//요약하면, 이 코드는 AJAX를 사용하여 파일을 서버에 보내고, 
			//업로드가 성공했는지를 나타내는 텍스트 응답을 기대하며, 성공한 경우 클라이언트 측에서 업로드한 이미지의 미리보기를 표시합니다.
			$.ajax({
				type : "post",
				url : "/ajax/uploadAjax",
				data : formData,
				dataType : "text", // 서버에서 기대되는 데이터 유형을 지정합니다. 이 경우 서버는 일반 텍스트로 응답해야 합니다.
				processData : false, // jQuery가 데이터를 자동으로 처리하는 것을 방지합니다. 이것은 이미 FormData 형식으로 된 데이터를 보내고 있기 때문에 false로 설정됩니다.
				contentType : false, //  jQuery가 자동으로 Content-Type 헤더를 설정하는 것을 방지합니다. formData의 내용에 따라 올바른 Content-Type이 자동으로 설정됩니다.
				success : function(data){
					alert(data);
					if(data === "UPLOAD SUCCESS"){
						var file = event.target.files[0]; //입력 필드에서 선택한 파일을 가져옵니다.
						var reader = new FileReader(); //파일 내용을 읽을 수 있는 FileReader 객체를 생성합니다.
						reader.onload = function(e){ //파일 읽기가 완료될 때 실행될 콜백 함수를 정의합니다.
							$("#preview").attr("src",e.target.result);
							//"preview"라는 ID를 가진 HTML 요소의 소스 속성을 선택한 이미지의 base64로 인코딩된 데이터로 설정합니다. 
							//이는 업로드한 이미지의 미리보기를 표시하는 데 일반적으로 사용됩니다.
						}
						reader.readAsDataURL(file);
						//파일을 데이터 URL로 읽기(베이스64 인코딩)를 시작합니다. 이 작업이 완료되면 onload 콜백이 트리거됩니다.
						
					}
					
				}
				
			}); //ajax 끝
			
		}else{ //이미지 파일이 아닐 때
			alert("이미지를 넣어주세요!");
		
		}
		
		
	});

});



// change 이벤트가 발생할 때 선택된 파일이 이미지인지 검증
function isImageFile(file){
	var ext = file.name.split(".").pop().toLowerCase(); //파일명에서 확장자를 가져온다.
	
	//확장자 중 이미지에 해당하는 확장자가 아닌 경우 포함되어있는 문자가 없으니까 -1(false)가 리턴
	//확장자 중 이미지 확장자가 포함되어 있다면 0보다 큰 수 일테니 true가 리턴
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true;
}



</script>



</html>