<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
    

    			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1>공지사항 게시판</h1>
						</div>
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
								<li class="breadcrumb-item active">공지사항 게시판</li>
							</ol>
						</div>
					</div>
				</div>
	
			</section>



			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
							<div class="card card-dark card-outline">
								<div class="card-header">
									<div class="card-tools">
										<div class="input-group input-group-sm" style="width: 440px;">
											<select class="form-control">
												<option>제목</option>
												<option>작성자</option>
											</select> <input type="text" name="table_search"
												class="form-control float-right" placeholder="Search">
											<div class="input-group-append">
												<button type="submit" class="btn btn-default">
													<i class="fas fa-search"></i>검색
												</button>
											</div>
										</div>
									</div>
									<h3 class="card-title">공지사항</h3>
								</div>
								<!-- /.card-header -->
								<div class="card-body">
									<table class="table table-bordered">
										<thead class="table-dark">
											<tr>
												<th style="width: 6%">#</th>
												<th style="width: px">제목</th>
												<th style="width: 12%">작성자</th>
												<th style="width: 12%">작성일</th>
												<th style="width: 10%">조회수</th>
											</tr>
										</thead>
										<tbody>
											<c:set value="${pagingVO.dataList }" var="noticeList"/>
											<c:choose>
												<c:when test="${empty noticeList }">
													<tr>
														<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
													</tr>
												
												</c:when>
												<c:otherwise>
													
													<c:forEach items="${noticeList }" var="notice">
														<tr>
															<td>${notice.boNo }</td>
															<td>${notice.boTitle }</td>
															<td><font class="badge badge-danger"
															style="font-size: 14px;">${notice.boWriter }</font>
															</td>
															<td>${notice.boDate }</td>
															<td>${notice.boHit }</td>
														</tr>		
													</c:forEach>
													
												</c:otherwise>
																					
											</c:choose>


										</tbody>
									</table>
								</div>
								<div class="card-footer" align="right">
									<button type="submit" class="btn btn-dark">등록</button>
								</div>
	
								<div class="card-footer clearfix">
									<ul class="pagination pagination-md m-0 float-right">
										<li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
										<li class="page-item"><a class="page-link" href="#">1</a></li>
										<li class="page-item"><a class="page-link" href="#">2</a></li>
										<li class="page-item"><a class="page-link" href="#">3</a></li>
										<li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
	

				</div>
	
			</section>