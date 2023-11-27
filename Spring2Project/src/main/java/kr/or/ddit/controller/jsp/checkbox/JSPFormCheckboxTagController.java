package kr.or.ddit.controller.jsp.checkbox;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/formtag/checkbox")
public class JSPFormCheckboxTagController {

	/*
	 * 	8. 체크박스 요소
	 * 
	 * 		-HTML 체크박스를 출력하려면 <form:checkbox> 요소를 사용한다.
	 * 
	 */
	
	@RequestMapping(value="/registerForm01", method=RequestMethod.GET)
	public String registerForm01(Model model) {
		log.info("registerForm01() 실행..!");
		model.addAttribute("member", new Member()); //순환체계 연결, 필수값임!!
		return "home/formtag/checkbox/registerForm01";
		
	}
	
	
}
