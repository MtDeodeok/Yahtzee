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

import com.project.yahtzee.service.InquiryBoardService;
import com.project.yahtzee.service.MessageBoardCommentService;
import com.project.yahtzee.service.MessageBoardService;
import com.project.yahtzee.util.Pagination;
import com.project.yahtzee.vo.InquiryBoardVO;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardCommentVO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InquiryBoardController {

	private final InquiryBoardService inquiryBoardService;
	
	
	@RequestMapping(value="inquiryBoard", method= RequestMethod.GET)
	public void messageBoard(Model model, @RequestParam(defaultValue = "1") int page) {
				
		// 총 게시물 수
		int totalListCnt = inquiryBoardService.inquiryBoardCount();
		// 생성인자로 총 게시물 수 , 현재 페이지 전달
		Pagination pagination = new Pagination(totalListCnt, page);
		
		model.addAttribute("pagination", pagination);
		
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		List<InquiryBoardVO> boardList = inquiryBoardService.findListPaging(startIndex, pageSize);
		model.addAttribute("inquiryBoardList", boardList);
	}
	
	@GetMapping("writeInquiryBoard")
	public void writeInquiryBoard() {
		
	}
	
	@PostMapping("writeInquiryBoard")
	public String writeInquiryBoard(HttpServletRequest request, HttpSession session, InquiryBoardVO inquiryBoardVO) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		String content = request.getParameter("content");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("&gt;", ">");
		inquiryBoardVO.setContent(content);
		inquiryBoardVO.setUserID(member.getUserID());
		inquiryBoardService.insertInquiryBoard(inquiryBoardVO);
		return "inquiryBoard";
	}

	@PostMapping("deleteInquiryBoard")
	public String deleteInquiryBoard(@RequestParam(value="boardIdx")String boardIdx) {
		int idx = Integer.parseInt(boardIdx);
		inquiryBoardService.deleteInquiryBoard(idx);
		return "messageBoard";
	}
}
