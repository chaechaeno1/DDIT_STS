package kr.or.ddit.vo.crud;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeFileVO {
	
	private MultipartFile item;
	private Integer boNo;
	private Integer fileNo;
	private String fileName;
	private long fileSize;
	private String fileFancySize; 
	private String fileMime;
	private String fileSavepath;
	private int fileDowncount;

	public NoticeFileVO() {}
	
	public NoticeFileVO(MultipartFile item) {
		this.item = item;
		this.fileName = item.getOriginalFilename();
		this.fileSize = item.getSize();
		this.fileMime = item.getContentType();
		this.fileFancySize = FileUtils.byteCountToDisplaySize(fileSize);

	}

}
