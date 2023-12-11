package kr.or.ddit.controller;

public class SecurityController {
	/*
	 * [18장 : 스프링 시큐리티]
	 * 
	 * 	1. 스프링 시큐리티 소개
	 * 		
	 * 		- 애플리케이션에서 보안 기능을 구현하는데 사용되는 프레임 워크이다.
	 * 		- 스프링 시큐리티는 필터 기반으로 동작하기 때문에 스프링 MVC와 분리되어 동작한다.
	 * 
	 * 		# 기본 보안 기능
	 * 		- 인증(Authentication)
	 * 			> 애플리케이션 사용자의 정당성을 확인한다.
	 * 		- 인가 (Authorization)
	 * 			> 애플리케이션의 리소스나 처리에 대한 접근을 제어한다.
	 * 
	 * 		# 시큐리티 제공 기능
	 * 		- 세션 관리
	 * 		- 로그인 처리
	 * 		- CSRF 토큰 처리
	 * 		- 암호화 처리
	 * 		- 자동 로그인
	 * 
	 * 		**** CSRF 용어 설명
	 * 
	 *		- 크로스 사이트 요청 위조는 웹 사이트 취약점 공격의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가
	 *		의도한 행위(수정, 삭제, 등록 등)를 특정 웹 사이트에 요청하게 하는 공격을 말한다.
	 *
	 *			> CSRF 공격을 대비하기 위해서는 스프링 시큐리티 CSRF Token을 이용하여 인증을 진행한다.
	 *
	 *
	 *		# 시큐리티 인증 구조
	 *
	 *			클라이언트에서 타겟으로 들어가기 위해서 요청을 진행합니다.
	 *			이때 타켓에 설정되어 있는 요청 접근 권한이 '사용자'등급일 때로 설정되어 있다고 가정하자.
	 *			타겟으로 접근하기 위한 요청을 날렸고 요청안에 사용자 등급에 해당하는 인가 정보가 포함되어 있지 않으면 
	 *			스프링 시큐리티는 인증을 진행할 수 있도록 인증 페이지(로그인 페이지)를 제공하여 사용자에게 인증 요청한다.
	 *			사용자는 아이디, 비밀번호를 입력 후 인증을 요청한다.
	 *			클라이언트에서 서버로 요청한 HttpsServletRequest의 요청 객체를 AuthenticationFilter가 요청을
	 *			인터셉터하는데 UsernamePasswordAuthenticationToken을 통해 인증을 진행할 토근을 만들어
	 *			AuthenticationManager에게 위임한다. 넘겨받은 id, pw를 이용해 인증을 진행하는데 성공 시,
	 *			Authentication 객체 생성과 성공을 전달하고, 그렇지 않으면 Exception에러를 발생시킨다.
	 *			인증에 성공 후, 만들어진 Authentication 객체를 AuthenticationProvider에게 전달하고
	 *			userDetailService에서 넘겨받은 Authentication 객체 정보를 이용해서 Database에 일치하는
	 *			회원 정보를 조회하여 꺼낸다. 꺼낸 정보를 UserDetails로 만들고 최종 User객체에 회원 정보를 등록한다.
	 *			등록이 되면서 User Session 정보가 생성된다. 이후 스프링 시큐리티 내 SecurityContextHolder
	 *			(시큐리티 인메모리)에 UserDetail정보를 저장한다. 그리고 최종적으로 JSESSIONID가 유효한지를
	 *			검증 후 인증을 완료 후 타겟 정보로 넘어가도록 돕는다.
	 *
	 *
	 *
	 *	2. 스프링 시큐리티 설정
	 *
	 *		# 환경 설정
	 *			- 의존 라이브러리 설정(pom.xml 설정)
	 *			> spring-security-web
	 *			> spring-security-config
	 *			> spring-security-core
	 *			> spring-security-tablibs
	 *
	 *		# 웹 컨테이너 설정(web.xml 설정)
	 *			- 스프링 시큐리티가 제공하는 서블릿 필터 클래스를 서블릿 컨테이너에 등록한다.
	 *			- 스프링 시큐리티는 필터 기반이므로 시큐리티 필터체인을 등록한다.
	 *				> context-param의 param-value 추가
	 *				(추가 파라미터: /WEB-INF/spring/security-context.xml)
	 *				> SpringSecurityFilterChain 추가
	 *
	 *		# 스프링 시큐리티 설정
	 *
	 *			- 스프링 시큐리티 컴포넌트를 빈으로 정의한다.
	 *			- spring/security-context.xml 설정
	 *
	 *		# 웹 화면 접근 정책
	 *
	 *			- 웹 화면 접근 정책을 정한다. (테스트를 위한 각 화면당 접근 정책을 설정)
	 *
	 *				대상		│	화면		│	접근정책
	 *			────────────────────────────────────────────────────────
	 *			일반게시판		│	목록화면	│	모두 접근 가능
	 *						│	등록화면	│	로그인한 회원만 접근 가능
	 *			────────────────────────────────────────────────────────
	 *			공지사항 게시판	│ 	목록화면	│	모두 접근 가능
	 *						│	등록화면	│	로그인한 관리자만 접근 가능
	 *			────────────────────────────────────────────────────────
	 *		
	 *		# 화면 설명
	 *			- 컨트롤러
	 *			> controller/SecurityBoardController
	 *			> controller/SecurityNoticeController
	 *			
	 *			- 화면
	 *			> board/list, register
	 *			> notice/list, register
	 *
	 * 
	 * 
	 */
	

}
