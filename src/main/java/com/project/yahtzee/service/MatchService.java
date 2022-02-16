package com.project.yahtzee.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MatchVO;
import com.project.yahtzee.vo.MemberVO;

public interface MatchService {
	void insertMember(MemberVO member);
	void updateWin(MatchVO matchVO);
	void updateLose(String userID);
	void updateDraw(String userID);
	List<MatchVO> ranking();
}
