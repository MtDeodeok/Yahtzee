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
	String modifiyTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	@Override
	public void insertMessageBoard(MessageBoardVO messageBoardVO) {
		// TODO Auto-generated method stub
		messageBoardDAO.insertMessageBoard(messageBoardVO);
	}

	@Override
	public void updateMessageBoard(MessageBoardVO messageBoardVO) {
		// TODO Auto-generated method stub
		messageBoardVO.setModifiyDate(modifiyTime);
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
	
	
}
