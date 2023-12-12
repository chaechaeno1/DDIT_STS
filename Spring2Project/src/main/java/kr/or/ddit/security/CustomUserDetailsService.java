package kr.or.ddit.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.or.ddit.mapper.IMemberMapper;
import kr.or.ddit.vo.CrudMember;
import kr.or.ddit.vo.CustomUser;

public class CustomUserDetailsService implements UserDetailsService {
	
	// 정보를 로깅하기 위한 Logger
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	
	// BCryptPasswordEncoder 및 IMemberMapper를 주입받기 위한 필드
	@Inject
	private BCryptPasswordEncoder bpe;
	
	@Inject
	private IMemberMapper memberMapper;
	
	
	// UserDetailsService 인터페이스에서 제공하는 메서드를 오버라이드하여 사용자 정보를 사용자명으로 가져오기
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername() 실행...!");
		
		// 데모 목적으로 하드코딩된 비밀번호 (실제로는 동적으로 가져와야 함)
		String password = "1234";
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		log.info("#### 암호화된 비밀번호 : " + bpe.encode(password));
		
		// 사용자명을 로깅
		log.info("Load User By Username : " + username);
		
		
		// UserDetailsService를 등록하는 과정에서 우리가 할 목표는 User 객체의 정보와
		// 인증되어 실제로 사용될 내 id에 해당하는 회원정보를 CrudMember에 담고 그 녀석을 UserDetails정보 안에서
		// 가용할 수 있도록 만든다.
		
		// 사용자 정보를 담을 CrudMember 객체
		CrudMember member;
		try {
			// 사용자명에 해당하는 회원 정보를 데이터베이스에서 조회
			member = memberMapper.readByUserId(username);
			log.info("queried by member mapper : " + member);
			// 조회된 회원 정보가 없으면 null 반환, 그렇지 않으면 CustomUser 객체를 반환
			return member == null ? null : new CustomUser(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 예외 발생 시 null 반환
		return null;
	}

}
