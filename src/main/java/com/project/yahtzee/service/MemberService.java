package com.project.yahtzee.service;

import com.project.yahtzee.vo.MemberVO;

public interface MemberService {
	void insertMember(MemberVO member);
	void updateMember(MemberVO member);
	void deleteMember(String userID);
	int memberCheck(MemberVO member);
	int userIDCheck(String userID);
	MemberVO callMember(MemberVO member);
	void updateNickName(MemberVO member);
}
