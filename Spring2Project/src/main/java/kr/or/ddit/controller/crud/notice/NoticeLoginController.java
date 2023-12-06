package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeLoginController {
	
	@Inject
	private INoticeService noticeService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String noticeLogin(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/login";
	}
	
	
	@RequestMapping(value = "/loginCheck.do", method = RequestMethod.POST)
	public String logincheck(
			HttpServletRequest req, NoticeMemberVO member, Model model,
			RedirectAttributes ra) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if(StringUtils.isBlank(member.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요.");
		}
		if(StringUtils.isBlank(member.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요.");
		}
		
		if(errors.size() > 0 ) { //에러 정보가 존재 한다는 뜻
			model.addAttribute("errors", errors);
			model.addAttribute("member", member);
			model.addAttribute("bodyText", "login-page");
			goPage = "conn/login";
		} else {	// 정상적인 데이터 (아이디, 비번 칸에 입력이 모두 다 된 상태) 
			NoticeMemberVO memberVO = noticeService.loginCheck(member);
			if(memberVO != null) { // 로그인 성공
				HttpSession session = req.getSession();
				session.setAttribute("SessionInfo",memberVO);
				ra.addFlashAttribute("message", "로그인 성공!");
				goPage = "redirect:/notice/list.do";
			} else { //로그인 실패(DB에 등록된 id,pw 값이 아닌 값을 입력한 경우)
				model.addAttribute("message", "서버에러, 로그인 정보를 정확하게 확인해주세요.");
				model.addAttribute("member", member);
				model.addAttribute("bodyText", "login-page");
				goPage = "conn/login";	
			}
		}
		return goPage;
	}
	
	
	// register.jsp의 ajax와 연결
	@ResponseBody
	@RequestMapping(value= "/idCheck.do" , method=RequestMethod.POST)
	public ResponseEntity<ServiceResult> idCheck(@RequestBody Map<String, String> map){
		/*
		 * 단일 데이터를 꺼낼 때
		 * 0) ajax 설정에서 ContentType 설정을 하지 않고, 데이터만 {memId : id} 설정해서 넘길 때
		 * 		- String memId로 꺼낼 수 있다.
		 * 		> 이 형태는 쿼리스트링에 구성된 memId를 꺼낼때도 동일함
		 * 
		 * 1) ajax 설정에서 ContentType 설정을 하지 않고, 데이터만 JSON.stringify() 일 때
		 * 		- @RequestBody로 String memId를 꺼내면 '%7B%22memId%22%3A...=' 이런 데이터가 넘어옴
		 * 
		 * 2) ajax 설정에서 ContentType 설정하고, 데이터만 JSON.stringify() 일 때(데이터 JSON 객체로 넘어감)
		 * 		- @RequestBody로 String memId를 꺼내면, '{memId : a001}' 데이터가 넘어옴
		 * 
		 * 3) ajax 설정에서 ContentType을 설정하고, 데이터만 JSON.stringify() 일 때 (데이터 JSON 객체로 넘어감)
		 * 		- @RequestParam으로 String memId를 꺼내면 400에러가 발생한다.
		 * 
		 * 4) ajax 설정에서 ContentType 설정을 하고, 데이터만 JSON.stringify() 일 때(데이터 JSON 객체로 넘어감)
		 * 		- @RequestBody Map<String, String> map을 꺼내면 'a001' 데이터가 넘어옴
		 * 
		 * 우리는 단일 데이터를 받아낼 때에 고민하지 말고 컬렉션 Map 또는 단일 데이터를 받을 VO객체를 파라미터로 설정하여 값을 받는다.
		 * 
		 */
		log.info("넘겨 받은 아이디 : " + map.get("memId"));
		ServiceResult result = noticeService.idCheck(map.get("memId"));
		return new ResponseEntity<ServiceResult>(result, HttpStatus.OK);

	}
	
	

	@RequestMapping(value = "/signup.do", method=RequestMethod.GET)
	public String signupForm(Model model) {
		model.addAttribute("bodyText", "register-page");
		return "conn/register";
	}
	
	@RequestMapping(value = "/signup.do", method=RequestMethod.POST)
	public String signup(HttpServletRequest req, NoticeMemberVO memberVO, Model model, RedirectAttributes ra) {
	//HttpServletRequest
	//이 객체는 현재의 HTTP 요청에 대한 정보를 담고 있는 객체로, 클라이언트로부터 받은 다양한 정보를 추출하는 데 사용됩니다. 
	//주로 HTTP 요청의 헤더, 매개변수, 세션 등에 접근할 때 사용됩니다.
		String goPage = ""; // 최종적으로 이동할 페이지를 저장할 변수
		
		// 에러 메시지를 담을 Map 객체 생성
		Map<String, String> errors = new HashMap<String, String>();
		
		// 입력값 유효성 검사
		if(StringUtils.isBlank(memberVO.getMemId())) {
			errors.put("memId", "아이디를 입력해주세요.");
		}
		if(StringUtils.isBlank(memberVO.getMemPw())) {
			errors.put("memPw", "비밀번호를 입력해주세요.");
			
		}
		if(StringUtils.isBlank(memberVO.getMemName())) {
			errors.put("memName", "이름을 입력해주세요.");
			
		}
		
		if(errors.size() > 0 ) { // 넘겨받은 데이터의 에러가 존재
			// 에러 정보와 입력된 회원 정보를 모델에 추가
			model.addAttribute("errors", errors);
			model.addAttribute("member", memberVO);
			model.addAttribute("bodyText", "register-page");
			goPage = "conn/register"; // 회원 가입 페이지로 이동
		}else {	//정상적인 데이터를 받았을 때
			 // 회원 가입 서비스를 호출하여 가입을 시도하고 결과를 받아옴
			ServiceResult result = noticeService.signup(req, memberVO);
			if(result.equals(ServiceResult.OK)) { // 가입 성공
				// 가입 성공 메시지를 플래시 속성에 추가하고 로그인 페이지로 리다이렉트
				ra.addFlashAttribute("message", "회원가입을 완료하였습니다!");
				goPage = "redirect:/notice/login.do";
			}else {	//가입 실패
				// 실패 메시지와 회원 정보를 모델에 추가하고 회원 가입 페이지로 이동
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				model.addAttribute("member", memberVO);
				model.addAttribute("bodyText", "register-page");
				goPage = "conn/register";	
			}
		}
		return goPage; // 최종적으로 이동할 페이지 반환

	}
	
	
	@RequestMapping(value = "/forget.do",method=RequestMethod.GET)
	public String loginForgetIdAndPw(Model model) {
		model.addAttribute("bodyText", "login-page");
		return "conn/forget";
	}

	
	
	
	
	
	
	
	
	
	
	
}
