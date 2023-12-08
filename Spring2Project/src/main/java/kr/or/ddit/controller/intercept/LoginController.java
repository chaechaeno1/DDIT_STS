package kr.or.ddit.controller.intercept;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.or.ddit.vo.crud.CrudMember;

@Controller
public class LoginController {

	@RequestMapping(value = "/login1", method= RequestMethod.GET)
	public String loginForm() {
		return "login/loginForm";
	}
	@RequestMapping(value = "/login1", method= RequestMethod.POST)
	public String loginForm(String userId, String userPw, Model model) {
		CrudMember member = new CrudMember();
		member.setUserId(userId);
		member.setUserPw(userPw);
		member.setUserName("홍길동");
		model.addAttribute("user", member);
		return "login/success";
		
	}
}
