package com.project.yahtzee.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MatchVO;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardVO;

public interface MessageBoardService {
	void insertMessageBoard(MessageBoardVO messageBoardVO);
	void updateMessageBoard(MessageBoardVO messageBoardVO);
	void deleteMessageBoard(int idx);
	List<MessageBoardVO> messageBoardList();
	List<MessageBoardVO> findListPaging(int startIndex, int pageSize);
	int messageBoardCount();
}
