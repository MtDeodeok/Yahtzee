package com.project.yahtzee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MessageBoardCommentVO;
import com.project.yahtzee.vo.MessageBoardVO;

@Mapper
public interface MessageBoardCommentDAO {
	void insertMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO);
	void updateMessageBoardComment(MessageBoardCommentVO messageBoardCommentVO);
	void deleteMessageBoardComment();
	List<MessageBoardCommentVO> messageBoardCommentList();
}
