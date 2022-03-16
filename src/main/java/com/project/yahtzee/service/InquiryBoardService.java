package com.project.yahtzee.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardVO;

public interface InquiryBoardService {
	void insertInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void updateInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void deleteInquiryBoard();
	List<InquiryBoardVO> inquiryBoardList();
	List<InquiryBoardVO> findListPaging(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	int inquiryBoardCount();
}
