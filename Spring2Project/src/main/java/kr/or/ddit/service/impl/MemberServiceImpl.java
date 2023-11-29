package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.IMemberMapper;
import kr.or.ddit.service.IMemberService;
import kr.or.ddit.vo.crud.CrudMember;
import kr.or.ddit.vo.crud.CrudMemberAuth;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Inject
	private IMemberMapper mapper;

	@Override
	public void register(CrudMember member) {
		// 회원 1명의 정보를 등록 시, 하나의 권한을 가질 수 있다.
		mapper.create(member);	// 회원정보 1명의 데이터를 등록
		
		// 등록된 회원 정보를 이용해서 권한을 등록
		CrudMemberAuth memberAuth = new CrudMemberAuth();
		memberAuth.setUserNo(member.getUserNo());
		memberAuth.setAuth("ROLE_USER");
		
		mapper.createAuth(memberAuth);
		

	}

	@Override
	public List<CrudMember> list() {
		
		return mapper.list();
	}

}
