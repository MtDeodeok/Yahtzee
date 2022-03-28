package com.project.yahtzee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardCommentVO;
import com.project.yahtzee.vo.InquiryBoardVO;

@Mapper
public interface InquiryBoardCommentDAO {
	void insertInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void updateInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void deleteInquiryBoardComment(@Param("idx")int idx);
	List<InquiryBoardCommentVO> InquiryBoardComment(@Param("boardIdx")int idx);
}
