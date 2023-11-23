package kr.or.ddit.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	
	private String userName = "hongkd";
	private String password = "1234";
	private String userId = "a001";
	private int coin = 100;
	private Date dateOfBirth;
	
	
}
