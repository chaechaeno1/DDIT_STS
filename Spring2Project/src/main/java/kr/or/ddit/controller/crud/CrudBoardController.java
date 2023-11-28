package kr.or.ddit.controller.crud;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IBoardSerivce;
import kr.or.ddit.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/crud/board")
public class CrudBoardController {
	
	@Inject
	private IBoardSerivce service;
	

	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String crudRegisterForm() {
		
		log.info("crudRegisterForm() 실행...!");
		
		return "crud/register";
		
	}
	
	
	//등록 기능 담당
	@RequestMapping(value ="/register", method=RequestMethod.POST)
	public String crudRegister(Board board, Model model) {
		log.info("crudRegister() 실행...!");
		
		service.register(board);
		// 게시글을 입력 후 최신 게시글 번호가 담겨있다 (boardNo)
		log.info("게시글 등록 후 만들어진 최신 게시글 번호 : " + board.getBoardNo());
		
		model.addAttribute("msg", "등록이 완료되었습니다!");
		return "crud/success";
		
	}
	
	
	
	
	
	
	
	
}
