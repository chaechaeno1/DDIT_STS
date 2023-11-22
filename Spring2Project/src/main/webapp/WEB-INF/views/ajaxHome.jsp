<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AjaxHome</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<body>

	<h3>AJAX HOME</h3>
	<hr/>
	<form action="" method="get">
		boardNo : <input type="text" name="boardNo" id="boardNo"/><br/>
		title : <input type="text" name="title" id="title"/><br/>
		content : <textarea rows="20" cols="50" name="content" id="content"></textarea><br/>
		writer : <input type="text" name="writer" id="writer"/><br/>
		<input type="button" id="btn" value="전송"/>		
	</form><br/>
	
	<div>
		<h3>Headers 매핑</h3>
		<button	id="putBtn" type="button">MODIFY(PUT)</button>
		<button id="putHeaderBtn" type="button">MODIFY(PUT With Header)</button>
		<br/>
		
		<h3>Content Type 매핑</h3>
		<button	id="postBtn" type="button">MODIFY(POST)</button>
		<button	id="putJsonBtn" type="button">MODIFY(PUT JSON)</button>
		<button id="putXMLBtn" type="button">MODIFY(PUT XML)</button>
		<br/>
		
		<h3>Accept 매핑</h3>
		<button	id="getBtn" type="button">READ</button>
		<button	id="getJsonBtn" type="button">READ(JSON)</button>
		<button id="getXmlBtn" type="button">READ(XML)</button>
		<br/>
		
		
	</div>
	

</body>

