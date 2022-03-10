package com.project.yahtzee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.yahtzee.vo.MessageBoardVO;

@Mapper
public interface MessageBoardDAO {
	void insertMessageBoard(MessageBoardVO messageBoardVO);
	void updateMessageBoard(MessageBoardVO messageBoardVO);
	void deleteMessageBoard();
	List<MessageBoardVO> messageBoardList();
}
