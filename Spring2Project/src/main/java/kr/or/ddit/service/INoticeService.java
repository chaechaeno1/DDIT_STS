package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.crud.NoticeVO;
import kr.or.ddit.vo.crud.PaginationInfoVO;

public interface INoticeService {

	int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);

	List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO);

	ServiceResult insertNotice(NoticeVO noticeVO);

	NoticeVO selectNotice(int boNo);

	ServiceResult updateNotice(NoticeVO noticeVO);

	ServiceResult deleteNotice(int boNo);

	
}
