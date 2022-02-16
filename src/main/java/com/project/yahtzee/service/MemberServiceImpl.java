package com.project.yahtzee.service;

import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.MemberDAO;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberDAO memberDAO;
	
	
	@Override
	public void insertMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		memberDAO.insertMember(memberVO);
		
	}

	@Override
	public void updateMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		memberDAO.updateMember(memberVO);
	}

	@Override
	public void deleteMember(String userID) {
		// TODO Auto-generated method stub
		memberDAO.deleteMember(userID);
	}

	@Override
	public int memberCheck(MemberVO member) {
		// TODO Auto-generated method stub
		return memberDAO.memberCheck(member);
	}
	
	@Override
	public int userIDCheck(String userID) {
		// TODO Auto-generated method stub
		return memberDAO.userIDCheck(userID);
	}

	@Override
	public MemberVO callMember(MemberVO member) {
		return memberDAO.callMember(member);
	}

	@Override
	public void updateNickName(MemberVO member) {
		memberDAO.updateNickName(member);
	}


}
