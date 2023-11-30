package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.service.impl.NoticeServiceImpl;
import kr.or.ddit.vo.crud.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeInsertController {
	
	@Inject
	private INoticeService noticeService;

	@RequestMapping(value = "/form.do", method=RequestMethod.GET)
	public String noticeInsertForm() {
		return "notice/form";
		
	}
	
	@RequestMapping(value = "/insert.do", method=RequestMethod.POST)
	public String noticeInsert(NoticeVO noticeVO, Model model, RedirectAttributes ra) { // RedirectAttributes 일회성 메시지를 전달
		String goPage = ""; //이동할 페이지 정보
		
		//넘겨받은 데이터 검증 후, 에러가 발생한 데이터에 대한 에러정보를 담을 공간
		Map<String, String> errors = new HashMap<String, String>();
		
		//제목 데이터가 누락되었을 때 에러 정보 저장(라이브러리 추가 후 작성)
		if(StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요.");			
		}
		//내용 데이터가 누락되었을 때 에러 정보 저장
		if(StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요.");			
		}
		// 기본 데이터의 누락정보에 따른 에러 정보 갯수로 분기 처리
		if(errors.size() > 0) { // 에러 갯수가 0보다 클 때 (에러가 존재)
			model.addAttribute("errors", errors);
			model.addAttribute("noticeVO", noticeVO);
			goPage = "notice/form";	
			
		}else {	//에러가 없을 때
			//로그인 처리를 하지 않고 게시글을 작성 하므로 작성자를 하드코딩한다.
			noticeVO.setBoWriter("a001");
			ServiceResult result =  noticeService.insertNotice(noticeVO);
			
			if(result.equals(ServiceResult.OK)) { //등록 성공
				goPage = "redirect:/notice/detail.do?boNo="+noticeVO.getBoNo();
				ra.addFlashAttribute("message", "게시글 등록이 완료되었습니다.");
				
			}else {	//등록 실패
				model.addAttribute("noticeVO", noticeVO);
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				goPage = "notice/form";
			}
			
		}
		
		return goPage;
		
		
		
		
		
		
	} // noticeInsert 끝
	

	
	
	
	
	
	
	
	
	
}
