package kr.or.ddit.controller.test.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.test.service.ITagBoardService;
import kr.or.ddit.mapper.ITagBoardMapper;
import kr.or.ddit.vo.crud.PaginationInfoVO;
import kr.or.ddit.vo.test.TagBoardVO;
import kr.or.ddit.vo.test.TagVO;

@Service
public class TagBoardServiceImpl implements ITagBoardService {

	@Inject
	private ITagBoardMapper mapper;
	

	@Override
	public int selectTagBoardCount(PaginationInfoVO<TagBoardVO> pagingVO) {
		
		return mapper.selectTagBoardCount(pagingVO);
	}

	@Override
	public List<TagBoardVO> selectTagBoardList(PaginationInfoVO<TagBoardVO> pagingVO) {
		
		return mapper.selectTagBoardList(pagingVO);
	}

	@Override
	public ServiceResult insertTagBoard(TagBoardVO tagBoardVO) {
		ServiceResult result = null;
		int status = mapper.insertTagBoard(tagBoardVO);
		if(status > 0) { //등록 성공
			result = ServiceResult.OK;
		}else { //등록 실패
			result = ServiceResult.FAILED;	
		}
		return result;
	}
	
	

	@Override
	public TagBoardVO selectTagBoard(int boNo) {
		mapper.incrementHit(boNo);	//게시글 조회수 증가
		return mapper.selectTagBoard(boNo);	// 게시글 번호에 해당하는 게시글 정보 가져오기
	}

	@Override
	public ServiceResult updateTagBoard(TagBoardVO tagBoardVO) {
		ServiceResult result= null;
		int status = mapper.updateTagBoard(tagBoardVO);
		if(status >0) { //등록 성공
			result=ServiceResult.OK;
		}else {//등록 실패
			result = ServiceResult.FAILED;
			
		}
		
		return result;
	}

	@Override
	public ServiceResult deleteTagBoard(int boNo) {
		ServiceResult result= null;
		int status = mapper.deleteTagBoard(boNo);
		if(status >0) { //등록 성공
			result=ServiceResult.OK;
		}else {//등록 실패
			result = ServiceResult.FAILED;
			
		}
		
		return result;


	}
	
}
