package com.project.yahtzee.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardCommentVO;
import com.project.yahtzee.vo.InquiryBoardVO;

@Mapper
public interface InquiryBoardCommentDAO {
	void insertInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void updateInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void deleteInquiryBoardComment(@Param("idx")int idx);
	InquiryBoardVO InquiryBoardComment(@Param("idx")int idx);
}
