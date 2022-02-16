package com.project.yahtzee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MatchVO;
import com.project.yahtzee.vo.MemberVO;

@Mapper
public interface MatchDAO {
	void insertMember(MemberVO member);
	void updateWin(MatchVO matchVO);
	void updateLose(@Param("userID")String userID);
	void updateDraw(@Param("userID")String userID);
	List<MatchVO> ranking();
}
