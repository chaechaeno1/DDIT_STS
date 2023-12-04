package kr.or.ddit.controller.file.item03;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/item3")
public class FileUploadController03 {

	/*
	 * 	4. 비동기 방식 업로드
	 * 
	 * 		- 비동기 방식으로 여러개의 이미지를 업로드 하는 파일 업로드 기능을 구현한다.
	 * 	
	 * 		# 환경설정
	 * 
	 * 			- 의존 관계  정의(pom.xml 설정)
	 * 				> commons-io		: 파일을 처리하기 위한 의존 라이브러리
	 * 				> imgscalr-lib		: 이미지 변환을 처리하기 위한 의존 라이브러리
	 * 				> jackson-databind	: json 데이터 바인딩을 위한 의존 라이브러리
	 * 		
	 * 		# 파일 업로드 구현 설명
	 * 
	 * 			- 파일 업로드 등록화면 컨트롤러 만들기 (FileUploadController03)
	 * 			- 파일 업로드 등록화면 메소드 만들기 (item3RegisterForm:get)
	 * 			- 파일 업로드 등록 화면 만들기 (item3/register.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 
	 * 
	 * 
	 * 
	 * 
	 */
	
	// root-context.xml에서 설정한 uploadPath 빈 등록 paht 경로를 사용한다.
	@Resource(name="uploadPath")
	private String resourcePath;
	

	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String item3RegisterForm() {
		return "item3/register";
		
	}
	
	@ResponseBody // 응답을 내보낼 때 데이터 형식으로 내보내기
	@RequestMapping(value = "/uploadAjax",method=RequestMethod.POST, produces = "text/plain;charset=utf-8") //produces 있다는 것 ajax에서 데이터타입이 있어야 하는것
	public ResponseEntity<String> uploadAjax(MultipartFile file){
		log.info("originalName : " + file.getOriginalFilename());
		
		//savedName은 /2023/12/04/UUID_원본파일명을 리턴한다.
		String savedName = UploadFileUtils.uploadFile(resourcePath, file.getOriginalFilename(), file.getBytes());
		return new ResponseEntity<String>(savedName, HttpStatus.OK);
	
	}
	
	
	
	
	
	
	
	
	
	
}
