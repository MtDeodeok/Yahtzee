package com.project.yahtzee.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.yahtzee.vo.MemberVO;

@Mapper
public interface MemberDAO {
	void insertMember(MemberVO member);
	void updateMember(MemberVO member);
	void deleteMember(@Param("userID")String userID);
	int memberCheck(MemberVO member);
	int userIDCheck(@Param("userID")String userID);
	MemberVO callMember(MemberVO member);
	void updateNickName(MemberVO member);
}
