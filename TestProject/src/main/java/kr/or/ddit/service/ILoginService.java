package kr.or.ddit.service;

import kr.or.ddit.ServiceResult;

public interface ILoginService {

	public ServiceResult idCheck(String string);

	public ServiceResult nickNameCheck(String string);

}
