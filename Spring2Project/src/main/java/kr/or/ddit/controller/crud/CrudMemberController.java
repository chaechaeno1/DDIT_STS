package kr.or.ddit.controller.crud;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.crud.CrudMember;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/crud/member")
public class CrudMemberController {
	
	
	@Inject
	private IMemberService service;
	
	
	//등록 화면단
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String crudMemberRegisterForm() {
		log.info("crudMemberRegisterForm() 실행...!");
		return "crud/member/register";		
	}
	
	//등록 기능
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String crudMemberRegister(CrudMember member, Model model) {
		log.info("crudMemberRegister() 실행...!");
		service.register(member);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "crud/member/success";
	}
	
	//목록 화면
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String crudMemberList(Model model) {
		log.info("crudMemberList() 실행...!");
		List<CrudMember> memberList =  service.list();
		model.addAttribute("memberList", memberList);
		return "crud/member/list";
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
}
