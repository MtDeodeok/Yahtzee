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

import com.project.yahtzee.service.MessageBoardService;
import com.project.yahtzee.util.Pagination;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final MessageBoardService messageBoardService;
	
	
	@RequestMapping(value="messageBoard", method= RequestMethod.GET)
	public void messageBoard(Model model, @RequestParam(defaultValue = "1") int page) {
				
//		// 총 게시물 수
//		int totalListCnt = messageBoardService.messageBoardCount();
//		// 생성인자로 총 게시물 수 , 현재 페이지 전달
//		Pagination pagination = new Pagination(totalListCnt, page);
//		model.addAttribute("pagination", pagination);
		// DB select start index
//		int startIndex = pagination.getStartIndex();
		// 페이지당 보여지는 게시글 최대 개수
//		int pageSize = pagination.getPageSize();
		
		int startIndex = 0;
		int pageSize = 10;
		
		List<MessageBoardVO> boardList = messageBoardService.findListPaging(startIndex, pageSize);
		model.addAttribute("messageBoardList", boardList);
	}
	
	@RequestMapping(value="messageBoard", method= RequestMethod.POST)
	@ResponseBody
	public JSONObject messageBoard(@RequestParam(value="startNum", required=false)String startNum) {
		int totalListCnt = messageBoardService.messageBoardCount();
		int startListNum = Integer.parseInt(startNum);
		List<MessageBoardVO> messageboardList = messageBoardService.findListPaging(startListNum, 10);
		JSONObject listData = new JSONObject();
		listData.put("totalListCnt",totalListCnt);
		listData.put("boardList",messageboardList);
		return listData;
	}
	
	@PostMapping("writeBoard")
	public String writeBoard(HttpServletRequest request, HttpSession session, MessageBoardVO messageBoardVO) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		messageBoardVO.setTitle(request.getParameter("title"));
		messageBoardVO.setContent(request.getParameter("content"));
		messageBoardVO.setUserID(member.getUserID());
		messageBoardService.insertMessageBoard(messageBoardVO);
		return "/messageBoard";
	}
}
