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
	
	int inquiryBoardSearchCount(String searchKeyword);
	List<InquiryBoardVO> inquiryListSearch(Map<String,Object> parameter);
	
	InquiryBoardVO viewInquiry(int idx);
}
