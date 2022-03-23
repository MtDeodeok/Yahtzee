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
	List<InquiryBoardVO> findListPaging(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	List<InquiryBoardVO> searchInquiryList(Map<String,Object> parameter);
	InquiryBoardVO viewInquiry(@Param("idx")int idx);
	int inquiryBoardCount();
}
