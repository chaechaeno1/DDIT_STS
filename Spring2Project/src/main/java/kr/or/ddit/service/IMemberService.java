package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.crud.CrudMember;

public interface IMemberService {

	void register(CrudMember member);

	List<CrudMember> list();

}