<script type="text/javascript">
$(function(){
	// Headers 매핑
	var putBtn = $("#putBtn");				//버튼 putBtn Element
	var putHeaderBtn = $("#putHeaderBtn");  //버튼 putHeaderBtn Element
	
	//Content Type 매핑
	var postBtn = $("#postBtn");				//버튼 postBtn Element
	var putJsonBtn = $("#putJsonBtn"); 		//버튼 putJsonBtn Element
	var putXMLBtn = $("#putXMLBtn");  		//버튼 putXMLBtn Element
	
	//Accept 매핑
	var getBtn = $("#getBtn");				//버튼 getBtn Element
	var getJsonBtn = $("#getJsonBtn"); 		//버튼 getJsonBtn Element
	var getXmlBtn = $("#getXmlBtn");  		//버튼 getXMLBtn Element
	
	// Headers 매핑 시작 -------------------------------------------------
	putBtn.on("click",function(){
		var boardNo = $("#boardNo").val();	//번호 데이터
		var title = $("#title").val();		//제목 데이터
		var content = $("#content").val();	//내용 데이터
		var writer = $("#writer").val();	//작성자 데이터
		
		var boardObject = { //받은 값을 객체로 묶기
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer				
		}
		
		//비동기 처리
		$.ajax({
			type : "put", //method 방식
			// put : 데이터를 "수정"할때? Header와 body라는 영역에서 꺼낼 수 있음 (post와 비슷한...)
			// post로 설정해서 진행해도 문제는 없음. 메소드 방식은 get, post, put, delete 방식이 있다는 것 알면됨
			// get 페이지 요청 post 데이터 가용
			url : "/board/" + boardNo, //서버로 보낼 요청 url
			data : JSON.stringify(boardObject),	//서버쪽으로 보낼 값이 존재하다면 data라는 속성에 특정 값을 설정
			contentType : "application/json; charset=utf-8",	//request안에 mime 타입 설정
			success: function(result){ //result=넘겨받은 결과값	//응답을 받는 콜백이 success
				console.log("result : " + result);
				// === : 강력한 equals
				// 값, 타입, hash까지 일치하는지를 검증
				if(result === "SUCCESS"){
					alert(result);
				}
				
			}
			
			
		}); //ajax 끝
		
		
		
		
	});
	
	
	putHeaderBtn.on("click",function(){
		var boardNo = $("#boardNo").val();	//번호 데이터
		var title = $("#title").val();		//제목 데이터
		var content = $("#content").val();	//내용 데이터
		var writer = $("#writer").val();	//작성자 데이터
		
		var boardObject = { //받은 값을 객체로 묶기(JSON 데이터로 만들기)
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer				
		}		
		
		
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			headers: {
				"X-HTTP-Method-Override" : "PUT"
			},
			contentType : "application/json; charset=utf-8",
			success: function(result){
				console.log("result : "+result);
				if(result === "SUCCESS"){
					alert(result);
				}
			}
			
		});
		
		
		
	});
	// Headers 매핑 끝 -------------------------------------------------
	
	
	// Content Type 매핑 시작 -------------------------------------------------
	
	postBtn.on("click", function(){
		var boardNo = $("#boardNo").val();	//번호 데이터
		var title = $("#title").val();		//제목 데이터
		var content = $("#content").val();	//내용 데이터
		var writer = $("#writer").val();	//작성자 데이터
		
		var boardObject = { //받은 값을 객체로 묶기(JSON 데이터로 만들기)
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer				
		}
		
		$.ajax({
			type : "post",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(result){
				console.log("result : "+ result);
				// '==' : Equals Operator
				// '===' : Strict Equals Operator
				// '==='는 값을 더 엄격하게 비교할 때 사용 			
				if(result === "SUCCESS"){
					alert(result);
				}
			}
			
			
		});
		
		
	});
	
	putJsonBtn.on("click", function(){
		var boardNo = $("#boardNo").val();	//번호 데이터
		var title = $("#title").val();		//제목 데이터
		var content = $("#content").val();	//내용 데이터
		var writer = $("#writer").val();	//작성자 데이터
		
		var boardObject = { //받은 값을 객체로 묶기(JSON 데이터로 만들기)
				boardNo : boardNo,
				title : title,
				content : content,
				writer : writer				
		}
		
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=utf-8",
			success : function(result){
				console.log("result : "+ result);
				// '==' : Equals Operator
				// '===' : Strict Equals Operator
				// '==='는 값을 더 엄격하게 비교할 때 사용 			
				if(result === "SUCCESS"){
					alert(result);
				}
			}
			
			
		});
		
		
		
		
		
	});
	
	putXMLBtn.on("click", function(){
		var boardNo = $("#boardNo").val();	//번호 데이터
		var title = $("#title").val();		//제목 데이터
		var content = $("#content").val();	//내용 데이터
		var writer = $("#writer").val();	//작성자 데이터
		
		var xmlData = "";
		xmlData += "<Board>";
		xmlData += "<boardNo>" + boardNo + "</boardNo>";	
		xmlData += "<title>" + title + "</title>";	
		xmlData += "<content>" + content + "</content>";	
		xmlData += "<writer>" + writer + "</writer>";	
		xmlData += "</Board>";
		
		
		$.ajax({
			type : "put",
			url : "/board/" + boardNo,
			data : xmlData,
			contentType : "application/xml; charset=utf-8",
			success : function(result){
				console.log("result : "+ result);
				// '==' : Equals Operator
				// '===' : Strict Equals Operator
				// '==='는 값을 더 엄격하게 비교할 때 사용 			
				if(result === "SUCCESS"){
					alert(result);
				}
			}
			
			
		});
		
		
		
		
	});
	
	
	
	// Content Type 매핑 끝 -------------------------------------------------
	
	
	// Accept 매핑 시작 -------------------------------------------------
	getBtn.on("click",function(){
		var boardNo = $("#boardNo").val();
		
		//GET 방식 비동기 HTTP 요청 수행
		$.get("/board/" + boardNo, function(data){
			console.log(data);
			alert(JSON.stringify(data));
			
		});
		
		
	});
	
	getJsonBtn.on("click",function(){
		var boardNo = $("#boardNo").val();
		
		$.ajax({
			type: "get",
			url: "/board/"+boardNo,
			headers:{
				"Accept" : "application/json"
			},
			success : function(result){
				console.log(result);
				alert(JSON.stringify(result));
			}
			
		});
		
		
	});
	
	getXmlBtn.on("click",function(){
		var boardNo = $("#boardNo").val();
				
				$.ajax({
					type: "get",
					url: "/board/"+boardNo,
					headers:{
						"Accept" : "application/xml"
					},
					success : function(result){
						console.log(result);
						alert(xmlToString(result));
					}
					
				});
		
	});
	
	
	
	// Accept 매핑 끝 -------------------------------------------------
	
	
	
function xmlToString(xmlData){
	
		var xmlString;
		
		//window.ActiveXObject는 ActiveObject를 지원하는 브라우저면
		//Object를 리턴하고 그렇지 않다면 null을 리턴
		if(window.ActiveXObject){
			xmlString = xmlData.xml;
		}else{
			xmlString = (new XMLSerializer()).serializeToString(xmlData);
		}
		return xmlString;
	}	
	
	
	
	
})

</script>


</html>