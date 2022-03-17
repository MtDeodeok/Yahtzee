package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.MessageBoardCommentDAO;
import com.project.yahtzee.vo.MessageBoardCommentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageBoardCommentServiceImpl implements MessageBoardCommentService{
	
	private final MessageBoardCommentDAO messageBoardCommentDAO;
	String writeTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	
	@Override
	public void insertMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO) {
		// TODO Auto-generated method stub
		messageBoardCommentVO.setWriteDate(writeTime);
		messageBoardCommentVO.setModifiyDate(writeTime);
		messageBoardCommentDAO.insertMessageBoardComment(messageBoardCommentVO);
	}

	@Override
	public void updateMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO) {
		// TODO Auto-generated method stub
		messageBoardCommentVO.setModifiyDate(writeTime);
		messageBoardCommentDAO.updateMessageBoardComment(messageBoardCommentVO);
	}

	@Override
	public void deleteMessageBoardComment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MessageBoardCommentVO> messageBoardCommentList() {
		// TODO Auto-generated method stub
		return messageBoardCommentDAO.messageBoardCommentList();
	}
	
	
	
}
