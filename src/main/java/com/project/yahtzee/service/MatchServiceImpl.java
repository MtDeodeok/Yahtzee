package com.project.yahtzee.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.yahtzee.dao.MatchDAO;
import com.project.yahtzee.util.chat.WebSocketHandler;
import com.project.yahtzee.vo.MatchVO;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
	
	private final MatchDAO matchDAO;
	
	String winTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	
	@Override
	public void updateWin(MatchVO matchVO) {
		// TODO Auto-generated method stub
		matchVO.setLastWinTime(winTime);
		matchDAO.updateWin(matchVO);
	}

	@Override
	public void updateLose(String userID) {
		// TODO Auto-generated method stub
		matchDAO.updateLose(userID);
		
	}

	@Override
	public void updateDraw(String userID) {
		// TODO Auto-generated method stub
		matchDAO.updateDraw(userID);
	}

	@Override
	public List<MatchVO> ranking() {
		// TODO Auto-generated method stub
		
		return matchDAO.ranking();
	}

	@Override
	public void insertMember(MemberVO member) {
		// TODO Auto-generated method stub
		matchDAO.insertMember(member);
	}

}
