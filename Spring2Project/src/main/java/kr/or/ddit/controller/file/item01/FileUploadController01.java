package kr.or.ddit.controller.file.item01;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.IItemService;
import kr.or.ddit.vo.Item;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/item")
public class FileUploadController01 {

	/*
	 * [ 13장 파일업로드 ]
	 * 
	 * 	1. 파일 업로드 설명
	 * 
	 * 		- 서블릿 3.0에서 지원하는 파일 업로드 기능과 스프링 웹에서 제공하는 컴포넌트를 사용하여 파일을 업로드 한다.
	 * 
	 * 		# 파일 업로드 설정
	 * 
	 * 			1) 서블릿 3.0 이상 지원
	 * 			2) 서블릿 표준 파일 업로드 기능을 활성화
	 * 			3) 스프링 MVC와 연계
	 * 			4) 업로드 된 파일 저장 위치 설정
	 * 		
	 * 		# 환경 설정
	 * 
	 * 			1) 의존 관계 정의
	 * 			- 파일을 처리하기 위해서 의존 라이브러리 추가
	 * 				> pom.xml commons-io 추가
	 * 
	 * 			2) 웹 컨테이너 설정
	 * 				> web.xml 서블릿 표준 버전 3.1로 설정
	 * 				> multipart-config 활성화(web.xml의 servlet 태그 내 설정) (설정은 하지만 주석처리 할 것, 지금 사용하는 방법 아니므로!)
	 * 
	 * 			3) 스프링 웹 설정
	 * 				> servlet-context.xml
	 * 				> multipartResolver Bean 등록 (id는 mulitpartResolver로 설정)
	 
	 * 				[파일 업로드 설정]
	 * 				파일 업로드를 설정하는 방식은 2가지가 있다.
	 * 		
	 * 				- StandardServletMultipartResolver 사용 시 설정,
	 * 					> servlet 3.0의 Part를 이용한 MultipartFile 데이터 처리 
	 * 					> servlet-context.xml 에 설정
	 * 						> StandardServletMultipartResolver Bean 등록
	 * 					> web.xml에 설정
	 * 						> multipart-config (servlet 태그 안에 설정)					
	 * 
	 * 				- CommonsMultipartResolver 사용 시 설정, (우리가 사용할 방식)
	 * 					> Commons Fileupload API를 이용한 MultipartFile 데이터 처리
	 * 					> bean 속성으로 maxUploadSize, maxInMemorySize 및 defaultEncoding 설정을 제공
	 * 					> 기본값 및 허용되는 값에 대한 자세한 내용은 각 DiskFileUpload 속성을 참조
	 * 					> pom.xml 설정
	 * 						> commons-fileupload 추가
	 * 					> root-context.xml에 설정
	 * 						> CommonsMultipartResolver bean 등록
	 * 
	 * 
	 * 
	 * 				- root-context.xml에 설정
	 * 					> uploadPath bean 등록
	 * 
	 * 				- multipartFilter 필터 등록
	 * 					> web.xml
	 * 
	 * 
	 * 
	 * 			# 데이터베이스 준비
	 * 
	 * 				- item 테이블 생성 (item, item2, item3, item3_attach)
	 * 					(단일파일, 멀티파일, 비동기처리를 위한 CRUD)
	 * 
	 * 
	 * 
	 * 
	 * 	2. 이미지 업로드
	 * 
	 * 		- 한 개의 이미지를 업로드 하는 기본 파일 업로드 기능을 구현합니다.
	 * 		
	 * 		# 파일 업로드 구현 설명
	 * 
	 * 			- 파일 업로드 등록 화면 컨트롤러 만들기 (FileUploadController01)
	 * 			- 파일 업로드 등록 화면 컨트롤러 메소드 만들기 (itemRegisterForm:get)
	 * 			- 파일 업로드 등록 화면 만들기 (item/register.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 등록 기능 컨트롤러 메소드 만들기 (itemRegister:post)
	 * 			- 파일 업로드 등록 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 등록 완료 페이지 만들기
	 * 			- 여기까지 확인
	 * 
	 * 	
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	//root-context.xml에서 설정한 uploadPath bean 등록 path 경로를 사용한다.
	@Resource(name="uploadPath")
	private String resourcePath;
	
	@Inject
	private IItemService itemService;
	
	
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	//@GetMapping("/register")	
	public String itemRegisterForm(Model model) {
		// 스프링 form 태그를 이용한다면, modelAttribute 속성에 연결된 item 자바 객체와의 연결을 위함
		// 스프링 form 태그를 활용한다면 아래와 같이 순환체계를 연결하기 위해서 model에 데이터를 담아 클라이언트로 보낸다.
		//model.addAttribute("item", new Item());
		return "item/register";
		
	}
	
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String itemRegister(Item item, Model model) {
		
		MultipartFile file =  item.getPicture();
		
		log.info("originalName : "+ file.getOriginalFilename());
		log.info("size : " + file.getSize());
		log.info("contentType : " + file.getContentType());
		
		//넘겨 받은 파일을 이용하여 파일 업로드(복사)를 진행한다.
		//return : UUID + "_" + 원본 파일명
		String createdFileName =  uploadFile(file.getOriginalFilename(), file.getBytes());
		
		item.setPictureUrl(createdFileName);
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "item/success";
		
		
	}
	
	
	
	private String uploadFile(String originalName, byte[] fileData) throws IOException {
		UUID uuid = UUID.randomUUID(); // UUID로 파일명 생성
		// UUID + "_" + 원본 파일명
		String createdFileName = uuid.toString() + "_" + originalName;
		
		File file = new File(resourcePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		File target = new File(resourcePath, createdFileName); //파일 업로드 준비
		FileCopyUtils.copy(fileData, target); //파일 복사 진행, 빨간줄 뜨면 throws 추가하면 된다!
		return createdFileName;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
