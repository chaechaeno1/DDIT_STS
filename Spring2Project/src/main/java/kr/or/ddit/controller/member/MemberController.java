package kr.or.ddit.controller.member;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	/*
	 * [5장 : 컨트롤러 요청 처리]
	 * 
	 * 	1. 컨트롤러 메서드 매개변수
	 * 	
	 * 		- Model : 이동 대상에 전달할 데이터를 가지고 있는 인터페이스 
	 * 		- RedirectAttributes : 리다이렉트 대상에 전달할 데이터를 가지고 있는 인터페이스
	 * 		- 자바빈즈 클래스 : 요청 파라미터를 가지고 있는 자바빈즈 클래스
	 * 		- MultipartFile : 멀티파트 요청을 사용해 업로드된 파일 정보를 가지고 있는 인터페이스 
	 * 		- BindingResult : 도메인 클래스의 입력값 검증 결과를 가지고 있는 인터페이스 
	 * 		- Locale : 클라이언트 Locale (언어)
	 * 		- Principal : 클라이언트 인증을 위한 사용자 정보를 가지고 있는 인터페이스
	 * 
	 * 
	 * 
	 */
	
	
	
	//요청 처리 페이지 
	@RequestMapping(value="/registerForm", method = RequestMethod.GET)
	public String registerForm() {
		log.info("registerForm() 실행...!");
		return "member/registerForm";
		
	}
	
	// 1) URL 경로 상의 쿼리 파라미터 정보로부터 요청 데이터를 취득할 수 있다.
	// a버튼이므로 get 방식!! post 방식 안됨
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerByParameter(String userId, String password) {
		log.info("registerByParameter() 실행..!");
		log.info("userId : "+userId);
		log.info("password : "+password);
		return "success";
	}
	
	// 2) URL 경로 상의 경로 변수로부터 요청 데이터를 취득할 수 있다.
	@RequestMapping(value = "/register/{userId}", method = RequestMethod.GET)
	public String registerByPath(@PathVariable String userId) {
		//넘겨받은 userId를 경로상에서 꺼낼 때, @PathVariable를 이용한다.
		log.info("registerByPath() 실행..!");
		log.info("userId : "+userId);
		return "success";
	}
	
	// 3) HTML Form 필드명과 컨트롤러 매개변수명이 일치하면 요청 데이터를 취득할 수 있다.
	// //순서 바꿔도 데이터는 들어옴, 매개변수 명과 Form태그안에 name명만 일치한다면 순서상관없이 가능
	// 그리고, 매개변수의 위치는 순서에 상관없고 오직 매개변수명이 일치하면 요청 데이터를 취득할 수 있다. 
	@RequestMapping(value = "/register01", method=RequestMethod.POST)
	public String register01(String userId, String password) {
		log.info("register01() 실행...!");
		log.info("userId : "+userId);
		log.info("password : "+password);
		return "success";
		
	}
	
	//4) HTML Form 필드 값이 숫자일 경우에는 숫자로 타입 변환되어 데이터를 취득할 수 있다. 	
	@RequestMapping(value = "/register02", method = RequestMethod.POST)
	public String register02(String userId, String password, int coin) { 
		log.info("register02() 실행..!!");
		log.info("userId : "+userId);
		log.info("password : "+password);
		log.info("coin : "+coin);
		return "success";
	}
	
	
	
	/*
	 * 	3. 요청 데이터 처리 어노테이션
	 * 
	 * 		-@PathVariable : URL에서 경로 변수 값을 가져오기 위한 어노테이션
	 * 		-@RequestParam : 요청 파라미터 값을 가져오기 위한 어노테이션
	 * 		-@RequestHeader : 요청 헤더 값을 가져오기 위한 어노테이션
	 * 		-@RequestBody : 요청 본문 내용을 가져오기 위한 어노테이션
	 * 		-@CookieValue : 쿠키 값을 가져오기 위한 어노테이션
	 */
	
	// 1) URL 경로 상의 경로 변수가 여러 개일 때, @PathVariable 어노테이션을 사용하여 특정한 경로 변수명을 지정해준다.
	@RequestMapping(value = "/register/{userId}/{coin}", method = RequestMethod.GET)
	public String registerByPath(
			@PathVariable("userId") String userId,
			@PathVariable("coin") int coin
			) {
		log.info("registerByPath() 실행...!");
		log.info("userId : " +userId);
		log.info("coin : " + coin);
		return "success";
		
		
	}
	
	// 2) @RequestParam 어노테이션을 사용하여 특정한 HTML Form의 필드명을 지정하여 요청을 처리 한다.
	@RequestMapping(value = "/register0202", method=RequestMethod.POST)
	public String register0202(
			@RequestParam("userId") String username, String password, int coin
			) {		
		log.info("register0202() 실행..!");
		log.info("username : " + username);
		log.info("password: " + password);
		log.info("coin: " + coin);
		return "success";
		
	}
	
	
	/*
	 * 4. 요청 처리 자바빈즈
	 * 
	 * 
	 * 
	 */
	
	
	// 1) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.
	
	@RequestMapping(value = "/beans/register01", method=RequestMethod.POST)
	public String registerBeans01(Member member) {
		log.info("registerBeans01() 실행...!");
		log.info("member.userId : "+ member.getUserId());
		log.info("member.password : "+ member.getPassword());
		log.info("member.coin : "+ member.getCoin());
		return "success";
	}
	
	//2) 폼 텍스트 필드 요소값을 자바빈즈 매개변수와 기본 데이터 타입인 정수 타입 매개변수로 처리한다.
	// member에는 3가지, int는 따로 받아줌
	// coin으로 넘어온 데이터는 하나인데, member와 int coin 두개라는 매개변수가 있는데.. 어떻게?
	// 두군데 다 들어간다! coin 이라는 매개변수명이 같으므로!
		@RequestMapping(value = "/beans/register02", method=RequestMethod.POST)
		public String registerBeans02(Member member, int coin) {
			log.info("registerBeans02() 실행...!");
			log.info("member.userId : "+ member.getUserId());
			log.info("member.password : "+ member.getPassword());
			log.info("member.coin : "+ member.getCoin());
			log.info("coin : "+ coin);
			return "success";
		}
	
	/*
	 * 5. Date 타입 처리
	 * 
	 * 	- 스프링 MVC는 Date 타입의 데이터를 처리하는 여러 방법을 제공
	 * 	- 클라이언트에서 날짜 데이터를 서버로 보낼 때에는 '년/월/일' 형식에 맞춰서 보내야한다. (슬래시만 적용) (기본적인 방식)
	 * 
	 */
	
	//1) 
	
	@RequestMapping(value = "registerByGet01", method=RequestMethod.GET)	
	public String registerByGet01(String userId, Date dateOfBirth) {
		log.info("registerByGet01() 실행...!");
		log.info("userId : " +userId);
		log.info("dateOfBirth : " +dateOfBirth);
		return "success";
	}
		
	
	//2) 
	@RequestMapping(value = "registerByGet02", method=RequestMethod.GET)	
	public String registerByGet02(Member member) {
		log.info("registerByGet02() 실행...!");
		log.info("member.userId : " +member.getUserId());
		log.info("member.dateOfBirth : " +member.getDateOfBirth());
		return "success";
	}	
		

	//3)
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Member member) {
		log.info("register() 실행...!");
		log.info("member.userId : " +member.getUserId());
		log.info("member.password : " +member.getPassword());
		log.info("member.dateOfBirth : " +member.getDateOfBirth());
		return "success";
	}
	
	
	/*
	 * 6. @DateTimeFormat 어노테이션
	 * 
	 * 	-@DateTimeFormat 어토네이션의 pattern 속성값에 원하는 날짜형식 지정할 수 있다.
	 * 
	 * 
	 */
		
	@RequestMapping(value = "/registerByGet03", method=RequestMethod.POST)
	public String registerByGet03(String userId, @DateTimeFormat(pattern = "yyyyMMdd") Date dateOfBirth) { //이제는 2023/11/23으로 보내면 에러발생함
		log.info("registerByGet03() 실행...!");
		log.info("userId : " +userId);
		log.info("dateOfBirth : " +dateOfBirth);
		return "success";
		
		
	}
	
	
	/*
	 * 7.폼 방식 요청 처리
	 * 
	 * 
	 * 
	 */
	
	
	//1) 폼 텍스트 필드 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.
	
	@RequestMapping(value="/registerUserId", method = RequestMethod.POST)
	public String registerUserId(String userId) {
		log.info("registerUserId() 실행...!");
		log.info("userId : " + userId);
		return "success";
	}
	
	
	//2) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.
	
	@RequestMapping(value="/registerMemberUserId", method = RequestMethod.POST)
	public String registerMemberUserId(Member member) {
		log.info("registerMemberUserId() 실행...!");
		log.info("member.userId : " + member.getUserId());
		return "success";
	}
	
	//3) 폼 비밀번호 필드 요소값을 자바빈즈 매개변수로 처리한다.
	@RequestMapping(value="/registerPassword", method = RequestMethod.POST)
	public String registerPassword(Member member) {
		log.info("registerPassword() 실행...!");
		log.info("member.password : " + member.getPassword());
		return "success";
	}
	
	//4) 폼 라디오버튼 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.
	@RequestMapping(value="/registerRadio", method = RequestMethod.POST)
	public String registerRadio(String gender) {
		log.info("registerRadio() 실행...!");
		log.info("gender : " + gender);
		return "success";
	}
	
	//5) 폼 셀렉트 박스 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.
	@RequestMapping(value="/registerSelect", method = RequestMethod.POST)
	public String registerSelect(String nationality) {
		log.info("registerSelect() 실행...!");
		log.info("nationality : " +nationality);
		return "success";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		
	
}
