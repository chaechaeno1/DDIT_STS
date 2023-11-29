package kr.or.ddit.controller.crud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeRetreiveController {
	
	@RequestMapping(value = "/list.do")
	public String noticeList() {
		return "notice/list";
	}

}
