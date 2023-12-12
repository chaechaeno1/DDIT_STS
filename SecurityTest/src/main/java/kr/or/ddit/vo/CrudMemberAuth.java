package kr.or.ddit.vo;


public class CrudMemberAuth {
	private int userNo; //sql디벨로퍼에서 외래키로 지정해주었음!
	private String auth;
	
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
	
	
}
