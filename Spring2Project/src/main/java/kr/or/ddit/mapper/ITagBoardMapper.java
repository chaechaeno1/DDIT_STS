package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.crud.PaginationInfoVO;
import kr.or.ddit.vo.test.TagBoardVO;
import kr.or.ddit.vo.test.TagVO;

public interface ITagBoardMapper {

	public int selectTagBoardCount(PaginationInfoVO<TagBoardVO> pagingVO);

	public List<TagBoardVO> selectTagBoardList(PaginationInfoVO<TagBoardVO> pagingVO);

	public int insertTagBoard(TagBoardVO tagBoardVO);

	public void incrementHit(int boNo);

	public TagBoardVO selectTagBoard(int boNo);

	public int updateTagBoard(TagBoardVO tagBoardVO);

	public int deleteTagBoard(int boNo);

	public int insertTag(TagVO tagVO);

	public void deleteTag(int boNo);


}
