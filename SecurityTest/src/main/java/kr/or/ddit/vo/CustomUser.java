package kr.or.ddit.vo;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


// Spring Security의 User 클래스를 상속하고, 사용자 정보를 담은 CrudMember 클래스를 기반으로 한 CustomUser 클래스를 정의
public class CustomUser extends User{
	
	//멤버변수선언
	private CrudMember member;
	
	//생성자1: 사용자명(username), 비밀번호(password), 권한(authorities)을 받아서 부모 클래스인 User의 생성자를 호출합니다.
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	//생성자2: CrudMember 객체를 받아서 해당 객체의 정보를 이용하여 User 클래스의 생성자를 호출합니다. 
	// member.getAuthList()를 통해 회원이 가지고 있는 권한 정보를 스트림으로 변환
	// 각 권한을 SimpleGrantedAuthority로 매핑하여 권한 목록을 생성
	public CustomUser(CrudMember member) {
		//Java 스트림을 사용한 경우(람다 표현식)
		// - 자바 버전 8부터 추가된 기능
		// map : 컬렉션(List, Map, Set 등), 배열 등의 설정되어있는 각 타입의 값들을 하나씩 참조하여 람다식으로 반복 처리할 수 있게 해준다.
		// collect : Stream()을 돌려 발생되는 데이터를 가공 처리하고 원하는 형태의 자료형으로 변환을 돕는다.
		// 회원정보안에 들어있는 역할명들을 컬렉션 형태의 스트림으로 만들어서 보내준다.
		// ** 람다표현식은 복잡한 메서드 라인을 간단한 표현식으로 출력할 수 있다는 장점이 있는 대신 디버깅을 할 수 없다는 단점이 있다.
		// User 클래스의 생성자에 필요한 사용자 정보를 제공하는 역할
		super(member.getUserId(), //사용자 아이디
				member.getUserPw(), // 사용자 비밀번호
				member.getAuthList() //사용자 권한 목록
				.stream(). // 권한 목록 스트림으로 변환
				map(auth -> new SimpleGrantedAuthority(auth.getAuth())) //각 권한을 SimpleGrantedAuthority 객체로 변환
				.collect(Collectors.toList())); //매핑된 권한을 리스트로 수집
		this.member = member;

	}
	//게터세터 지정
	//member 변수의 Getter와 Setter를 정의합니다. 
	//이를 통해 외부에서 member 변수에 접근하고 수정할 수 있도록 합니다.
	public CrudMember getMember() {
		return member;
	}

	public void setMember(CrudMember member) {
		this.member = member;
	}

}
