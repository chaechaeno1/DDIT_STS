package kr.or.ddit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

//Spring Security PasswordEncoder 인터페이스를 구현한 사용자 정의 클래스
public class CustomNoOpPasswordEncoder implements PasswordEncoder{ // 암호화 처리기를 사용하지 않겠다!!
	
	// 메시지 로깅을 위한 Logger
	private static final Logger log = LoggerFactory.getLogger(CustomNoOpPasswordEncoder.class);

	// PasswordEncoder 인터페이스의 encode 메서드 구현
	@Override
	public String encode(CharSequence rawPassword) { 
		// 암호화 전에 원시 비밀번호를 로깅
		log.info("before encode : "+rawPassword);
		// 사용자 정의 구현은 실제로 어떤 암호화도 수행하지 않으며,
		// 단순히 원시 비밀번호를 문자열로 반환함
		return rawPassword.toString();
	}

	// PasswordEncoder 인터페이스의 matches 메서드 구현
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) { //넘겨받은 비밀번호와 해쉬값이 일치하는지
		// 비교를 위해 원시 비밀번호와 암호화된 비밀번호를 로깅
		log.info("matches : " + rawPassword + " : : : : " + encodedPassword);
		// 사용자 정의 구현은 원시 비밀번호를 문자열로 변환한 값이
		// 제공된 암호화된 비밀번호와 동일한지 확인함
		return rawPassword.toString().equals(encodedPassword);
	}

}
