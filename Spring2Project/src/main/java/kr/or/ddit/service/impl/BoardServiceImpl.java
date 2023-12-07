package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IBoardMapper;
import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements IBoardService {

	@Inject
	private IBoardMapper mapper;
	
	@Override
	public void register(Board board) {
		log.info("BoardServiceImpl register 실행...!");
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
		log.info("BoardServiceImpl remove 실행...!");
		mapper.delete(boardNo);
		
	}

	@Override
	public List<Board> search(Board board) {
		return mapper.search(board);
	}

}
