package com.project.yahtzee.dao;

import java.util.List;

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
	int inquiryBoardCount();
}
