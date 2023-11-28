package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Member {
	
	//입력값 검증 규칙을 지정한다.
	
	//문자열이 null이 아니고 trim한 길이가 0보다 크다는 것을 검사한다.
	//message로 설정하여 내가 원하는 오류 문구 출력 가능
	@NotBlank(message = "아이디를 입력해주세요! X_X")
	private String userId = "a001";
	

	@NotBlank(message = "이름를 입력해주세요! X_X")
	@Size(max=3)
	private String userName = "hongkd";
	
	@NotBlank(message = "비밀번호를 입력해주세요! X_X")
	@Length(min=4,max=8,message = "비밀번호는 최소 4자리, 최대 8자리까지 입력가능합니다. 다시입력해주세요.")
	private String password = "1234";
	
	private int coin = 100;
	
	//과거 날짜인지를 검사한다.
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	//이메일 주소 형식인지를 검사한다.
	@Email
	private String email;
	
	private String gender;
	private String hobby;
	private String[] hobbyArray;
	private List<String> hobbyList;
	private boolean foreigner;
	private String developer;
	private String nationality;
	
	//중첩된 자자빈즈의 입력값 검증을 지정한다.
	@Valid
	private Address address;
	
	// 자바빈즈 컬렉션의 입력값 검증을 지정한다.
	@Valid
	private List<Card> cardList;
	
	private String cars;
	private String[] carArray;
	private List<String> carList;
	private String introduction;
	private String birthDay;
	
	
}
