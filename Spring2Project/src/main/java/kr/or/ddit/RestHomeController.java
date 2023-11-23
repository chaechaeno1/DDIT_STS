package kr.or.ddit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.vo.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestHomeController {
	

	@RequestMapping(value = "/goRestHome0301", method = RequestMethod.GET)
	public Member goRestHome0301() {
		log.info("goRestHome0301() 실행...!");
		Member member = new Member();
		return member; //원래는 return값을 페이지로 인식하지만 어노테이션 추가해서 데이터로 인식하게 함
				
	}
	
	@RequestMapping(value = "/goRestHome0401", method = RequestMethod.GET)
	public List<Member> goRestHome0401(){
		log.info("goRestHome0401() 실행...!");
		List<Member> list = new ArrayList<Member>();
		Member member = new Member();
		Member member2 = new Member();
		list.add(member);
		list.add(member2);
		
		return list;
		
	}
	
	//반환 값이 컬렉션 Map 타입이면 JSON 객체 타입으로 자동으로 변환된다.

		@RequestMapping(value = "/goRestHome0501", method= RequestMethod.GET)
		public Map<String, Member> goRestHome0501(){
			log.info("goRestHome0501() 실행...!");
			Map<String, Member> map = new HashMap<String, Member>();
			Member member = new Member();
			Member member2 = new Member();
			
			map.put("key1", member);
			map.put("key2", member2);
			
			return map;
			
		}
		
		
		
		// 200 OK 상태코드 전송
		// Void 타입은 아무런 형태가 아닌데 제네릭타입의 뭔가는 채워야 겠어서 채우는 placeholder같은 느낌이랄까?
		@RequestMapping(value = "/goRestHome0601", method = RequestMethod.GET)
		public ResponseEntity<Void> goRestHome0601(){
			log.info("goRestHome0601() 실행...!");
			/*
			 * 내가 요청한 url로 응답이 나가면서 응답 데이터로 아무런 값이 전달되지 않는다.
			 * 해당 URL요청 후, 브라우저에서 개발자 도구를 이용해서 네트워크 탭의 내역을 확인해보면 응답으로 URL이 나간걸 확인할 수 있는데,
			 * 이때, 상태코드 200으로 정상 응답이 나간걸 확인할 수 있다.
			 * 그리고, 다른 기능으로 아무런 형태 없이 응답으로 나가지만 응답에 대한 header 정보를 변경하고자 할때 사용할 수 있다. 
			 */
			
			return new ResponseEntity<Void>(HttpStatus.OK); //데이터로 인식
			
		}
		
		
		/*
		 * 7. ResponseEntity<String> 타입
		 * 
		 * 	-response 할 때 HTTP 헤더 정보와 문자열 데이터를 전달하는 용도로 사용한다.
		 * 
		 */
		
		//"SUCCESS" 메시지와 200 OK 상태 코드를 전송한다.
		@RequestMapping(value = "/goRestHome0701", method = RequestMethod.GET)
		public ResponseEntity<String> goRestHome0701(){
			log.info("goRestHome0701() 실행...!");
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}
	
		
		
		// 객체의 JSON 타입의 데이터와 200 OK 상캐 코드를 전송한다.
		@ResponseBody
		@RequestMapping(value = "/goRestHome0801",method = RequestMethod.GET)
		public ResponseEntity<Member> goRestHome0801(){
			log.info("goRestHome0801() 실행...!");
			Member member = new Member();
			return new ResponseEntity<Member>(member, HttpStatus.OK);
			
		}
		
		// 객체의 JSON 객체 배열 타입의 데이터와 200 OK 상태코드를 전송한다.
		@RequestMapping(value = "/goRestHome0901", method = RequestMethod.GET)
		public ResponseEntity<List<Member>> goRestHome0901(){
			log.info("goRestHome0901() 실행...!");
			List<Member> list = new ArrayList<Member>();
			Member member = new Member();
			Member member2 = new Member();
			list.add(member);
			list.add(member2);
			
			return new ResponseEntity<List<Member>>(list, HttpStatus.OK);
			
		}
		
		
		@RequestMapping(value = "/goRestHome1001", method = RequestMethod.GET)
		public ResponseEntity<Map<String, Member>> goRestHome1001(){
			log.info("goRestHome1001() 실행...!");
			Map<String, Member> map = new HashMap<String, Member>();
			Member member = new Member();
			Member member2 = new Member();
			map.put("key1",member);
			map.put("key2",member2);
			
			return new ResponseEntity<Map<String,Member>> (map, HttpStatus.OK);
		}
		
		@RequestMapping(value = "/goRestHome1101", method = RequestMethod.GET)
		public ResponseEntity<byte[]> goRestHome1101(){
			log.info("goRestHome1101() 실행...!");
			ResponseEntity<byte[]> entity = null;
			
			InputStream in = null;
			HttpHeaders headers = new HttpHeaders();
			try {
				in = new FileInputStream("D:\\image\\ive.jpg");
				headers.setContentType(MediaType.IMAGE_JPEG);
				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
				
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return entity;
			
		}
		
		
		@RequestMapping(value = "/goRestHome1102", method = RequestMethod.GET)
		public ResponseEntity<byte[]> goRestHome1102() throws IOException{
			log.info("goRestHome1102() 실행...!");
			
			ResponseEntity<byte[]> entity = null;
			
			InputStream in = null;
			
			String fileName = "DDIT_Spring2_goHome1102.jpg"; //저장할 파일 이름
			HttpHeaders header = new HttpHeaders();
			
			try {
				in = new FileInputStream("D:\\image\\ive.jpg");
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				header.add("Content-Disposition", "attachment; filename=\""+new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), header, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			}finally {
				in.close();
			}
			return entity;
			
			
		}
	

}
