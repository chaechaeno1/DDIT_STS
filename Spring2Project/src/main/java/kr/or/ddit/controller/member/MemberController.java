package kr.or.ddit.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
}
