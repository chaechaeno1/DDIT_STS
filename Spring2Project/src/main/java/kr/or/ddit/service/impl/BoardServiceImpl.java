package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IBoardMapper;
import kr.or.ddit.service.IBoardSerivce;
import kr.or.ddit.vo.Board;

@Service
public class BoardServiceImpl implements IBoardSerivce {

	@Inject
	private IBoardMapper mapper;
	
	@Override
	public void register(Board board) {
		mapper.create(board);

	}

	@Override
	public List<Board> list() {
		return mapper.list();
	}

	@Override
	public Board read(int boardNo) {
		return mapper.read(boardNo);
	}

	@Override
	public void modify(Board board) {
		mapper.update(board);
		
	}

	@Override
	public void remove(int boardNo) {
		mapper.delete(boardNo);
		
	}

	@Override
	public List<Board> search(Board board) {
		return mapper.search(board);
	}

}
