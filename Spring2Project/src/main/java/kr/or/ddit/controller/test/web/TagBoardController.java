package kr.or.ddit.controller.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/board/tag")
public class TagBoardController {

	// 태그 게시판 목록 
	@RequestMapping(value="/list.do", method = RequestMethod.GET)
	public String tagList() {
		return "test/list";
	}
	
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String tagForm() {
		return "test/form";
	}
	
	@RequestMapping(value="/detail.do", method = RequestMethod.GET)
	public String tagDetail() {
		return "test/detail";
	}
	
}
