package com.project.yahtzee.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardVO;

public interface InquiryBoardService {
	void insertInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void updateInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void deleteInquiryBoard(int idx);
	List<InquiryBoardVO> inquiryBoardList();
	void inquiryBoardAdminCheck(int idx);
	void inquiryBoardRecommentUpdate(int idx);
	
	int inquiryBoardCount(String userID);
	List<InquiryBoardVO> findListPaging(Map<String,Object> parameter);
	
	int inquiryBoardSearchKeywordCount(String searchKeyword);
	List<InquiryBoardVO> inquiryListSearchKeyword(Map<String,Object> parameter);

	int inquiryBoardSearchStateCount(int searchState);
	List<InquiryBoardVO> inquiryListSearchState(Map<String,Object> parameter);
	
	InquiryBoardVO viewInquiry(int idx);
}
