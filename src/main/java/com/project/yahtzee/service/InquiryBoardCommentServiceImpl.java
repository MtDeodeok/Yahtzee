package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.InquiryBoardCommentDAO;
import com.project.yahtzee.vo.InquiryBoardCommentVO;
import com.project.yahtzee.vo.InquiryBoardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryBoardCommentServiceImpl implements InquiryBoardCommentService{
	
	private final InquiryBoardCommentDAO inquiryBoardCommentDAO; 
	String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	@Override
	public void insertInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO) {
		inquiryBoardCommentVO.setWriteDate(now);
		inquiryBoardCommentDAO.insertInquiryBoardComment(inquiryBoardCommentVO);
	}

	@Override
	public void updateInquiryBoardComment(InquiryBoardCommentVO inquiryBoardCommentVO) {
		inquiryBoardCommentDAO.updateInquiryBoardComment(inquiryBoardCommentVO);
	}

	@Override
	public void deleteInquiryBoardComment(int idx) {
		inquiryBoardCommentDAO.deleteInquiryBoardComment(idx);		
	}

	@Override
	public InquiryBoardVO InquiryBoardComment(int idx) {
		return inquiryBoardCommentDAO.InquiryBoardComment(idx);
	}
	
	
}
