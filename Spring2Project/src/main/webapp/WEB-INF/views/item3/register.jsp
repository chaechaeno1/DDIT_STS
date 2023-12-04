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
					<div class="uploadList"></div>
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
		
		$.ajax({ //서버로 데이터 전송
			type : "post",
			url : "/item3/uploadAjax",
			data : formData,
			processData : false,
			contentType : false, 
			success : function(data){
				console.log(data); // 넘어온 결과를 테스트로 출력
				
				var str = ""; 
				
				if(checkImageType(data)){ //이미지면 이미지 태그를 이용하여 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>";
					str += "		<img src='/item3/displayFile?fileName=" + getThumnailName(data) + "'/>";
					str += "	</a>";
					str += "	<span></span>";
					str += "</div>";
					
				}else{	// 파일이면 파일명에 대한 링크로만 출력
					str += "<div>";
					str += "	<a href='/item3/displayFile?fileName=" + data + "'>" + getOriginalName(data) + "</a>";
					str += "	<span></span>";
					str += "</div>";
					
				}
				
				// uploadedList class안에 추가
				$("uploadedList.").append(str);
				
			}
			
		});
		
		
	});
	
	//임시 파일로 썸네일 이미지 만들기
	function getThumnailName(fileName){
		var front = fileName.substr(0,12); // 2023/12/04 폴더 경로
		var end = fileName.substr(12); // 뒤 파일명
		
		console.log("front : " + front);
		console.log("end : " + end);
		
		return front + "s_" + end;
	}
	
	// 파일명 추출(원본 파일명)
	function getOriginalName(fileName){
		if(checkImageType(fileName)){ // 이미지 파일일 때 리턴
			return;
		}
		
		var idx = fileName.indexOf("_") + 1;
		return fileName.substr(idx);
		
	}
	
	
	// 이미지 파일인지 검증
	function checkImageType(fileName){
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern); //패턴과 일치하면 true(너 이미지가 맞구나?)
	}
	
	
});

</script>






</html>