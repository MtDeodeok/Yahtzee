package com.project.yahtzee.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.InquiryBoardCommentVO;
import com.project.yahtzee.vo.InquiryBoardVO;

public interface InquiryBoardCommentService {
	void insertInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void updateInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO);
	void deleteInquiryBoardComment(int idx);
	List<InquiryBoardCommentVO> InquiryBoardComment(int idx);
}
