package kr.or.ddit.controller.conn;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.ILoginService;

@Controller
public class LoginController {
	
	@Inject
	private ILoginService loginService;
	
	@RequestMapping(value="/signin.do", method = RequestMethod.GET)
	public String signIn() {
		return "conn/signin";
	}
	
	@RequestMapping(value="/signup.do", method = RequestMethod.GET)
	public String signUpForm() {
		return "conn/signup";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> idCheck(@RequestBody Map<String, String> map){
		ServiceResult result = loginService.idCheck(map.get("memId"));
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/nickNameCheck.do", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> nickNameCheck(@RequestBody Map<String, String> map){
		ServiceResult result = loginService.nickNameCheck(map.get("memNickname"));
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
		
	}
	
	
	
	
	
}
