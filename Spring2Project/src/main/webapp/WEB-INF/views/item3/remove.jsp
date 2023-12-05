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


	<h2>REMOVE</h2>
	<form id="item" action="/item3/remove" method="post" enctype="multipart/form-data">
		<input type="hidden" name="itemId" value="${item.itemId }"/>
		<table border="1">
			<tr>
				<td>상품명</td>
				<td><input type="text" name="itemName" id="itemName" value="${item.itemName }" disabled="disabled"/></td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price" id="price" value="${item.price }" disabled="disabled"/></td>
			</tr>
			<tr>
				<td>파일</td>
				<td>
					<div class="uploadedList"></div>
				</td>
			</tr>
			<tr>
				<td>개요</td>
				<td>
					<textarea rows="10" cols="30" name="description" disabled="disabled">${item.description}</textarea>
				</td>
			</tr>
		</table>
		<div>
			<button type="submit" id="removeBtn">Remove</button>
			<button type="button" id="listBtn">List</button>
		</div>
		
	</form>



</body>


<script type="text/javascript">
$(function(){
		
	var listBtn = $("#listBtn");		// list 버튼 Element
	
	
	// 목록 버튼 클릭 시, 목록 화면으로 이동
	listBtn.on("click",function(){		
		location.href="/item3/list";
		
	});
	
	
	
	/* modify에서 추가 */
	
	var itemId = "${item.itemId}";
	console.log("itemId : "+ itemId);
	
	// 이미지 미리보기 기능 살려두기!!!
	
	//수정을 하러 들어왔을 때 기존에 업로드했던 파일들의 정보를 가지고 uploadedList안에 내용을 출력한다.
	//이 코드는 JavaScript 및 jQuery를 사용하여 AJAX(비동기 JavaScript 및 XML) 요청을 수행하고 
	// 특정 아이템 ID(itemId)에 관련된 항목 목록을 가져오는 작업을 수행합니다. 
	// 그런 다음 코드는 수신된 목록을 처리하고 각 항목의 유형에 따라 HTML 내용을 업데이트합니다.
	
	//이 줄은 AJAX GET 요청을 사용하여 /item3/getAttach/ 엔드포인트로 서버에 아이템 ID를 추가하여 요청합니다.
	//서버는 JSON 데이터로 응답해야하며(@ResponseBody), 그런 다음 콜백 함수에서 이 데이터를 처리합니다.
	$.getJSON("/item3/getAttach/" + itemId, function(list){
		//each 함수는 수신된 list의 각 항목을 반복하는 데 사용
		$(list).each(function(){
			console.log("processing...!");
			
			//루프 내에서 현재 항목은 data 변수에 할당됩니다.
			var data = this;
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
			// 생성된 HTML(str)을 uploadedList 클래스를 가진 요소에 추가합니다. 
			// 이것이 웹페이지에 콘텐츠가 표시되는 위치입니다.
			$(".uploadedList").append(str);
		});
		
	}); //getJSON 끝
	
	
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