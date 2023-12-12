package kr.or.ddit.mapper;

import kr.or.ddit.vo.crud.NoticeMemberVO;

public interface ILoginMapper {

	public NoticeMemberVO loginCheck(NoticeMemberVO member);

	public NoticeMemberVO idCheck(String memId);

	public int signup(NoticeMemberVO memberVO);

	public String findId(NoticeMemberVO memberVO);

	public String findPw(NoticeMemberVO memberVO);

	public NoticeMemberVO readByUserId(String username);

	public void signupAuth(int memNo);

}
