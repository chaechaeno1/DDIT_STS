package kr.or.ddit.controller.crud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/crud/member")
public class CrudMemberController {
	
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String crudMemberRegisterForm() {
		log.info("crudMemberRegisterForm() 실행...!");
		return "crud/member/register";		
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
