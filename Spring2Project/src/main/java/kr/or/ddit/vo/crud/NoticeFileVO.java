package kr.or.ddit.vo.crud;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeFileVO {
	
	private MultipartFile item;
	private Integer boNo;
	private Integer fileNo;
	private String fileName;
	private long fileSize;
	private String fileFancysize;
	private String fileMime;
	private String fileSavepath;
	private int fileDowncount;

}
