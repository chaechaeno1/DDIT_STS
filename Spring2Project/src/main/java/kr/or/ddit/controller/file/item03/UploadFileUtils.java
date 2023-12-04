package kr.or.ddit.controller.file.item03;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;


// 파일 업로드와 함께 업로드된 이미지에 대한 썸네일 생성까지 수행하는 유틸리티 클래스
public class UploadFileUtils {

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		// UUID 생성
		UUID uuid = UUID.randomUUID();
		
		// UUID_원본파일명 형식으로 저장할 파일명 생성
		String savedName = uuid.toString() + "_" + originalName;
		
		// 2023/12/04 폴더 경로를 만들고, /2023/12/04 폴더 경로를 리턴한다.
		String savedPath = calcPath(uploadPath);
		
		
		// 배포된 서버 업로드 경로 + /2023/12/04/ + UUID_원본파일명으로 File target을 하나 만들어준다.
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target); // 위에서 만들어진 경로와 파일명을 가지고 파일 복사를 진행한다.
		
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1); //확장차 추출
		
		// \2023\12\04 경로를 '/' 경로로 변경 후 원본 파일명을 붙인다.
		String uploadedFileName = savedPath.replace(File.separatorChar, '/') + "/" + savedName;
		
		//확장자가 이미지 파일이면 s_가 붙은 파일의 썸네일 이미지 파일을 생성한다.
		if(MediaUtils.getMediaType(formatName) != null) {	// 확장자를 통한 이미지 파일인지 검증 (새로 class 만들었음)
			makeThumnail(uploadPath, savedPath, savedName); // 썸네일 이미지 생성
			
			
		}
		
		return uploadedFileName;
	
	}
	
	
	//썸네일 이미지 만들기
	private static void makeThumnail(String uploadPath, String path, String fileName) throws Exception {
		// 썸네일 이미지를 만들기 위해 원본 이미지를 읽는다.
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		
		// 썸네일 이미지를 만들기 위한 설정
		// Method.AUTOMATIC : 최소 시간 내에 가장 잘 보이는 이미지를 얻기 위한 사용 방식
		// Mode.FIT_TO_HEIGTH : 이미지 방향과 상관없이 주어진 높이 내에서 가장 잘 맞는 이미지로 계산
		// targetSize :  값 100, 정사각형 사이즈로 100x100 
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		// 업로드 한 원본 이미지를 가지고 's_'를 붙여서 임시 파일로 만들기 위해 썸네일 경로 + 파일명을 작성한다.
		// uploadPath는 업로드된 파일이 저장된 기본 경로, 
		// path는 년/월/일 형식의 하위 경로, 
		// File.separator는 파일 경로의 구분자 (슬래시 또는 역슬래시)를 의미합니다. 
		// 따라서 thumbnailName은 썸네일 파일의 전체 경로를 나타냅니다. 
		// "s_" + fileName은 썸네일 파일명에 's_'를 붙인 것입니다.
		String thumnailName = uploadPath + path + File.separator + "s_" + fileName;
		
		
		// File 클래스를 사용하여 위에서 생성한 경로로 newfile이라는 객체를 만듭니다. 
		// 이 객체는 실제로 파일을 나타냅니다.
		File newfile = new File(thumnailName);
		
		// 파일명에서 확장자를 추출한다.
		// fileName에서 마지막 점 (.) 이후의 문자열을 추출하여 확장자를 얻습니다. 
		// 이것은 나중에 이미지를 저장할 때 사용됩니다.
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 's_'가 붙은 썸네일 이미지를 만든다.
		// ImageIO.write 메서드를 사용하여 destImg (썸네일 이미지)를 파일로 저장합니다. 
		// 여기서 formatName.toUpperCase()는 이미지의 확장자를 대문자로 변환하여 사용합니다. 
		// 이를 통해 이미지 포맷에 따라 올바른 파일 확장자를 사용할 수 있습니다.
		ImageIO.write(destImg, formatName.toUpperCase(), newfile);
		
	}
	
	
	
	// /2023/12/04/ 경로 생성
	private static String calcPath(String uploadPath) {
		Calendar cal =  Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR); // 2023
		
		//DecimalFormat("00") : 두 자리에서 빈자리는 0으로 채움
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1); // 2023/12
		String dataPath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE)); // 2023/12/04
		
		//년월일 폴더 구조에 의한 폴더 생성
		makeDir(uploadPath, yearPath, monthPath, dataPath);
		return dataPath;
		
	
	
	}
	
	//가변인자
	// 키워드 '...'를 사용한다.
	// [사용법] 타입...변수명 형태로 사용
	// 순서대로 yearPath, monthPath, dataPath가 배열로 들어가 처리
	private static void makeDir(String uploadPath, String ...paths) {
		// /2023/12/04 폴더 구조가 존재한다면 return
		// 만들려던 폴더 구조가 이미 만들어져 있는 형태니까 return
		if(new File(paths[paths.length - 1]).exists()) {
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			
			// /2023/12/04 와 같은 경로에 각 폴더가 없으면 각각 만들어준다.
			if(!dirPath.exists()) {
				dirPath.mkdirs();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
