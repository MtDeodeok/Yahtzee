package com.project.yahtzee.service;

import java.util.List;
import java.util.Map;

import com.project.yahtzee.vo.InquiryBoardVO;

public interface InquiryBoardService {
	void insertInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void updateInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void deleteInquiryBoard(int idx);
	List<InquiryBoardVO> inquiryBoardList();
	List<InquiryBoardVO> findListPaging(int startIndex, int pageSize);
	List<InquiryBoardVO> searchInquiryList(Map<String,Object> parameter);
	InquiryBoardVO viewInquiry(int idx);
	int inquiryBoardCount();
}
