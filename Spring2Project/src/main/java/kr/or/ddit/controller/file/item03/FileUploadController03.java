package kr.or.ddit.controller.file.item03;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.service.IItemService3;
import kr.or.ddit.vo.Item3;
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
	 * 			- 파일 업로드 등록 기능 컨트롤러 메소드 만들기 (item3register.post)
	 * 			- 파일 업로드 등록 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 등록 기능 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 등록 완료 페이지 만들기
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 목록 화면 컨트롤러 메소드 만들기 (item3List:get)
	 * 			- 파일 업로드 목록 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 목록 화면 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 목록 화면 Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 목록 화면 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 목록 화면 만들기(item3/list.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 수정화면 컨트롤러 메소드 만들기 (item3ModifyForm:get)
	 * 			- 파일 업로드 수정 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 수정 화면 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 수정 화면  Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 수정 화면 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 수정 화면 만들기 (item3/modify.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 수정 기능 컨트롤러 메소드 만들기 (item3Modify:post)
	 * 			- 파일 업로드 수정 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 수정 기능 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 수정 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 수정 기능 Mapper xml 쿼리 만들기
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 삭제 화면 컨트롤러 메소드 만들기 (item3RemoveForm:get)
	 * 			- 파일 업로드 삭제 화면 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 삭제 화면 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 삭제 화면  Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 삭제 화면 Mapper xml 쿼리 만들기
	 * 			- 파일 업로드 삭제 화면 만들기 (item3/remove.jsp)
	 * 			- 여기까지 확인
	 * 
	 * 			- 파일 업로드 삭제 기능 컨트롤러 메소드 만들기 (item3Remove:post)
	 * 			- 파일 업로드 삭제 기능 서비스 인터페이스 메소드 만들기
	 * 			- 파일 업로드 삭제 기능 서비스 클래스 메소드 만들기
	 * 			- 파일 업로드 삭제 기능 Mapper 인터페이스 메소드 만들기
	 * 			- 파일 업로드 삭제 기능 Mapper xml 쿼리 만들기
	 * 			- 여기까지 확인
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	// root-context.xml에서 설정한 uploadPath 빈 등록 path 경로를 사용한다.
	
	@Resource(name="uploadPath")
	private String resourcePath;
	
	@Inject 
	private IItemService3 itemService;
	

	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String item3RegisterForm() {
		return "item3/register";
		
	}
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String item3Register(Item3 item, Model model) {
		
		//파일 정보 처리
		
		//HTTP 요청으로부터 받은 Item3 객체에서 파일 정보를 추출합니다.
		//배열 files에는 전송된 파일의 이름들이 들어 있습니다.
		String[] files = item.getFiles();
		
		for(int i = 0; i <files.length; i++) {
			log.info("files["+i+"] : " + files[i]);
		}
		
		//아이템 등록 및 뷰로 이동
		itemService.register(item);
		model.addAttribute("msg", "등록이 완료되었습니다.");
		return "item3/success";
		
	}
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public String item3List(Model model) {
		List<Item3> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
		return "item3/list";
		
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String item3ModifyForm(int itemId, Model model) {
		Item3 item =  itemService.read(itemId);
		model.addAttribute("item", item);
		return "item3/modify";

	}

	
	@RequestMapping(value = "/modify", method=RequestMethod.POST)
	public String item3Modify(Item3 item, Model model) {
		String[] files = item.getFiles();
		
		for(int i=0; i<files.length; i++) {
			log.info("files["+i+"] : " + files[i]);
		}
		
		itemService.modify(item);
		model.addAttribute("msg", "수정이 완료되었습니다.");
		return "item3/success";
	}
	
	
	// 삭제 화면은 미리보기의 의미만 있을 뿐...
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String item3RemoveForm(int itemId, Model model) {
		Item3 item = itemService.read(itemId);
		model.addAttribute("item", item);
		return "item3/remove";
		
	}
	
	
	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public String item3Remove(int itemId, Model model) {
		itemService.remove(itemId);
		model.addAttribute("msg", "삭제가 완료되었습니다!");
		return "item3/success";
		
		
	}
	

	@ResponseBody
	@RequestMapping(value = "/getAttach/{itemId}", method = RequestMethod.GET)
	public List<String> getAttach(@PathVariable("itemId") int itemId){
		log.info("itemId : "+ itemId);
		
		//item3_attach 테이블에서 fullname 추출
		//itemId 하나에 들어있는 파일들 (여러개가 될 수 있음)
		return itemService.getAttach(itemId);
		/*
		 * select fullname 
		 * from item3_attach 
		 * where item_id = #{itemId} 
		 * order by regdatedesc
		 */
	
		
	}
	

	//AJAX를 통한 파일 업로드 처리
	@ResponseBody 
	// 응답을 내보낼 때 데이터 형식으로 내보내기
	// 메서드의 반환값을 HTTP 응답의 본문으로 사용해야 함을 나타냅니다.
	// 이는 반환값을 JSON 또는 XML로 변환하여 HTTP 응답으로 전송합니다.
	@RequestMapping(value = "/uploadAjax",method=RequestMethod.POST, produces = "text/plain;charset=utf-8") 
	//produces 있다는 것 ajax에서 데이터타입이 있어야 하는것
	// 'produces' 속성은 메서드가 생성할 수 있는 미디어 타입을 지정합니다.
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		log.info("originalName : " + file.getOriginalFilename());
		
		//savedName은 /2023/12/04/UUID_원본파일명을 리턴한다.
		// savedName은 업로드된 파일의 경로를 나타내는 문자열입니다.
		// UploadFileUtils.uploadFile 메서드를 사용하여 파일을 특정 디렉토리 (resourcePath)에 새 이름으로 저장합니다.
		String savedName = UploadFileUtils.uploadFile(resourcePath, file.getOriginalFilename(), file.getBytes());
		
		// ResponseEntity는 Spring의 클래스로, HTTP 응답을 나타냅니다.
		 // 여기서는 String 타입의 본문(savedName)과 HttpStatus.OK 상태를 갖는 ResponseEntity를 생성합니다.
		return new ResponseEntity<String>(savedName, HttpStatus.OK);
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/displayFile", method=RequestMethod.GET)
	public ResponseEntity<byte[]> display(String fileName) throws Exception{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		log.info("fileName : " + fileName);
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") +1);
			MediaType mType =  MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(resourcePath + fileName);
			
			if(mType != null) { //이미지 파일 일 때
				headers.setContentType(mType);
				
			}else { // 이미지 파일이 아닐 때
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close(); //오류나면 throws 설정
		}
		return entity;
		
	}
	
	
	
	
	
	
	
	
	
	
}
