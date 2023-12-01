package kr.or.ddit.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IItemMapper;
import kr.or.ddit.service.IItemService;
import kr.or.ddit.vo.Item;

@Service
public class ItemServiceImpl implements IItemService {

	@Inject
	private IItemMapper mapper;
	
	@Override
	public void register(Item item) {
		mapper.create(item);


	}

}
