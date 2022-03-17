package com.project.yahtzee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.yahtzee.service.MessageBoardCommentService;
import com.project.yahtzee.service.MessageBoardService;
import com.project.yahtzee.util.Pagination;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardCommentVO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageBoardCommentController {

	private final MessageBoardService messageBoardService;
	private final MessageBoardCommentService messageBoardCommentService;
	
	@PostMapping("writeBoardComment")
	public String writeBoard(HttpServletRequest request, HttpSession session, MessageBoardCommentVO messageBoardCommentVO) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		messageBoardCommentVO.setComment(request.getParameter("comment"));
		messageBoardCommentVO.setMessageBoardIdx(Integer.parseInt(request.getParameter("boardIdx")));
		messageBoardCommentVO.setUserID(member.getUserID());
		messageBoardCommentService.insertMessageBoardComment(messageBoardCommentVO);
		return "/messageBoard";
	}
}
