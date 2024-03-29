package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.BoardFileVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IBoardMapper {

	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO);

	public List<BoardVO> selectBoardList(PaginationInfoVO<BoardVO> pagingVO);

	public void incrementHit(int boNo);

	public BoardVO selectBoard(int boNo);

	public int insertBoard(BoardVO boardVO);

	public void insertBoardFile(BoardFileVO boardFileVO);

	public BoardFileVO selectFileInfo(int fileNo);

	public void deleteBoardFile(int boNo);

	public int deleteBoard(int boNo);

	public int updateBoard(BoardVO boardVO);

	public void deleteBoardFileList(Integer[] delBoardNo);

}
