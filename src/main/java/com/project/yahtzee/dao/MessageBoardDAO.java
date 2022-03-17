package com.project.yahtzee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MessageBoardVO;

@Mapper
public interface MessageBoardDAO {
	void insertMessageBoard(MessageBoardVO messageBoardVO);
	void updateMessageBoard(MessageBoardVO messageBoardVO);
	void deleteMessageBoard(@Param("idx")int idx);
	List<MessageBoardVO> messageBoardList();
	List<MessageBoardVO> findListPaging(@Param("startIndex")int startIndex, @Param("pageSize")int pageSize);
	int messageBoardCount();
	
}
