<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ITEM3</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


<body>


	<h2>REGISTER</h2>
	<form id="item" action="/item3/register" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>상품명</td>
				<td><input type="text" name="itemName" id="itemName"/></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price" id="price"/></td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<input type="file" id="inputFile"/>
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td>
					<textarea rows="10" cols="30" name="description"></textarea>
				</td>
			</tr>
		</table>
		<div>
			<button type="submit" id="registerBtn">Register</button>
			<button type="button" id="listBtn">List</button>
		</div>
		
	</form>



</body>


<script type="text/javascript">
$(function(){
	
	var item = $("#item");				// 폼 태그 Element
	var inputFile = $("#inputFile");	// input file Element
	var listBtn = $("#listBtn");		// list 버튼 Element
	
	
	// 목록 버튼 클릭 시, 목록 화면으로 이동
	listBtn.on("click",function(){		
		location.href="/item3/list";
		
	});
	
	// Open 파일 변경했을 때 이벤트 발동
	inputFile.on("change", function(event){
		
		console.log("change...!");
		
		var files = event.target.files;
		var file = files[0];
		
		console.log(file);
		var formData = new FormData();
		formData.append("file", file);
		
		//서버로 데이터 전송
		$.ajax({ 
			type : "post",
			url : "/item3/uploadAjax",
			data : formData,
			processData : false,
			contentType : false, 
			success : function(data){
				console.log(data); // 서버에서 받은 결과를 콘솔에 출력 
				
				var str = ""; 
				
				if(checkImageType(data)){ //이미지면 이미지 태그를 이용하여 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>";
					str += "		<img src='/item3/displayFile?fileName=" + getThumnailName(data) + "'/>";
					str += "	</a>";
					str += "	<span>X</span>";
					str += "</div>";
					
				}else{	// 파일이면 파일명에 대한 링크로만 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>" + getOriginalName(data) + "</a>";
					str += "	<span>X</span>";
					str += "</div>";
					
				}
				
				// uploadedList class안에 추가
				$(".uploadedList").append(str);
				
			}
			
		});
		
		
	});
	
	//업로드한 파일이 추가된 div 영역 안에 'X'를 눌러 삭제하면 div 그룹이 통째로 날라간다.
	//uploadList 클래스 안에 있는 span 태그 안에있는 X라는 버튼을 누르면 삭제되는 것 
	$(".uploadedList").on("click","span", function(){
		$(this).parent("div").remove();
		
	});
	
	
	item.submit(function(event){
		// form 태그의 submit 이벤트를 우선 block
		event.preventDefault();
		
		var that = $(this); // 현재 발생한 form 태그
		var str = "";
		$(".uploadedList a").each(function(index){
			var value = $(this).attr("href");
			value = value.substr(28);	//?fileName= 다음에 나오는 값
					
			str += "<input type='hidden' name='files["+ index +"]' value='"+value+"'>";
			
		});
		
		console.log("str " +str);
		that.append(str);
		// form의 첫번째를 가져와서 submit() 처리
		// get() 함수는 여러개 중에 1개를 찾을 때 (form태그가 1개이긴 하지만 여러개 중에 1개를 찾을 때도 활용함)
		that.get(0).submit();
		
		
	});
	
	
	
	//임시 파일로 썸네일 이미지 만들기
	//업로드된 파일명을 받아와서 날짜 폴더 경로와 파일명 부분을 분리한 뒤, 그 사이에 's_'를 넣어서 썸네일 파일명을 생성
	function getThumnailName(fileName){
		// 파일명에서 앞부분(날짜 폴더 경로)과 뒷부분(파일명)을 분리
		var front = fileName.substr(0,12); // 2023/12/04 폴더 경로
		var end = fileName.substr(12); // 뒤 파일명, 예: UUID_원본파일명
		
		console.log("front : " + front);
		console.log("end : " + end);
		
		// 썸네일 파일명 생성하여 반환 (예: 2023/12/04s_UUID_원본파일명)
		return front + "s_" + end;
	}
	
	// 파일명 추출(원본 파일명)
	function getOriginalName(fileName){
		// 이미지 파일이 아니면 함수 종료
		if(checkImageType(fileName)){ // 이미지 파일일 때 리턴
			return;
		}
		
		// 언더스코어(_) 이후의 부분을 추출하여 원본 파일명 반환
		var idx = fileName.indexOf("_") + 1;
		return fileName.substr(idx);
		
	}
	
	
	// 이미지 파일인지 검증
	function checkImageType(fileName){
		// 정규 표현식을 사용하여 확장자가 jpg, gif, png, jpeg 중 하나와 일치하는지 확인
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern); //패턴과 일치하면 true(너 이미지가 맞구나?)
	}
	
	
});

</script>






</html>