package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.crud.CrudMember;
import kr.or.ddit.vo.crud.CrudMemberAuth;

public interface IMemberMapper {

	public void create(CrudMember member);

	public void createAuth(CrudMemberAuth memberAuth);

	public List<CrudMember> list();

}
