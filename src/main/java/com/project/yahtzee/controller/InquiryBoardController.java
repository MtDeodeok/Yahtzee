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
	InquiryBoardVO inquiryBoardVO = new InquiryBoardVO();
	
	@RequestMapping(value="inquiryBoard", method= RequestMethod.GET)
	public void inquiryBoard(Model model, @RequestParam(defaultValue = "1") int page) {
				
		// 총 게시물 수
		int totalListCnt = inquiryBoardService.inquiryBoardCount();
		// 생성인자로 총 게시물 수 , 현재 페이지 전달
		Pagination pagination = new Pagination(totalListCnt, page);
		
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		List<InquiryBoardVO> boardList = inquiryBoardService.findListPaging(startIndex, pageSize);
		model.addAttribute("inquiryBoardList", boardList);
		model.addAttribute("pagination", pagination);
	}
	
	@RequestMapping(value="inquiryBoardSearch", method= RequestMethod.GET)
	public String inquiryBoardSearch(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page) {
		String search = request.getParameter("search");
		
		int totalListCnt = inquiryBoardService.inquiryBoardCount();
		Pagination pagination = new Pagination(totalListCnt, page);
		
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		Map<String,Object> parameter = new HashMap();
		parameter.put("search", search);
		parameter.put("startIndex", startIndex);
		parameter.put("pageSize", pageSize);
		
		List<InquiryBoardVO> boardList = inquiryBoardService.searchInquiryList(parameter);
		model.addAttribute("inquiryBoardList", boardList);
		model.addAttribute("pagination", pagination);
		return "inquiryBoard";
	}
	
	@GetMapping("inquiryBoardWrite")
	public void inquiryBoardWrite() {
		
	}
	
	@PostMapping("inquiryBoardWrite")
	public void writeInquiryBoard(HttpServletRequest request, HttpSession session, InquiryBoardVO inquiryBoardVO) {
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		String content = request.getParameter("content");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("&gt;", ">");
		inquiryBoardVO.setContent(content);
		inquiryBoardVO.setUserID(member.getUserID());
		inquiryBoardService.insertInquiryBoard(inquiryBoardVO);
	}

	@PostMapping("inquiryBoardDelete")
	public String deleteInquiryBoard(@RequestParam(value="boardIdx")String boardIdx) {
		int idx = Integer.parseInt(boardIdx);
		
		inquiryBoardService.deleteInquiryBoard(idx);
		return "inquiryBoard";
	}
	@GetMapping("inquiryBoardView")
	public void inquiryBoardView(Model model) {
		model.addAttribute("inquiryDetails",inquiryBoardVO);
		System.out.println(model.getAttribute("inquiryDetails"));
	}
	
	@RequestMapping(value="inquiryBoardView", method= RequestMethod.POST)
	@ResponseBody
	public JSONObject inquiryBoardView(@RequestParam(value="idx")String boardIdx) {
		int idx = Integer.parseInt(boardIdx);
		inquiryBoardVO = inquiryBoardService.viewInquiry(idx);
		JSONObject inquiryData = new JSONObject();
		inquiryData.put("inquriry", inquiryBoardVO);
		return inquiryData;
	}
}
