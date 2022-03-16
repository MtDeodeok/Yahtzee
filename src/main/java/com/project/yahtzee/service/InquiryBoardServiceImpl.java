package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.InquiryBoardDAO;
import com.project.yahtzee.dao.MessageBoardDAO;
import com.project.yahtzee.vo.InquiryBoardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryBoardServiceImpl implements InquiryBoardService{
	
	private final InquiryBoardDAO inquiryBoardDAO; 
	String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	@Override
	public void insertInquiryBoard(InquiryBoardVO inquiryBoardVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInquiryBoard(InquiryBoardVO inquiryBoardVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInquiryBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InquiryBoardVO> findListPaging(int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.findListPaging(startIndex, pageSize);
	}
	
	@Override
	public List<InquiryBoardVO> inquiryBoardList() {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardList();
	}

	@Override
	public int inquiryBoardCount() {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardCount();
	}

	
}
