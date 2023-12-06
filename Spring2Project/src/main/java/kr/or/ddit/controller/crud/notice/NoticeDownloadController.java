package kr.or.ddit.controller.crud.notice;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeFileVO;

@Controller 
public class NoticeDownloadController {
	
	@Inject
	private INoticeService noticeService;

	//Model 과 ModelMap은 같은 용도임
	@RequestMapping(value = "/notice/download.do")
	public View noticeProcess(
		int fileNo, ModelMap model
			) {
		// 선택한 파일을 다운로드 하기 위한 정보를 파일번호에 해당하는 파일 정보를 얻어온다.
		NoticeFileVO noticeFileVO = noticeService.noticeDownload(fileNo);
		
		// 데이터 전달자를 통해서 파일 정보를 전달하기 위한 Map 선언
		Map<String, Object> noticeFileMap = new HashMap<String, Object>();
		noticeFileMap.put("fileName", noticeFileVO.getFileName());
		noticeFileMap.put("fileSize", noticeFileVO.getFileSize());
		noticeFileMap.put("fileSavepath", noticeFileVO.getFileSavepath());
		model.addAttribute("noticeFileMap", noticeFileMap);
		
		//리턴되는 NoticeDownloadView는 jsp 페이지로 존재하는 페이지 Name을 요청하는게 아니라,
		//클래스를 요청하는 것인데, 해당 클래스가 스프링에서 제공하는 AbstactView 클래스를 상속받은 클래스인데
		//그 클래스는 AbstractView를 상속 받아 renderMergedOutputMode 함수를 재정의할 때 View로 취급할 수 있게 해준다.
		return new NoticeDownloadView();
		
		//리턴타입을 view로 설정했다는건 return 타입이 page 인것
		//페이지로 인식되고있는 NoticeDownloadView() 클래스에서 다운로드 진행할 것
		
	}
	
}
