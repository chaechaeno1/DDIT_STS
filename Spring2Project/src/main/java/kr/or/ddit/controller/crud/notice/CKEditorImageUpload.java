package kr.or.ddit.controller.crud.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class CKEditorImageUpload {

	// CKEditor 본문 내용에 이미지 업로드하기
	@RequestMapping(value = "/imageUpload.do")
	public String imageUpload(
			HttpServletRequest req, HttpServletResponse resp,
			MultipartHttpServletRequest multiFile
			) throws Exception {
		
		//CKEditor4 특정 버전 이후부터 html 형식의 데이터를 리턴하는 방법에서 JSON 데이터를 구성해서
		// 리턴하는 방식으로 변경됨
		JsonObject json = new JsonObject(); //JSON 객체를 만들기 위한 준비
		PrintWriter printWriter = null;		//외부 응답으로 내보낼 때 사용할 객체
		OutputStream out = null; 			//본문 내용에 추가한 이미지를 파일로 생성할 객체
		long limitSize = 1024 * 1024 * 2; 	//업로드 파일 최대 크기(2MB)
			
		//CKEditor 본문 내용에 이미지를 업로드 해보면 'upload'라는 키로 파일 데이터가 전달되는 것을 확인할 수 있다.
		//'upload'라는 키로 MultipartFile 타입의 파일 데이터를 꺼낸다.
		MultipartFile file=  multiFile.getFile("upload");
		
		// 파일 객체가 null이 아니고, 파일 사이즈가 0보다 크고, 파일명이 공백이 아닌 경우는 무조건 파일 데이터가 존재하는 경우
		if(file != null && file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
			// 데이터 Mime 타입이 'image/'를 포함한 이미지 파일인지 체크
			// MIME : Multipurpose Internet Mail Extensions의 약자로 간략히 말씀을 드리면 파일 변환을 뜻
			// startsWith는 문자열이 주어진 접두사로 시작하는지 여부를 확인하는 JavaScript 문자열 메서드
			if(file.getContentType().toLowerCase().startsWith("image/")) {
				if(file.getSize() > limitSize) { //업로드한 파일 사이즈가  최대 크기(2MB)보다 클 때
					/*
					 * {
					 * 		"uploaded" : 0,
					 * 		"error"	   : [
					 * 			{
					 * 				"message" : "2MB미만의 이미지만 업로드 가능합니다."
					 * 			}
					 * 
					 * 		]
					 * }
					 * 
					 * 에러가 발생했을 때 출력 형태를 위와 같은 형식으로 만든다.
					 * 
					 */
					
					// JsonObject와 JsonArray 객체 생성
					JsonObject jsonMsg = new JsonObject();
					JsonArray jsonArr = new JsonArray();
					// "message" 속성을 가진 JsonObject 생성 및 값 설정
					jsonMsg.addProperty("message", "2MB미만의 이미지만 업로드 가능합니다.");
					// 위에서 생성한 JsonObject를 JsonArray에 추가
					jsonArr.add(jsonMsg);
					// json은 새로운 JsonObject
					// "uploaded" 속성을 0으로 설정
					json.addProperty("uploaded", 0);
					// "error" 속성에 앞서 생성한 JsonArray의 첫 번째 항목을 추가
					json.add("error", jsonArr.get(0));
					
					// 위 형식의 Json 데이터를 출력한다.
					resp.setCharacterEncoding("UTF-8"); //  응답(Response) 객체의 문자 인코딩을 UTF-8로 설정
					printWriter = resp.getWriter();
					printWriter.println(json);
					
					// 이 코드는 "uploaded" 값이 0이며 "error"에는 "2MB미만의 이미지만 업로드 가능합니다."라는 
					// 메시지를 포함하는 JSON 데이터를 생성하고, 이를 HTTP 응답으로 출력하는 용도로 사용됩니다.
					
					
					
				} else { //정상 크기 범위의 이미지 파일 일 때
					try {
						String fileName = file.getName(); //파일명 얻어오기
						byte[] bytes = file.getBytes();	//파일 데이터 얻어오기
						
						//업로드 경로 설정
						String uploadPath = req.getServletContext().getRealPath("/resources/img"); //업로드 경로 설정
						
						//업로드 경로로 설정된 폴더구조가 존재하지 않는 경우, 파일을 복사할 수 없으므로
						//폴더 구조가 존재하지 않는 경우 생성하고 존재하는 경우 건너뛰도록 한다.
						File uploadFile = new File(uploadPath);
						if(!uploadFile.exists()) { //폴더가 존재하지 않다면!!!!
							uploadFile.mkdirs();
						}
						
						// 위에서 얻어온 파일 이름을 기존 파일명으로 사용해도 되고
						// UUID로 설정해서 사용해도 됨
						// 범용 고유 식별자(Universally Unique Identifier, UUID) 
						// 해당 타입의 다른 모든 리소스 중에서 리소스를 고유하게 식별하는 데 사용되는 레이블
						fileName = UUID.randomUUID().toString(); //UUID의 random 파일명을 생성한다.
						uploadPath = uploadPath + "/" + fileName + ".jpg"; // 업로드 경로 + 파일명
						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes); //파일 복사
						
						printWriter = resp.getWriter();
						//복사된 이미지 파일의 서버 업로드 경로를 url로 접근하기 위한 경로 설정
						String fileUrl = req.getContextPath() + "/resources/img/" + fileName + ".jpg";
						
						
						/*
						 * {
						 * 		"uploaded" : 1,
						 * 		"fileName" : "XXXXXXXXX-XXXXXXXXXXX.jpg",
						 * 		"url" : "/resources/img/XXXXXXXXX-XXXXXXXX.jpg"
						 * }
						 * 
						 * 출력 형태를 위와 같은 형식으로 만든다.
						 * 
						 */
						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);
						
						//위 형식의 JSON 데이터를 출력한다.
						printWriter.println(json);
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						if(out != null) out.close();
						if(printWriter != null) printWriter.close();
						
					}

					
				}
				
			}
		}
		return null;
		
	}
	
}
