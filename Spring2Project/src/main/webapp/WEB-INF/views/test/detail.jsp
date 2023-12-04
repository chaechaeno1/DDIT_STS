<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>

    		<section class="py-1 text-center container">
			<div class="row py-lg-4">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light">DDIT 상세보기</h1>
				</div>
			</div>
		</section>
		<section class="py-1 container">
			<div class="row">
				<div class="col-md-12">
					<div class="card">
						<div class="card-header">${tagBoardVO.boTitle }</div>
						<div class="card-body">${tagBoardVO.boWriter }  ${tagBoardVO.boDate }  ${tagBoardVO.boHit }</div>
						
						<form id="quickForm" novalidate="novalidate">
							<div class="card-body">${tagBoardVO.boContent }</div>
							<div class="card-body">
								<span class="badge bg-success">태그들</span>
<%-- 									 <c:forEach var="tag" items="${tagBoardVO.tagList}">
							            #${tag.tagName} <!-- 태그 출력 -->
							        </c:forEach> --%>
							</div>
							
							<div class="card-footer">
								<button type="button" class="btn btn-warning" id="modifyBtn">수정</button>
								<button type="button" class="btn btn-danger" id="delBtn">삭제</button>
								<button type="button" class="btn btn-info" id="listBtn">목록</button>
							</div>
							
						</form>	
					</div>
				</div>
				
			<form action="/board/tag/delete.do" method="post" id="delForm">
				<input type="hidden" name="boNo" value="${tagBoardVO.boNo }"/>
			</form>
				
			</div>
		</section>
		
		
		
		
		
<script type="text/javascript">
$(function(){
	var listBtn = $("#listBtn");	//목록 버튼 Element
	var modifyBtn = $("#modifyBtn");	//수정 버튼 Element
	var delBtn = $("#delBtn");	//삭제 버튼 Element
	var delForm = $("#delForm"); //수정 및 삭제를 처리할 Form Element
	
	// 목록 버튼 클릭 시, 목록 페이지 이동
	listBtn.on("click",function(){
		location.href="/board/tag/list.do";
		
	});
	
	// 수정 버튼 클릭 시, 수정 페이지 이동
	modifyBtn.on("click",function(){
		delForm.attr("action","/board/tag/update.do");
		delForm.attr("method","get");
		delForm.submit();
		
	});
	
	// 삭제 버튼 클릭 시, confirm 메시지 출력 후 삭제
	delBtn.on("click",function(){
		if(confirm("정말로 삭제하시겠습니까?")){
			delForm.submit();
		}
		
	});
	
});


</script>		
		
		
		
		