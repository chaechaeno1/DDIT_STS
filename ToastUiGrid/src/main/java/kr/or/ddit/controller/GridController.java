package kr.or.ddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/grid")
public class GridController {
	
	//하드코딩 -> 출력잘됨
	@RequestMapping(value = "/grid01", method = RequestMethod.GET)
	public String grid01(){				
		return "grid";
	}
	
	
	//
	@RequestMapping(value = "/grid02", method = RequestMethod.GET)
	public String grid02(){
		
		return "api_read";
	}
	
	
	
	
	@ResponseBody
	@PostMapping("/saveData")
	public String saveData(@RequestBody String jsonData) {
		return "result"; // 담은 result는 ajax에게 넘겨준다.
	}
	
	
	
	
}
