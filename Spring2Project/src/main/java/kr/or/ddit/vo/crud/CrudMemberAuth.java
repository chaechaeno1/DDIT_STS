package kr.or.ddit.vo.crud;

import lombok.Data;

@Data
public class CrudMemberAuth {
	private int userNo; //sql디벨로퍼에서 외래키로 지정해주었음!
	private String auth;
	
}
