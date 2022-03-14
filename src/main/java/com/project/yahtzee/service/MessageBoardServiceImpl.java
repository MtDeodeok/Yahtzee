package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.MessageBoardDAO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageBoardServiceImpl implements MessageBoardService{

	private final MessageBoardDAO messageBoardDAO;
	String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	@Override
	public void insertMessageBoard(MessageBoardVO messageBoardVO) {
		// TODO Auto-generated method stub
		messageBoardVO.setWriteDate(now);
		messageBoardDAO.insertMessageBoard(messageBoardVO);
	}

	@Override
	public void updateMessageBoard(MessageBoardVO messageBoardVO) {
		// TODO Auto-generated method stub
		messageBoardVO.setModifiyDate(now);
		messageBoardDAO.updateMessageBoard(messageBoardVO);
	}

	@Override
	public void deleteMessageBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MessageBoardVO> messageBoardList() {
		// TODO Auto-generated method stub
		
		return messageBoardDAO.messageBoardList();
	}

	@Override
	public List<MessageBoardVO> findListPaging(int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		return  messageBoardDAO.findListPaging(startIndex, pageSize);
	}

	@Override
	public int messageBoardCount() {
		// TODO Auto-generated method stub
		return messageBoardDAO.messageBoardCount();
	}
	
}
