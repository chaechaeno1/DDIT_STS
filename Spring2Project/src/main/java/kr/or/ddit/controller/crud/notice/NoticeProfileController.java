package kr.or.ddit.controller.crud.notice;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.CustomUser;
import kr.or.ddit.vo.crud.NoticeMemberVO;

@Controller
@RequestMapping("/notice")
public class NoticeProfileController {
	
	@Inject
	private INoticeService noticeService;

	@RequestMapping(value = "/profile.do", method = RequestMethod.GET)
	public String noticeProfile(
			HttpServletRequest req,
			RedirectAttributes ra,
			Model model
			) {
		
		String goPage = "";
		
		// [일반적인 방법] 세션을 활용
//		HttpSession session = req.getSession();
//		NoticeMemberVO sessionMember = (NoticeMemberVO)session.getAttribute("SessionInfo");
//		
//		if(sessionMember == null) {
//			ra.addFlashAttribute("message", "로그인 후 이용가능합니다!");
//			return "redirect:/notice/login.do";
//		}
//		
//		NoticeMemberVO member = noticeService.selectMember(sessionMember.getMemId());
		
		// [스프링 시큐리티] 시큐리티 세션을 활용
		CustomUser user =  (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NoticeMemberVO memberVO = user.getMember();
		NoticeMemberVO member = noticeService.selectMember(memberVO.getMemId());
		
		if(member != null) {
			model.addAttribute("member", member);
			goPage = "notice/profile";
		}else { 
			ra.addFlashAttribute("message", "로그인 후 이용 가능합니다!");
			goPage = "redirect:/notice/login.do";
		}
		
		return "notice/profile";
	}
	
	@RequestMapping(value = "/profileUpdate.do", method=RequestMethod.POST)
	public String noticeProfileUpdate(
			HttpServletRequest req,
			NoticeMemberVO memberVO,
			RedirectAttributes ra,
			Model model
			) {
		String goPage = "";
		ServiceResult result = noticeService.profileUpdate(req, memberVO);
		if(result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "회원 정보 수정이 완료되었습니다!");
			goPage = "redirect:/notice/profile.do";
		}else{
			model.addAttribute("message", "서버에러, 수정이 실패했습니다!");
			model.addAttribute("member", memberVO);
			goPage = "notice/profile";
		}
		return goPage;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
