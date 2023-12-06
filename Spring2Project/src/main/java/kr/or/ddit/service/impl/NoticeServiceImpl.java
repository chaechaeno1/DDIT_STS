package kr.or.ddit.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.crud.notice.TelegramSendController;
import kr.or.ddit.mapper.ILoginMapper;
import kr.or.ddit.mapper.INoticeMapper;
import kr.or.ddit.service.INoticeService;
import kr.or.ddit.vo.crud.NoticeFileVO;
import kr.or.ddit.vo.crud.NoticeMemberVO;
import kr.or.ddit.vo.crud.NoticeVO;
import kr.or.ddit.vo.crud.PaginationInfoVO;

@Service
public class NoticeServiceImpl implements INoticeService {

	@Inject
	private INoticeMapper noticeMapper;
	@Inject
	private ILoginMapper loginMapper;
	
	private TelegramSendController tst = new TelegramSendController();
	
	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
	
		return noticeMapper.selectNoticeCount(pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {
	
		return noticeMapper.selectNoticeList(pagingVO);
	}

	@Override
	public ServiceResult insertNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult result = null;
		
		int status = noticeMapper.insertNotice(noticeVO);
		if(status > 0) { //게시글 등록이 성공
			List<NoticeFileVO> noticeFileList =  noticeVO.getNoticeFileList(); 
			try {
				//공지사항 파일 업로드 처리 
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//Telegram Bot API를 이용한 실시간 메세지 처리
			try {
				tst.sendGet("김민채", noticeVO.getBoTitle());
			} catch (Exception e) {
				e.printStackTrace();
			}

			result = ServiceResult.OK;
		}else { //등록 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public NoticeVO selectNotice(int boNo) {
		noticeMapper.incrementHit(boNo);	//게시글 조회수 증가
		return noticeMapper.selectNotice(boNo);	// 게시글 번호에 해당하는 게시글 정보 가져오기
	}

	@Override
	public ServiceResult updateNotice(HttpServletRequest req, NoticeVO noticeVO) {
		ServiceResult result= null;
		int status = noticeMapper.updateNotice(noticeVO); //게시글 수정
		if(status >0) { //게시글 수정 완료
			
			// 게시글 정보에서 파일 목록 가져오기
			List<NoticeFileVO> noticeFileList = noticeVO.getNoticeFileList();
			
			try {
				//공지사항 파일 업로드
				noticeFileUpload(noticeFileList, noticeVO.getBoNo(), req);
				
				//기존에 등록되어 있는 파일 목록들 중, 수정하기 위해서 X버튼을 눌러 삭제 처리로 남겨준 파일 번호들
				Integer[] delNoticeNo = noticeVO.getDelNoticeNo();
				if(delNoticeNo != null) {
					for(int i = 0; i < delNoticeNo.length; i++) {
						//삭제할 파일 번호 목록들 중, 파일 번호에 해당하는 공지사항 파일 정보를 가져온다.
						NoticeFileVO noticeFileVO = noticeMapper.selectNoticeFile(delNoticeNo[i]);
						noticeMapper.deleteNoticeFile(delNoticeNo[i]); //파일번호에 해당하는 파일 데이터를 삭제
						
						// 데이터베이스에서 데이터 삭제했다면 실제로 업로드된 경로에 있는 파일도 삭제되어야 하기 때문에 필요
						File file = new File(noticeFileVO.getFileSavepath()); 
						file.delete(); // 기존 파일이 업로드 되어있던 경로에 파일 삭제
						
						
						
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			result=ServiceResult.OK;
		}else {//등록 실패
			result = ServiceResult.FAILED;
			
		}
		
		return result;
	}

	@Override
	public ServiceResult deleteNotice(HttpServletRequest req, int boNo) {
		ServiceResult result= null;
		
		//공지사항 파일 데이터를 삭제하기 위한 준비(파일 적용시)
		NoticeVO noticeVO = noticeMapper.selectNotice(boNo);	//게시글 번호에 해당하는 공지사항 게시글 정보 가져오기
		noticeMapper.deleteNoticeFileByBoNo(boNo);			// 게시글 번호에 해당하는 파일 데이터 삭제
		
		int status = noticeMapper.deleteNotice(boNo); //일반적인 게시글 삭제
		if(status >0) { //등록 성공
			List<NoticeFileVO> noticeFileList =  noticeVO.getNoticeFileList();
			if(noticeFileList != null && noticeFileList.size() > 0) {
				// D:\99_Class....\workspace\.metadata\.plugins....\Project명\resources\notice\boNo 이렇게 경로가 들어있고,
				// boNo 뒤에 나오는 특정 파일이름(234234sfsdfsd23fsdfd.jpg) 을 '/'기준으로 잘라준다.
				String[] filePath = noticeFileList.get(0).getFileSavepath().split("/");
				
				String path = filePath[0];
				deleteFolder(req, path);
			}
			
			
			result=ServiceResult.OK;
		}else {//등록 실패
			result = ServiceResult.FAILED;
			
		}
		
		return result;
	}

	private void deleteFolder(HttpServletRequest req, String path) {
		// UUID + 원본파일명 전 폴더경로를 folder 파일객체로 잡는다.
		File folder = new File(path);
		
		if(folder.exists()) { //경로가 존재한다면
			File[] folderList = folder.listFiles(); //폴더 안에 있는 파일들의 목록을 가져온다.
			
			for(int i = 0; i < folderList.length; i++) {
				if(folderList[i].isFile()) {	//폴더안의 있는 파일이 파일일 때..
					// 폴더 안에 파일을 차례대로 삭제
					folderList[i].delete();
				}else { // 폴더 안에 폴더가 존재할 때
					// 폴더안에 있는 파일이 폴더일 때 재귀함수 호출(폴더 안으로 들어가서 재 처리할 수 있도록)
					deleteFolder(req, folderList[i].getPath());
				}
				
			}
			folder.delete(); //폴더 삭제
			
		}
		
	}

	@Override
	public NoticeMemberVO loginCheck(NoticeMemberVO member) {		
		return loginMapper.loginCheck(member);
	}

	@Override
	public ServiceResult idCheck(String memId) {
		ServiceResult result = null;
		NoticeMemberVO member = loginMapper.idCheck(memId);
		if(member != null) {
			result = ServiceResult.EXIST;
		}else {
			result = ServiceResult.NOTEXIST;
		}
		
		return result;
	}

	@Override
	public ServiceResult signup(HttpServletRequest req, NoticeMemberVO memberVO) {
		//mapper에서는 없던 HttpServletRequest req이 serviceImpl에서 추가됨
		//주로 파일 업로드와 관련
		//파일 업로드를 하기 위해서는 클라이언트에서 전송된 요청(request)에 대한 정보가 필요합니다. 
		//이때 HttpServletRequest를 사용하여 클라이언트의 요청 정보에 접근할 수 있습니다.
		//클라이언트가 업로드한 파일의 정보나 요청에 대한 다양한 속성들을 HttpServletRequest를 통해 얻어와서 
		//파일 업로드와 관련된 작업을 수행하는 것입니다.
		//
		
		ServiceResult result = null; // 메서드 수행 결과를 저장할 변수
		
		//회원가입 시, 프로필 이미지로 파일을 업로드 하는데 이때 업로드 할 서버 경로
		String uploadPath = req.getServletContext().getRealPath("/resources/profile");
		File file = new File(uploadPath); //지정된 업로드 경로에 대한 File 객체를 생성합니다.
		if(!file.exists()) { //업로드 경로에 해당하는 디렉터리가 존재하지 않으면 
			file.mkdirs();   //디렉터리를 생성합니다.
		}
		
		String proFileImg = ""; //회원정보에 추가될 프로필 이미지 경로
		try {
			//넘겨받은 회원 정보에서 파일 데이터 가져오기
			MultipartFile proFileImgFile = memberVO.getImgFile();
			
			//넘겨받은 파일 데이터가 존재할 때 
			if(proFileImgFile.getOriginalFilename() != null && !proFileImgFile.getOriginalFilename().equals("")) {
				String fileName = UUID.randomUUID().toString(); // UUID 파일명 생성
				fileName += "_" + proFileImgFile.getOriginalFilename(); // UUID_원본파일명으로 파일명 생성
				uploadPath += "/" + fileName;	// /resources/profile/uuid_원본파일명
				
				proFileImgFile.transferTo(new File(uploadPath)); // 해당 위치에 파일 복사
				proFileImg = "/resources/profile/" + fileName;		// 파일 복사가 일어난 파일의 위치로 접근하기 위한 URI 설정
			}
			
			memberVO.setMemProfileImg(proFileImg); //회원 객체에 프로필 이미지 경로를 설정
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 회원 등록을 위해 Mapper 호출
		int status = loginMapper.signup(memberVO);
		
		// 등록 성공 여부에 따라 결과값 설정
		if(status > 0 ) { //등록 성공
			result = ServiceResult.OK;
		}else {			// 등록 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	
	
	
	
	
	// 이 메소드는 특정 보드(boNo)와 연결하여 파일 업로드를 처리합니다.
	private void noticeFileUpload(
			List<NoticeFileVO> noticeFileList, // 파일 정보 목록 (NoticeFileVO 객체들의 리스트)
			int boNo, // 파일을 연결할 보드 번호
			HttpServletRequest req // 서블릿 컨텍스트를 얻기 위한 HttpServletRequest 객체
			) throws IOException {
		String savePath = "/resources/notice/"; // 파일이 저장될 기본 경로
		
		if(noticeFileList != null) { //넘겨받은 파일 데이터가 존재할 때
			if(noticeFileList.size() > 0) { // 목록에 파일이 있는지 확인
				for(NoticeFileVO noticeFileVO : noticeFileList) { // 각 파일에 대해 반복
					String saveName = UUID.randomUUID().toString(); //UUID의 랜덤 파일명 생성
					
					//파일명을 설정할 때, 원본 파일명의 공백을 '_'로 변경한다.
					saveName = saveName + "_" + noticeFileVO.getFileName().replaceAll(" ", "_");
					// 디버깅 및 확장자 추출 참고(여기서 활용하진 않음, 디버깅 할때 참고하세요)
					String endFilename = noticeFileVO.getFileName().split("\\.")[1];
					
					//최종 경로
					// 서버에 파일이 저장될 실제 경로 획득
					String saveLocate = req.getServletContext().getRealPath(savePath + boNo);
					
					// 디렉터리가 존재하지 않으면 생성
					File file = new File(saveLocate);
					if(!file.exists()) {
						file.mkdirs();
					}
					
					// 파일 이름을 포함한 완전한 파일 경로
					saveLocate += "/" + saveName;
					
					 // NoticeFileVO 객체에 속성 설정
					noticeFileVO.setBoNo(boNo);					// 게시글 번호 설정
					noticeFileVO.setFileSavepath(saveLocate);	//파일 업로드 경로 설정
					
					// 데이터베이스에 파일 정보를 삽입하기 위한 메소드 호출
					noticeMapper.insertNoticeFile(noticeFileVO);	// 게시글 파일 데이터 추가
					
					// 대상 파일을 나타내는 File 객체 생성
					File saveFile = new File(saveLocate);
					
					// 방법 1: InputStream 및 FileUtils.copyInputStreamToFile 사용
//					InputStream is = noticeFileVO.getItem().getInputStream();
//					FileUtils.copyInputStreamToFile(is, saveFile);
					
					// 방법 2: MultipartFile 객체의 transferTo 메소드 사용
					noticeFileVO.getItem().transferTo(saveFile);		//파일 복사
					// 오류나면 throws 주기 IOException
					
					
				}
			}
			
		}
		
	}

	@Override
	public NoticeFileVO noticeDownload(int fileNo) {
		NoticeFileVO noticeFileVO = noticeMapper.noticeDownload(fileNo);
		if(noticeFileVO == null) {
			throw new RuntimeException();	
		}
		noticeMapper.incrementNoticeDowncount(fileNo);	//다운로드 횟수 증가
		return noticeFileVO;		
	}
	
	
	
	
	
	
	
	
}









