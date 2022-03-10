package com.project.yahtzee.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.yahtzee.vo.MessageBoardCommentVO;
import com.project.yahtzee.vo.MessageBoardVO;

public interface MessageBoardCommentService {
	void insertMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO);
	void updateMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO);
	void deleteMessageBoardComment();
	List<MessageBoardCommentVO> messageBoardCommentList(int messageboardIdx);
}
