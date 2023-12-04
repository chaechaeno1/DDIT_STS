<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

		<section class="py-1 text-center container">
			<div class="row py-lg-4">
				<div class="col-lg-6 col-md-8 mx-auto">
					<h1 class="fw-light">DDIT 목록</h1>
				</div>
			</div>
		</section>
		<section class="py-1 container">
			<div class="row">
				<div class="col-md-12">
					
					
					<form class="row g-3 mb-3" method="post" id="searchForm">
						<input type="hidden" name="page" id="page"/>
						<div class="row">
							<div class="col-md-6"></div>
							<div class="col-md-6" align="right">
								<div class="row">
									<div class="col-md-4">		
										<!-- 게시물 검색 -->
										<select class="form-select" name="searchType">
											<option value="title" <c:if test="${searchType eq 'title' }">selected</c:if>>제목</option>
											<option value="writer" <c:if test="${searchType eq 'writer' }">selected</c:if>>작성자</option>
											<option value="both" <c:if test="${searchType eq 'both' }">selected</c:if>>제목+작성자</option>
										</select>
									</div>
									<div class="col-md-5">
										<label for="inputPassword2" class="visually-hidden">키워드</label>
										<input type="text" class="form-control" id="inputPassword2" name="searchWord" placeholder="검색 키워드" value="${searchWord}">
									</div>
									<div class="col-md-3">
										<button type="submit" class="btn btn-dark">검색하기</button>
									</div>
								</div>
							</div>
						</div>
					</form>
					
					
					
					<!-- 게시물 목록 출력 -->
					<table class="table">
						<thead class="table-dark">
							<tr>
								<th scope="col" width="8%">번호</th>
								<th scope="col">제목</th>
								<th scope="col" width="14%">작성자</th>
								<th scope="col" width="16%">작성일</th>
								<th scope="col" width="8%">조회수</th>
							</tr>
						</thead>
						<tbody>
						<c:set value="${pagingVO.dataList }" var="tagBoardList"/>
						<c:choose>
							<c:when test="${empty tagBoardList }">
								<tr>
									<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
								</tr>
							
							</c:when>
							<c:otherwise>
								<c:forEach items="${tagBoardList }" var="tagBoard">
									<tr>
										<td>${tagBoard.boNo }</td>
										<td>
											<a href="/board/tag/detail.do?boNo=${tagBoard.boNo}">${tagBoard.boTitle }</a> 
										</td>
										<td>${tagBoard.boWriter }</td>
										<td>${tagBoard.boDate }</td>
										<td>${tagBoard.boHit }</td>
									</tr>
									
								</c:forEach>
							
							</c:otherwise>
						
						</c:choose>
						
						</tbody>
					</table>
					
					<div class="card-footer" align="right">
						<button type="button" class="btn btn-primary" id="registerBtn">등록</button>
					</div>
					<div class="card-footer clearfix d-flex justify-content-center" id="pagingArea">			
							${pagingVO.pagingHTML }
					</div>
									
				</div>

			</div>
		</section>    
		
		


<script>
$(function(){
	//페이징을 처리할 때 사용할 Element
	//pagingArea div안에 ul과 li로 구성된 페이징 정보가 존재
	//그 안에는 a태그로 구성된 페이지 정보가 들어있음
	//a태그 안에 들어있는 page번호를 가져와서 페이징처리를 진행
	var pagingArea = $("#pagingArea");
	var searchForm = $("#searchForm");
	var registerBtn = $("#registerBtn");

	pagingArea.on("click","a",function(event){
		
		event.preventDefault(); //a태그의 이벤트를 block
		var pageNo = $(this).data("page");
		searchForm.find("#page").val(pageNo);
		searchForm.submit();
		
	});
	
	//등록 버튼 클릭 시, 게시판 등록 페이지로 이동
	registerBtn.on("click",function(){
		location.href = "/board/tag/form.do";
		
	});

});
</script>					
		
		