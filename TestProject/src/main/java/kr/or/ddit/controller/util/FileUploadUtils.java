package kr.or.ddit.controller.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.mapper.IBoardMapper;
import kr.or.ddit.vo.BoardFileVO;

public class FileUploadUtils {

	public static void boardFileUpload(List<BoardFileVO> boardFileList, int boNo, HttpServletRequest req,
			IBoardMapper mapper) throws Exception {
		
		String savePath = "/resources/board/";
		
		if(boardFileList != null && boardFileList.size() > 0 ) {
			for(BoardFileVO boardFileVO : boardFileList) {
				String saveName = UUID.randomUUID().toString();
				saveName = saveName + "_" + boardFileVO.getFileName().replaceAll(" ", "_"); //공백문자를 언더바로..
				
				//업로드 서버 경로  +  /resources/board/10
				String saveLocate = req.getServletContext().getRealPath(savePath + boNo);
				
				File file = new File(saveLocate);
				
				if(!file.exists()) {
					file.mkdirs();
				}
				
				// saveLocate + "/" + UUID_원본파일명
				saveLocate += "/" + saveName;
				boardFileVO.setBoNo(boNo);					// 게시글 번호 설정
				boardFileVO.setFileSavepath(saveLocate);	// 파일 업로드 경로 설정
				mapper.insertBoardFile(boardFileVO);		// 공지사항 게시글 파일 데이터 추가
				
				
				File saveFile = new File(saveLocate);
				boardFileVO.getItem().transferTo(saveFile);; //파일복사
				
				
				
			}
			
		}
		
	}

}
