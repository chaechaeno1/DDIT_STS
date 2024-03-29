package kr.or.ddit.controller.board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.controller.util.MediaUtils;
import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.BoardFileVO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Inject
	private IBoardService service;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String boardList(@RequestParam(name = "page", required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord, Model model) {
		PaginationInfoVO<BoardVO> pagingVO = new PaginationInfoVO<BoardVO>();

		// 검색을 진행
		if (StringUtils.isNotBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}

		pagingVO.setCurrentPage(currentPage); // startRow, endRow, startPage, endPage가 결정
		int totalRecord = service.selectBoardCount(pagingVO); // totalRecord(총 게시글 수)

		pagingVO.setTotalRecord(totalRecord);
		List<BoardVO> dataList = service.selectBoardList(pagingVO);
		pagingVO.setDataList(dataList);

		model.addAttribute("pagingVO", pagingVO);

		return "board/list";
	}

	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String boardDetail(int boNo, Model model) {
		BoardVO boardVO = service.selectBoard(boNo);
		model.addAttribute("board", boardVO);
		return "board/detail";
	}

	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String boardForm() {
		return "board/form";
	}

	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String boardInsert(HttpServletRequest req, // 보내야할 내용 중 파일도 있으므로!! 추가한 것
			BoardVO boardVO, Model model) throws Exception {

		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		if (StringUtils.isBlank(boardVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요.");
		}
		if (StringUtils.isBlank(boardVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요.");
		}

		if (errors.size() > 0) {
			model.addAttribute("errors", errors);
			model.addAttribute("boardVO", boardVO);
			goPage = "board/form";
		} else {
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("SessionInfo");
			boardVO.setBoWriter(memberVO.getMemId()); // 로그인한 사용자의 id로 작성자를 설정
			ServiceResult result = service.insertBoard(req, boardVO);

			if (result.equals(ServiceResult.OK)) {
				goPage = "redirect:/board/detail.do?boNo=" + boardVO.getBoNo();
			} else {
				model.addAttribute("message", "서버에러, 다시 시도해주세요!");
				goPage = "board/form";
			}

		}
		
		return goPage;

	}
	
	
	
	@RequestMapping(value = "/download.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> fileDownload(int fileNo) throws Exception{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		String fileName = null;
		BoardFileVO fileVO = service.selectFileInfo(fileNo);
		if(fileVO != null) {
			fileName = fileVO.getFileName();
			
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(fileVO.getFileSavepath());
			
			
//			if(mType != null) {
//				headers.setContentType(mType);	
//			}else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" +
						new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
//			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		}else {
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
		
		
		
	}
	
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String boardDelete(
			RedirectAttributes ra,
			int boNo, Model model) {
		
		String goPage= "";
		ServiceResult result =  service.deleteBoard(boNo);
		if(result.equals(ServiceResult.OK)) {
			ra.addFlashAttribute("message", "삭제가 완료되었습니다!");
			goPage = "redirect:/board/list.do";
		}else {
			ra.addFlashAttribute("message", "서버에러, 다시 시도해주세요!");
			goPage = "redirect:/board/detail.do?boNo="+boNo;
		}
		return goPage;
		
	}
	
	
	@RequestMapping(value = "/update.do", method=RequestMethod.GET)
	public String boardUpdateForm(int boNo, Model model) {
		BoardVO boardVO = service.selectBoard(boNo);
		model.addAttribute("board", boardVO);
		model.addAttribute("status", "u");
		return "board/form";		
	}
	
	@RequestMapping(value = "/update.do", method=RequestMethod.POST)
	public String boardUpdate(
			HttpServletRequest req,
			RedirectAttributes ra,
			BoardVO boardVO, Model model) throws Exception {
		String goPage = "";
		ServiceResult result = service.updateBoard(req, boardVO);		
		if(result.equals(ServiceResult.OK)) { //수정 성공
			ra.addFlashAttribute("message", "수정이 완료되었습니다!");
			goPage = "redirect:/board/detail.do?boNo=" + boardVO.getBoNo();
		}else {
			model.addAttribute("message", "수정에 실패하였습니다!");
			model.addAttribute("board", boardVO);
			model.addAttribute("status", "u");
			goPage = "board/form";
		}
		
		return goPage;		
	}
	
	
	
	
	
	

}
