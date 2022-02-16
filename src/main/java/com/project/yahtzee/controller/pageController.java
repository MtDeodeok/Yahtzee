package com.project.yahtzee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.yahtzee.util.Dice;
import com.project.yahtzee.util.chat.WebSocketHandler;
import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.util.gameRoom.RoomManager;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.YahtzeeBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class pageController {
	
	GameUser gameUser = new GameUser();
	Dice dice = new Dice();
	WebSocketHandler handler = new WebSocketHandler();
	RoomManager roomManager = new RoomManager(); 
	
	List<String> roomList = new ArrayList<String>();
	
	
	@GetMapping("waitingRoom")
	public void waitingRoom(Model model) {
		
		model.addAttribute("roomList",roomList);
	}
	
	@GetMapping("gameRoom")
	public void gameRoom() {
		
	}
	
	@PostMapping("gameRoomCreate")
	public String gameRoomCreate(HttpSession session,Model model, String game, String gameRoomName) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		gameUser.setId(member.getIdx());
		gameUser.setNickName(member.getUserNickName());
		GameRoom gameRoom = roomManager.createRoom(gameUser);
		gameRoom.setRoomName(gameRoomName);
		int bangID = gameRoom.getId();
		roomList.add(bangID-1,gameRoomName);
		
		System.out.println("crgame : "+game);
		return "redirect:/webSocket?bang_id="+bangID;
	}
}