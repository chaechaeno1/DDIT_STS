package kr.or.ddit.controller.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/el")
public class JSPELController {

	/*
	 * 	8. EL함수 
	 * 
	 * 
	 * 		- JSTL의 표현언어(EL)에서 사용할 수 있는 함수를 제공한다.
	 * 
	 * 		fn:contains(str1, str2)
	 * 		- 지정한 문자열이 포함돼 있는지 판단한다.
	 * 		fn:containsIgnoreCase(str1,str2)
	 * 		- 지정한 문자열이 대문자/소문자를 구분하지 않고 포함돼 있는지 판단한다.
	 * 		fn:startWith(str1, str2)
	 * 		- 지정한 문자열로 시작하는지 판단한다.
	 * 		fn:endWith(str1, str2)
	 * 		- 지정한 문자열로 끝나는지 판단한다.
	 * 		fn:indexOf(str1, str2)
	 * 		- 지정한 문자열이 처음으로 나왔을 때의 인덱스를 구한다.
	 * 		fn:length(obj)
	 * 		- 컬렉션 또는 배열의 요소 개수, 문자열 길이를 구한다.
	 * 		fn:escapeXml(str)
	 * 		- 지정한 문자열을 XML구문으로 해석되지 않도록 이스케이프 한다.
	 * 		fn:replace(str, src, dest)
	 * 		- 문자열을 치환한다.
	 * 		fn:toLowerCase(str)
	 * 		- 문자열을 소문자로 변환한다.
	 * 		fn:toUpperCase(str)
	 * 		- 문자열을 대문자로 변환한다.
	 * 		fn:trim(str)
	 * 		- 문자열을 trim한다. (공백제거)
	 * 		fn:substring(str, idx1, idx2)
	 * 		- 지정한 범위에 해당하는 문자열을 잘라낸다.
	 * 		fn:substringAfter(str1, str2)
	 * 		- 지정한 문자열에 일치하는 이후의 문자열을 잘라낸다.
	 * 		fn:substringBefore(str1, str2)
	 * 		- 지정한 문자열에 일치하는 이전의 문자열을 잘라낸다.
	 * 		fn:join(array, str2)
	 * 		- 문자열을 구분자로 분할해서 하나의 문자열로 만든다.
	 * 		fn:split(str1, str2)
	 * 		- 문자열을 구분자로 분할해서 문자열 배열을 만든다. 
	 * 
	 */
	
	
	@RequestMapping(value = "/home0101", method = RequestMethod.GET)
	public String home0101(Model model) {
		String str = "<font>Hello World!</font>";
		model.addAttribute("str",str);
		return "home/el/home0101";
		
	}
	
	
}
