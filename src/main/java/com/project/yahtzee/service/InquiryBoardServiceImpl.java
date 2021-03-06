package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
		inquiryBoardVO.setWriteDate(now);
		inquiryBoardDAO.insertInquiryBoard(inquiryBoardVO);
	}

	@Override
	public void updateInquiryBoard(InquiryBoardVO inquiryBoardVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInquiryBoard(int idx) {
		// TODO Auto-generated method stub
		inquiryBoardDAO.deleteInquiryBoard(idx);
	}

	@Override
	public int inquiryBoardCount(String userID) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardCount(userID);
	}
	
	@Override
	public List<InquiryBoardVO> findListPaging(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.findListPaging(parameter);
	}
	
	@Override
	public List<InquiryBoardVO> inquiryBoardList() {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardList();
	}

	@Override
	public int inquiryBoardSearchKeywordCount(String search) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardSearchKeywordCount(search);
	}
	
	@Override
	public List<InquiryBoardVO> inquiryListSearchKeyword(Map<String,Object> parameter) {
		// TODO Auto-generated method stub
		
		return inquiryBoardDAO.inquiryListSearchKeyword(parameter);
	}

	@Override
	public InquiryBoardVO viewInquiry(int idx) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.viewInquiry(idx);
	}

	@Override
	public void inquiryBoardAdminCheck(int idx) {
		// TODO Auto-generated method stub
		inquiryBoardDAO.inquiryBoardAdminCheck(idx);
	}

	@Override
	public void inquiryBoardRecommentUpdate(int idx) {
		// TODO Auto-generated method stub
		inquiryBoardDAO.inquiryBoardRecommentUpdate(idx);
	}

	@Override
	public int inquiryBoardSearchStateCount(int searchState) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryBoardSearchStateCount(searchState);
	}

	@Override
	public List<InquiryBoardVO> inquiryListSearchState(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return inquiryBoardDAO.inquiryListSearchState(parameter);
	}
	
}
