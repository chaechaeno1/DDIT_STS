package kr.or.ddit.controller.test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.test.service.ITagBoardService;
import kr.or.ddit.vo.Board;
import kr.or.ddit.vo.crud.NoticeVO;
import kr.or.ddit.vo.crud.PaginationInfoVO;
import kr.or.ddit.vo.test.TagBoardVO;
import kr.or.ddit.vo.test.TagVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/tag")
public class TagBoardController {
	
	@Inject
	private ITagBoardService service;

	// 태그 게시판 목록 
	//method 설정안하면 get과 post 둘 다 받을 수 있는 메서드
	@RequestMapping(value="/list.do")
	public String tagList(
			@RequestParam(name="page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model
			) {
		
		log.info("tagList() 실행..!");
		PaginationInfoVO<TagBoardVO> pagingVO = new PaginationInfoVO<TagBoardVO>();

		//검색기능 추가
		//검색했을 때의 조건은 키워드(searchWord)가 넘어왔을 때 정확하게 검색을 진행하거니까
		//이때, 검색을 진행하기 위한 타입과 키워드를 PaginationInfoVO에 셋팅하고 목록을 조회하기 위한 조건으로
		//쿼리를 조회할 수 있도록 보내준다.
		if(StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}
		
		//현재 페이지 전달 후, start/endRow와 start/endPage 설정
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord =  service.selectTagBoardCount(pagingVO); //총 게시글 수 가져오기
		pagingVO.setTotalRecord(totalRecord);
		List<TagBoardVO> dataList = service.selectTagBoardList(pagingVO);
		pagingVO.setDataList(dataList);		
		model.addAttribute("pagingVO", pagingVO);
		
		return "test/list";
	}		
		
	// 태그 게시판 등록 화면
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String tagForm() {
		log.info("tagForm() 실행..!");
		return "test/form";
	}
	
	// 태그 게시판 등록 기능
	@RequestMapping(value = "/insert.do", method=RequestMethod.POST)
	public String tagBoardInsert(TagBoardVO tagBoardVO, Model model, RedirectAttributes ra) { // RedirectAttributes 일회성 메시지를 전달
		String goPage = ""; //이동할 페이지 정보
		
		//넘겨받은 데이터 검증 후, 에러가 발생한 데이터에 대한 에러정보를 담을 공간
		Map<String, String> errors = new HashMap<String, String>();
		
		//제목 데이터가 누락되었을 때 에러 정보 저장(라이브러리 추가 후 작성)
		if(StringUtils.isBlank(tagBoardVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요.");			
		}
		//내용 데이터가 누락되었을 때 에러 정보 저장
		if(StringUtils.isBlank(tagBoardVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요.");			
		}
		// 기본 데이터의 누락정보에 따른 에러 정보 갯수로 분기 처리
		if(errors.size() > 0) { // 에러 갯수가 0보다 클 때 (에러가 존재)
			model.addAttribute("errors", errors);
			model.addAttribute("tagBoardVO", tagBoardVO);
			goPage = "test/form";	
			
		}else {	//에러가 없을 때
			//로그인 처리를 하지 않고 게시글을 작성 하므로 작성자를 하드코딩한다.
			tagBoardVO.setBoWriter("a001");
			ServiceResult result =  service.insertTagBoard(tagBoardVO);
			
			if(result.equals(ServiceResult.OK)) { //등록 성공
				goPage = "redirect:/board/tag/detail.do?boNo="+tagBoardVO.getBoNo();
				ra.addFlashAttribute("message", "게시글 등록이 완료되었습니다.");
				
			}else {	//등록 실패
				model.addAttribute("tagBoardVO", tagBoardVO);
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				goPage = "test/form";
			}
			
		}
		
		return goPage;
		

	} //insert 끝
	
	
	//상세보기 불러오기
	@RequestMapping(value = "/detail.do", method=RequestMethod.GET)
	public String tagBoardDetail(int boNo, Model model) {
		log.info("tagDetail() 실행..!");
		TagBoardVO tagBoardVO =  service.selectTagBoard(boNo);
		model.addAttribute("tagBoardVO", tagBoardVO);		
		return "test/detail";
		
	}
	
	//수정화면
	@RequestMapping(value = "/update.do", method=RequestMethod.GET)
	public String tagBoardUpdateForm(int boNo, Model model) {
		TagBoardVO tagBoardVO = service.selectTagBoard(boNo);
		List<TagVO> tagList = tagBoardVO.getTagList();
		String tag = "";
		for (int i = 0; i < tagList.size(); i++) {
			tag += tagList.get(i).getTagName() + " ";
		}
		tagBoardVO.setTag(tag);
		
		model.addAttribute("tagBoardVO", tagBoardVO);
		model.addAttribute("status", "u"); // '수정입니다' flag
		return "test/form";

	}
	
	
	//수정 기능 담당
	@RequestMapping(value = "/update.do", method=RequestMethod.POST)
	public String tagBoardUpdate(TagBoardVO tagBoardVO, Model model, RedirectAttributes ra) {
		String goPage = "";
		
		ServiceResult result =  service.updateTagBoard(tagBoardVO);
		
		if(result.equals(ServiceResult.OK)) { //수정 성공
			goPage = "redirect:/board/tag/detail.do?boNo="+tagBoardVO.getBoNo();
			ra.addFlashAttribute("message", "게시글 수정이 완료되었습니다!");
		}else {	//수정 실패
			model.addAttribute("tagBoardVO", tagBoardVO);
			model.addAttribute("message", "수정에 실패했습니다...");
			model.addAttribute("status", "u");
			goPage="test/form";
		
		}
		
		
		return goPage;
		
	}
	
	
	
	@RequestMapping(value = "/delete.do", method=RequestMethod.POST)
	public String tagBoardDelete(int boNo, Model model, RedirectAttributes ra) {
		String goPage ="";
		
		ServiceResult result =  service.deleteTagBoard(boNo);
		if(result.equals(ServiceResult.OK)) { //삭제 성공
			goPage = "redirect:/board/tag/list.do";
			ra.addFlashAttribute("message", "게시글 삭제가 완료되었습니다!");
		}else { //삭제 실패
			goPage = "redirect:/board/tag/detail.do?boNo"+boNo;
			ra.addFlashAttribute("message", "게시글 삭제가 실패했습니다!");	//model로 주지 않는다!
		}		
		return goPage;
		
		
	}
	
	

	
}
	

