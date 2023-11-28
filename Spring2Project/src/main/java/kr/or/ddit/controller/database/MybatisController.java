package kr.or.ddit.controller.database;

public class MybatisController {
	
	
	/*
	 * 	[12장. 마이바티스]
	 * 	
	 * 	1. 마이바티스 란?
	 * 		- 마이바티스는 자바 퍼시스턴스 프레임워크의 하나로 XML 서술자나 어노테이션을 사용하여 저장프로시저나 SQL문으로 객체들을 연결시킨다.
	 * 		- 마이바티스는 Apache 라이선스 2.0으로 배포되는 자유 소프트웨어 입니다.
	 * 
	 * 		# 마이바티스를 사용함으로써 얻을 수 있는 이점
	 * 		- SQL의 체계적인 관리
	 * 		- 자바 객체와 SQL 입출력 값의 투명한 바인딩
	 * 		- 동적 SQL 조합
	 * 
	 * 		# 마이바티스 설정
	 * 			1) 의존관계 정의
	 * 				- 총 6가지 라이브러리를 등록하여 관계를 정의합니다. (DatabaseConnectController 참고)
	 * 			2) 스프링과 마이바티스 연결 설정
	 * 				- root-context.xml 설정(DatabaseConnectcController 참고)
	 * 				- 총 3가지를 등록하여 설정 (추가로 Mapper를 등록하기 위한 basePackage 정보도 함께 추가할 예정)
	 * 				
	 * 			3) 마이바티스 설정
	 * 				- WEB-INF/mybatisAlias/mybatisAlias.xml 설정
	 * 				- 마이바티스의 위치 설정은 root-context.xml의 'sqlSessionFactory' 설정 시, property 요소로 적용
	 * 
	 * 		# 관련 테이블 생성
	 * 			- board 테이블 생성 (1)
	 * 			- member 테이블 생성 (2)
	 * 			- member_auth 테이블 생성 (2)
	 * 	
	 * 
	 * 
	 * 	2. Mapper 인터페이스 (DAO없는 대신 사용할 인터페이스)
	 * 		- 인터페이스의 구현을 mybatis-spring에서 자동으로 생성할 수 잇다.
	 * 
	 *  	1) 마이바티스 구현
	 *  
	 *  		1-1) Mapper 인터페이스
	 *  		- BoardMapper.java 생성(인터페이스)
	 *  		
	 *  		1-2) Mapper 인터페이스와 매핑할 Mapper
	 *  		- sqlmap/boardMapper_SQL.xml 생성
	 *  
	 *  		1-3) 게시판 구현 설명
	 *  
	 *  		- 게시판 컨트롤러 만들기 (crud/CrudBoardController)
	 *  		- 게시판 등록 화면 컨트롤러 메소드 만들기(crudRegister:get)
	 *  		- 게시판 등록 화면 만들기 (crud/register.jsp)
	 *  		- 여기까지 확인
	 *  
	 *  		- 게시판 등록 기능 컨트롤러 메소드 만들기 (crudRegister:post)
	 *  		- 게시판 등록 기능 서비스 인터페이스 메소드 만들기
	 *  		- 게시판 등록 기능 서비스 클래스 메소드 만들기
	 *  		- 게시판 등록 기능 Mapper 인터페이스 메소드 만들기
	 *  		- 게시판 등록 기능 Mapper xml 쿼리 만들기
	 *  		- 게시판 등록 완료 페이지 만들기
	 *  		- 여기까지 확인
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 *  
	 * 
	 * 
	 * 
	 */

}
