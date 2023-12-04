package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IItemMapper3;
import kr.or.ddit.service.IItemService3;
import kr.or.ddit.vo.Item3;

@Service
public class ItemServiceImpl3 implements IItemService3 {

	@Inject
	private IItemMapper3 mapper;
	
	@Override
	public void register(Item3 item) {
		//일반적인 데이터 insert
		mapper.create(item);
		
		//일반적인 데이터를 담고 있는 Item3 데이터 안에 파일 데이터가 들어있는데
		//넘겨받은 파일 데이터를 파일 테이블에 넣는다 
		
		String[] files = item.getFiles();
		if(files == null) { //만약 파일이 전송되지 않았을 경우, files는 null일 수 있으므로 null 체크를 수행합니다.
			return;
		}
		
		for(String fileName : files) { //파일 이름 배열을 순회하며 
			mapper.addAttach(fileName); //각 파일 이름을 mapper.addAttach(fileName)를 통해 데이터베이스에 등록합니다.
		}
		
	}

	@Override
	public List<Item3> list() {		
		return mapper.list();
	}

	@Override
	public Item3 read(int itemId) {		
		return mapper.read(itemId);
	}

	@Override
	public List<String> getAttach(int itemId) {
		
		return mapper.getAttach(itemId);
	}

	
	
	
	
	
	
}
