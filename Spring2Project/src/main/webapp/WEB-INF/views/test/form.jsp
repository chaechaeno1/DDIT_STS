<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 		<section class="py-1 text-center container">
 		
 		<c:set value="등록" var="name"/>
		<c:if test="${status eq 'u'}">
			<c:set value="수정" var="name"/>
		</c:if>
 		
			<div class="row py-lg-4">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light">DDIT ${name }</h1>
				</div>
			</div>
		</section>
		<section class="py-1 text-center container">
			<form class="album py-1 bg-light" action="/board/tag/insert.do" method="post" id="dditboard">
				<c:if test="${status eq 'u' }">
					<input type="hidden" name="boNo" value="${tagBoardVO.boNo }"/> <!-- 수정모드일 때 boNo 필요 -->
				</c:if>
				<div class="">
					<div class="container">
						<div class="card-body">
							<div class="input-group input-group-lg">
								<span class="input-group-text" id="inputGroup-sizing-lg">제목</span>
								<input type="text" id="boTitle" class="form-control" name="boTitle" value="${tagBoardVO.boTitle }"/>
							</div>
							<div class="input-group input-group-lg">
								<span class="input-group-text" id="inputGroup-sizing-lg">내용</span>
								<textarea class="form-control" aria-label="With textarea" rows="12" name="boContent" id="boContent">${tagBoardVO.boContent}</textarea>
							</div>
							<div class="input-group input-group-lg">
								<span class="input-group-text" id="inputGroup-sizing-lg">태그</span>
								<input type="text" class="form-control" name="tag" value="${tagBoardVO.tag}"/> <!-- name="tag"가 아니면 오류남-->
							</div>
						</div>
						<div class="card-footer" align="right">
							<input type="button" id="registerBtn" value="${name }" class="btn btn-secondary float-right">
								<c:if test="${status ne'u' }">
									<input type="button" id="listBtn" value="목록" class="btn btn-secondary float-right"> 
								</c:if>							
								<c:if test="${status eq'u' }">
									<input type="button" id="cancelBtn" value="취소" class="btn btn-dark float-right">
								</c:if>
						</div>
					</div>
				</div>
			</form>
		</section>   
		
		
		
<script type="text/javascript">
$(function(){

	CKEDITOR.replace("boContent");

	var dditboard = $("#dditboard"); //등록 폼 element
	var registerBtn = $("#registerBtn"); // 등록 버튼 element
	var listBtn = $("#listBtn");	//목록 버튼 element
	var cancelBtn = $("#cancelBtn");	//취소 버튼 element
	
	//등록 버튼 클릭 시, 등록 진행
	registerBtn.on("click",function(){
		var title = $("#boTitle").val();
		//일반적인 textarea 일때 가져오는 방법
		//var content = $("#boContent").val();	//내용 값
		
		//CKEDITOR를 이용한 내용 데이터 가져오는 방법
		var content = CKEDITOR.instances.boContent.getData();	//내용 값
		
		if(title == null || title == ""){
			alert("제목을 입력해주세요!");
			$("#boTitle").focus();
			return false;
		}
		
		if(content == null || content == ""){
			alert("내용을 입력해주세요!");
			$("#boContent").focus();
			return false;
		}
		
		if($(this).val() == "수정"){
			dditboard.attr("action","/board/tag/update.do");
		}
		
		dditboard.submit(); //전송
		
	});
	
	// 목록 버튼 클릭 시, 게시판 목록 화면으로 이동
	listBtn.on("click",function(){
		location.href="/board/tag/list.do";
		
	});
	
	//취소버튼 클릭시, 상세보기 화면으로 이동
	cancelBtn.on("click",function(){
		location.href="/board/tag/detail.do?boNo=${tagBoardVO.boNo}";
		
	});
	
	
	
	
	
});

</script>		
		
		
		
		