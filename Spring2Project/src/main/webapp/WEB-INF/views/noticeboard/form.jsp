<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>   

<section class="content-header">
	<c:set value="등록" var="name"/>
	<c:if test="${status eq 'u'}">
		<c:set value="수정" var="name"/>
	</c:if>

	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>공지사항 등록/수정</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
					<li class="breadcrumb-item active">공지사항 등록/수정</li>
				</ol>
			</div>
		</div>
	</div>
</section>


<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="card card-dark">
				<div class="card-header">
					<h3 class="card-title">공지사항 ${name }</h3>
					<div class="card-tools"></div>
				</div>
				
				<form action="/notice/insert.do" method="post" id="noticeForm" enctype="multipart/form-data">
					<c:if test="${status eq 'u' }">
						<input type="hidden" name="boNo" value="${notice.boNo }"/> <!-- 수정모드일 때 boNo 필요 -->
					</c:if>
					<div class="card-body">
						<div class="form-group">
							<label for="boTitle">제목을 입력해주세요</label> <input type="text"
								id="boTitle" name="boTitle" class="form-control" placeholder="제목을 입력해주세요" value="${notice.boTitle }">
						</div>
						<div class="form-group">
							<label for="boContent">내용을 입력해주세요</label>
							<textarea id="boContent" name="boContent" class="form-control" rows="14">${notice.boContent }</textarea>
						</div>
						
						<div class="form-group">
							<div class="custom-file">
	
								<input type="file" class="custom-file-input" id="boFile" name="boFile"multiple="multiple"> 
								<label class="custom-file-label" for="boFile">파일을 선택해주세요</label>
							</div>
							
						</div> 
						
					</div>
					<sec:csrfInput/>
				</form>
				
				<!-- 수정일때만 나타나게 할 것 -->
				<c:if test="${status eq 'u' }">
				
					<!-- li 처음에 4개였는데 1개 남기고 모두 삭제  -->
					
					<div class="card-footer bg-white">
						<ul class="mailbox-attachments d-flex align-items-stretch clearfix">
						
						<c:if test="${not empty notice.noticeFileList }">
						
							<c:forEach items="${notice.noticeFileList }" var="noticeFile">
							
								<li><span class="mailbox-attachment-icon">
									<i class="far fa-file-pdf"></i></span>
		
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name">
											<i class="fas fa-paperclip"></i> ${noticeFile.fileName }
										</a> 
										<span class="mailbox-attachment-size clearfix mt-1"> 
										<span>${noticeFile.fileFancySize }</span> 
										<span class="btn btn-default btn-sm float-right attachmentFileDel" id="span_${noticeFile.fileNo }">
											<i class="fas fa-times"></i>
										</span>
										</span>
									</div>
								</li>
							
							</c:forEach>
						
						
						</c:if>
						
						</ul>
					</div>
				</c:if>
				
				
				
				<div class="card-footer bg-white">
					<div class="row">
						<div class="col-12">
							<input type="button" id="insertBtn" value="${name }" class="btn btn-secondary float-right">
								<c:if test="${status ne'u' }">
									<input type="button" id="listBtn" value="목록" class="btn btn-secondary float-right"> 
								</c:if>
								<c:if test="${status eq'u' }">
									<input type="button" id="cancelBtn" value="취소" class="btn btn-dark float-right">
								</c:if>
							
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</section>







<script type="text/javascript">
$(function(){
	
	
	//이 줄은 ID가 "boContent"인 HTML 요소를 CKEditor의 인스턴스로 교체합니다.
	//"boContent"라는 특정 textarea 또는 input 요소를 대상으로 하고 있어, 이를 CKEditor 인스턴스로 변환합니다.
	CKEDITOR.replace("boContent", {
		//이 설정 옵션은 CKEditor의 파일 업로드 기능에 대한 URL을 설정
		filebrowserUploadUrl: '/imageUpload.do?${_csrf.parameterName}=${_csrf.token}'	 /* 업로드 했을 당시의 파일이 경로 쪽으로 넘어감 */
	});
	CKEDITOR.config.height = "500px"; //CKEDITOR 높이 설정
	
	
	
	
	var noticeForm = $("#noticeForm"); //등록 폼 element
	var insertBtn = $("#insertBtn"); // 등록 버튼 element
	var listBtn = $("#listBtn");	//목록 버튼 element
	var cancelBtn = $("#cancelBtn");	//취소 버튼 element
	
	//등록 버튼 클릭 시, 등록 진행
	insertBtn.on("click",function(){
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
			noticeForm.attr("action","/notice/update.do");
		}
		
		noticeForm.submit(); //전송
		
	});
	
	// 목록 버튼 클릭 시, 게시판 목록 화면으로 이동
	listBtn.on("click",function(){
		location.href="/notice/list.do";
		
	});
	
	//취소버튼 클릭시, 상세보기 화면으로 이동
	cancelBtn.on("click",function(){
		location.href="/notice/detail.do?boNo=${notice.boNo}";
		
	});
	
	
	
	$(".attachmentFileDel").on("click",function(){
		var id = $(this).prop("id");
		var idx = id.indexOf("_");
		var noticeFileNo = id.substring(idx+1);
		var ptrn = "<input type='hidden' name='delNoticeNo' value='%V'/>";
		$("#noticeForm").append(ptrn.replace("%V", noticeFileNo));
		$(this).parents("li:first").hide();
	});
	
	
	
	
	
});

</script>




