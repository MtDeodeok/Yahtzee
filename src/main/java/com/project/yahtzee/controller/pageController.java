package com.project.yahtzee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.yahtzee.service.MatchService;
import com.project.yahtzee.service.MessageBoardService;
import com.project.yahtzee.util.Dice;
import com.project.yahtzee.util.chat.WebSocketHandler;
import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.util.gameRoom.RoomManager;
import com.project.yahtzee.vo.MatchVO;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {
	
	private final MatchService matchservice;
	
	@GetMapping("gameBoard")
	public void gameRoom() {
		
	}
	
	@GetMapping("ranking")
	public void ranking(Model model) {
		List<MatchVO> list = matchservice.ranking();
		System.out.println(list);
		for(int i = 0;list.size()>i;i++) {
			double win = list.get(i).getUserWin();
			double score = list.get(i).getUserScore();
			double winningRate = ((win/score)*100);
			list.get(i).setUserWinningRate(Math.round(winningRate*100)/100.0);
		}
		model.addAttribute("ranking", list);
	}
	
	@GetMapping(value = {"/","/login"})
	public String login() {
		return "login";
	}
}
