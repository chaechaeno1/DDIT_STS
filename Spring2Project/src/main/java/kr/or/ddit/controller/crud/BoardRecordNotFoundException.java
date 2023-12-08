package kr.or.ddit.controller.crud;

// 사용자 정의 에러 출력 컨트롤러
public class BoardRecordNotFoundException extends Exception{
	
	//부모인 Exception으로 사용자가 정의한 메시지 전달
	public BoardRecordNotFoundException(String msg) {
		super(msg);
	}

}
