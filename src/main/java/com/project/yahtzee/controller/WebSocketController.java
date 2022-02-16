package com.project.yahtzee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.yahtzee.service.MatchService;
import com.project.yahtzee.service.MemberService;
import com.project.yahtzee.util.chat.WebSocketHandler;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class WebSocketController {
	
	private final MemberService memberservice;
	private final MatchService matchservice;
	private final WebSocketHandler handler;
	
	// 채팅방 입장
	@RequestMapping(value = "/webSocket", method = RequestMethod.GET)
	public String view_chat(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		return "webSocket";
	}
	
	@PostMapping("/joiner")
	public String joiner(HttpSession session, MemberVO member) {
		if(member!=null) {
			if(memberservice.memberCheck(member)==0) {
				if(memberservice.userIDCheck(member.getUserID())==0) {
					memberservice.insertMember(member);
					MemberVO memberVO = memberservice.callMember(member);
					matchservice.insertMember(memberVO);
					handler.loginMember(member);
					return "redirect:waitingRoom";
				} else {
					return "/";
				}
			} else if(memberservice.memberCheck(member)==1){
				memberservice.updateNickName(member);
				session.setAttribute("loginMember",memberservice.callMember(member));
				handler.loginMember(member);
				return "redirect:waitingRoom";
			} else {
				return "/";
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
	
	@GetMapping(value = {"/","/login"})
	public String login() {
		return "login";
	}

}