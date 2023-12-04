package kr.or.ddit.controller.test.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.crud.PaginationInfoVO;
import kr.or.ddit.vo.test.TagBoardVO;

public interface ITagBoardService {

	public int selectTagBoardCount(PaginationInfoVO<TagBoardVO> pagingVO);

	public List<TagBoardVO> selectTagBoardList(PaginationInfoVO<TagBoardVO> pagingVO);

	public ServiceResult insertTagBoard(TagBoardVO tagBoardVO);

	public TagBoardVO selectTagBoard(int boNo);

	public ServiceResult updateTagBoard(TagBoardVO tagBoardVO);

	public ServiceResult deleteTagBoard(int boNo);


}
