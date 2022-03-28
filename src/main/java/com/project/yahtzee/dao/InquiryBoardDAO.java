package com.project.yahtzee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardVO;
import com.project.yahtzee.vo.MessageBoardVO;

@Mapper
public interface InquiryBoardDAO {
	void insertInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void updateInquiryBoard(InquiryBoardVO inquiryBoardVO);
	void deleteInquiryBoard(@Param("idx")int idx);
	List<InquiryBoardVO> inquiryBoardList();
	void inquiryBoardAdminCheck(@Param("idx")int idx);
	void inquiryBoardRecommentUpdate(@Param("idx")int idx);
	
	int inquiryBoardCount(@Param("userID")String userID);
	List<InquiryBoardVO> findListPaging(Map<String,Object> parameter);
	
	int inquiryBoardSearchCount(@Param("searchKeyword")String searchKeyword);
	List<InquiryBoardVO> inquiryListSearch(Map<String,Object> parameter);
	
	InquiryBoardVO viewInquiry(@Param("idx")int idx);
}
