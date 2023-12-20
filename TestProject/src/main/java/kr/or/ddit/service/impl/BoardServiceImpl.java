package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.util.FileUploadUtils;
import kr.or.ddit.mapper.IBoardMapper;
import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.BoardFileVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Service
public class BoardServiceImpl implements IBoardService {
	
	
	@Inject
	private IBoardMapper mapper;

	@Override
	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO) {		
		return mapper.selectBoardCount(pagingVO);
	}

	@Override
	public List<BoardVO> selectBoardList(PaginationInfoVO<BoardVO> pagingVO) {
		
		return mapper.selectBoardList(pagingVO);
	}

	@Override
	public BoardVO selectBoard(int boNo) {
		mapper.incrementHit(boNo); // 조회수 증가
		return mapper.selectBoard(boNo);
	}

	@Override
	public ServiceResult insertBoard(HttpServletRequest req, BoardVO boardVO) throws Exception {
		ServiceResult result = null;
		int status = mapper.insertBoard(boardVO);
		
		if(status > 0) {
			List<BoardFileVO> boardFileList = boardVO.getBoardFileList();
			FileUploadUtils.boardFileUpload(boardFileList, boardVO.getBoNo(), req, mapper);
			result = ServiceResult.OK;			
		}else {
			result = ServiceResult.FAILED;
		}		
		return result;
	}

	@Override
	public BoardFileVO selectFileInfo(int fileNo) {		
		return mapper.selectFileInfo(fileNo);
	}

	@Override
	public ServiceResult deleteBoard(int boNo) {
		ServiceResult result = null;
		mapper.deleteBoardFile(boNo);
		int status = mapper.deleteBoard(boNo);
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult updateBoard(HttpServletRequest req, BoardVO boardVO) throws Exception {
		ServiceResult result = null;
		int status =  mapper.updateBoard(boardVO);
		if(status > 0 ) { //수정 성공
			List<BoardFileVO> boardFileList = boardVO.getBoardFileList();
			//파일 업로드 진행
			FileUploadUtils.boardFileUpload(boardFileList, boardVO.getBoNo(), req, mapper);
			
			Integer[] delBoardNo = boardVO.getDelBoardNo();
			if(delBoardNo != null) {
				// 넘겨받은 배열 형태의 boardNo 집합 데이터를 삭제 처리하기 위해 전달
				mapper.deleteBoardFileList(delBoardNo); // 파일 번호에 해당하는 파일 데이터를 삭제
			}
			result = ServiceResult.OK;
			
		}else { //수정 실패
			result = ServiceResult.FAILED;
			
		}
		

		return result;
	}

	
	
	
	
	
	
	
}
