package com.project.yahtzee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.yahtzee.service.MatchService;
import com.project.yahtzee.service.MemberService;
import com.project.yahtzee.util.chat.WebSocketHandler;
import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.util.gameRoom.RoomManager;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class WebSocketController {
	
	private final MemberService memberservice;
	private final MatchService matchservice;
	private final WebSocketHandler handler;
	GameUser gameUser = new GameUser();
	RoomManager roomManager = new RoomManager(); 
	List<String> roomList = new ArrayList<String>();
	// 채팅방 입장
	@RequestMapping(value = "/webSocket", method = RequestMethod.GET)
	public String view_chat(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		return "gameRoom";
	}
	
	@GetMapping("gameRoom")
	public String gameRoom() {
		return "/webSocket";
	}
	
	@ResponseBody
	@PostMapping("/gameRoomCreate")
	public JSONObject gameRoomCreate(HttpSession session,Model model, @RequestParam(value="gameRoomName")String gameRoomName) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		gameUser.setId(member.getIdx());
		gameUser.setNickName(member.getUserNickName());
		GameRoom gameRoom = roomManager.createRoom(gameUser);
		gameRoom.setRoomName(gameRoomName);
		int bangID = gameRoom.getId();
		roomList.add(bangID-1,gameRoomName);
		JSONObject response = new JSONObject();
		String returnUrl = "/webSocket?bang_id="+bangID;
		response.put("returnUrl", returnUrl);
		return response;
	}
	
	@GetMapping("waitingRoom")
	public void waitingRoom(Model model) {
		
		model.addAttribute("roomList",roomList);
	}
	
	@PostMapping("/joiner")
	public String joiner(HttpSession session, MemberVO member) {
		if(member!=null) {
			if(memberservice.memberCheck(member)==0) {
				if(memberservice.userIDCheck(member.getUserID())==0) {
					memberservice.insertMember(member);
					matchservice.insertMember(memberservice.callMember(member));
					handler.loginMember(member);
					return "redirect:waitingRoom";
				}
			} else if(memberservice.memberCheck(member)==1){
				memberservice.updateNickName(member);
				session.setAttribute("loginMember",memberservice.callMember(member));
				handler.loginMember(member);
				return "redirect:waitingRoom";
			}
		}
		return "/";
	} 
	
	@GetMapping("joiner")
	public String joiner(HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		if(memberservice.memberCheck(member)==0) {
			session.setAttribute("state", "신규");
		} else {
			member = memberservice.callMember(member);
			session.setAttribute("loginMember", member);
			session.setAttribute("state", "멤버");
		}
		return "join";
	}
	
	@GetMapping("/join")
	public void join() {
		
	}


}