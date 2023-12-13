<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<section class="content-header">
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>공지사항 상세보기</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
					<li class="breadcrumb-item active">공지사항 상세보기</li>
				</ol>
			</div>
		</div>
	</div>
</section>



<section class="content">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card card-dark">
					<div class="card-header">
						<h3 class="card-title">${notice.boTitle }</h3>
						<div class="card-tools">${notice.boWriter } ${notice.boDate } ${notice.boHit }</div>
					</div>
					<form id="quickForm" novalidate="novalidate">
						<div class="card-body">${notice.boContent }</div>
						<div class="card-footer bg-white">
						
						
						
						
							<ul class="mailbox-attachments d-flex align-items-stretch clearfix">
							
							<c:if test="${not empty notice.noticeFileList }">
							
								<c:forEach items="${notice.noticeFileList }" var="noticeFile">
								
									<li>
										<span class="mailbox-attachment-icon"> 
											<i class="far fa-file-pdf"></i>
										</span> 
											<div class="mailbox-attachment-info">
												<a href="#" class="mailbox-attachment-name"> 
													<i class="fas fa-paperclip"></i> ${noticeFile.fileName }
												</a> 
												<span class="mailbox-attachment-size clearfix mt-1"> 
												<span>${noticeFile.fileFancySize }</span> 
												
												<c:url value="/notice/download.do" var="downloadURL">
													<c:param name="fileNo" value="${noticeFile.fileNo }"/>
												</c:url>
												
												<a href="${downloadURL }"> 
												
												<span
														class="btn btn-default btn-sm float-right"> <i
															class="fas fa-download"></i>
													</span>
												</a>
												</span>
										</div>
									</li>
								
								</c:forEach>
							

							</c:if>
							</ul>
							
							
							
							
							
							
						</div>
						<div class="card-footer">			
							
									
							<button type="button" class="btn btn-secondary" id="listBtn">목록</button>
							
							<sec:authentication property="principal.member" var="member"/>
							<sec:authorize access="hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')">
								<!-- 관리자이거나, 글 작성자가 본인일 때 수정/삭제 버튼 보여주기 -->
								<c:if test="${member.memId eq notice.boWriter or member.memId eq 'admin'}">
									<button type="button" class="btn btn-dark" id="updateBtn">수정</button>
									<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>								
								</c:if>
							</sec:authorize>
						</div>
					</form>
					
					
					
				</div>
			</div>
			
			
			<form action="/notice/delete.do" method="post" id="delForm">
				<input type="hidden" name="boNo" value="${notice.boNo }"/>
				<sec:csrfInput/>
			</form>
			
			
			<div class="col-md-6"></div>
		</div>
	</div>
</section>








<script type="text/javascript">
$(function(){
	var listBtn = $("#listBtn");	//목록 버튼 Element
	var updateBtn = $("#updateBtn");	//수정 버튼 Element
	var deleteBtn = $("#deleteBtn");	//삭제 버튼 Element
	var delForm = $("#delForm"); //수정 및 삭제를 처리할 Form Element
	
	// 목록 버튼 클릭 시, 목록 페이지 이동
	listBtn.on("click",function(){
		location.href="/notice/list.do";
		
	});
	
	// 수정 버튼 클릭 시, 수정 페이지 이동
	updateBtn.on("click",function(){
		delForm.attr("action","/notice/update.do");
		delForm.attr("method","get");
		delForm.submit();
		
	});
	
	// 삭제 버튼 클릭 시, confirm 메시지 출력 후 삭제
	deleteBtn.on("click",function(){
		if(confirm("정말로 삭제하시겠습니까?")){
			delForm.submit();
		}
		
	});
	
	
	
	
	
	
	
	
	
	
});


</script>



