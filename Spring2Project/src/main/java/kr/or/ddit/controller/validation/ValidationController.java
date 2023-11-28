package kr.or.ddit.controller.validation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/validation")
public class ValidationController {
	
	/*
	 * [ 9장 입력 유효성 검증 ]
	 * 
	 * 	1. 입력값 검증
	 * 
	 * 		- 스프링 MVC Bean Validation 기능을 이용해 요청 파라미터 값이 바인딩된 도메인 클래스(또는 커맨드 클래스)의 입력값 검증을 한다.
	 * 
	 * 		[환경 설정]
	 * 		# 의존 관계 정의
	 * 			- 입력값 검증을 위한 의존 라이브러리 추가
	 * 			- pom.xml에 hibernate-validator 추가
	 * 
	 * 		# 입력값 검증 활성화
	 * 			- Member 클래스로 넘어가서 임시 테스트로 userId, userName 규칙을 활성화한다.
	 * 			- 이때, 규칙을 활성화 하기 위해서 사용할 어노테이션이 있다.
	 * 				> @Validated를 지정한다.
	 * 				> 입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의한다.
	 * 					BindingResult에는 요청 데이터의 바인딩 오류와 입력값 검증 오류 정보가 저장된다.
	 * 
	 * 		# 입력값 검증 환경설정 순서
	 * 		1. 입력값 검증을 위한 의존 라이브러리를 추가
	 * 		2. 입력값 검증 활성화
	 * 				> 활성화를 할 도메인 클래스 안에 @Validated 어노테이션을 지정한다.
	 * 		3. 도메인 클래스 내 필드에다가 검증을 위한 어노테이션들로 데이터 검증을 설정한다. (@NotBlank, @Size 등등)
	 * 		4. 에러를 받을 BindingResult를 설정한다. (컨트롤러 메서드 내 설정합니다)
	 * 
	 * 
	 * 
	 * 
	 * 	
	 */
	
	
	
	@RequestMapping(value = "registerValidationForm01",method=RequestMethod.GET)
	public String registerValidationForm01(Model model) {
		log.info("registerValidationForm01() 실행...!");		
		model.addAttribute("member", new Member());
		return "validation/registerValidationForm01";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
