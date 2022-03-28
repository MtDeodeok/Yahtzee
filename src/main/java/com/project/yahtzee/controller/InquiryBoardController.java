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

import com.project.yahtzee.service.InquiryBoardCommentService;
import com.project.yahtzee.service.InquiryBoardService;
import com.project.yahtzee.service.MessageBoardCommentService;
import com.project.yahtzee.service.MessageBoardService;
import com.project.yahtzee.util.Pagination;
import com.project.yahtzee.vo.InquiryBoardCommentVO;
import com.project.yahtzee.vo.InquiryBoardVO;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.MessageBoardCommentVO;
import com.project.yahtzee.vo.MessageBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InquiryBoardController {

	private final InquiryBoardService inquiryBoardService;
	private final InquiryBoardCommentService inquiryBoardCommentService;
	InquiryBoardVO inquiryBoardVO = new InquiryBoardVO();
	InquiryBoardCommentVO inquiryBoardCommentVO = new InquiryBoardCommentVO();
	
	@RequestMapping(value="inquiryBoard", method= RequestMethod.GET)
	public void inquiryBoard(Model model,HttpSession session, @RequestParam(defaultValue = "1") int page) {
		MemberVO memberVO = new MemberVO();
		memberVO = (MemberVO) session.getAttribute("loginMember");
				
		// 총 게시물 수
		int totalListCnt = inquiryBoardService.inquiryBoardCount(memberVO.getUserID());
		// 생성인자로 총 게시물 수 , 현재 페이지 전달
		Pagination pagination = new Pagination(totalListCnt, page);
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("startIndex",startIndex);
		parameter.put("pageSize",pageSize);
		parameter.put("userID",memberVO.getUserID()); 
		
		List<InquiryBoardVO> boardList = inquiryBoardService.findListPaging(parameter);
		
		for(int i=0;boardList.size()>i;i++) {
			if(boardList.get(i).getState()==2) {
				boardList.get(i).setStateName("check");
			} else if(boardList.get(i).getState()==3) {
				boardList.get(i).setStateName("CA");
			} else {
				boardList.get(i).setStateName("");
			}
		}
		
		model.addAttribute("inquiryBoardList", boardList);
		model.addAttribute("pagination", pagination);
	}
	
	@GetMapping("inquiryBoardSearch")
	public void inquiryBoardSearch() {
		
	}
	
	@RequestMapping(value="inquiryBoardSearch", method= RequestMethod.POST)
	@ResponseBody
	public JSONObject inquiryBoardSearch(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page,@RequestParam("searchKeyword")String searchKeyword) {
		int totalListCnt = inquiryBoardService.inquiryBoardSearchCount(searchKeyword);
		Pagination pagination = new Pagination(totalListCnt, page);
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		Map<String,Object> parameter = new HashMap<String,Object>();
		JSONObject inquiryData = new JSONObject();
		
		parameter.put("searchKeyword", searchKeyword);
		parameter.put("startIndex", startIndex);
		parameter.put("pageSize", pageSize);
		//parameter.put("state", state);
		
		List<InquiryBoardVO> boardList = inquiryBoardService.inquiryListSearch(parameter);
		inquiryData.put("inquiry", boardList);
		inquiryData.put("loginUser", member);
		inquiryData.put("pagination", pagination);
		
		return inquiryData;
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
	public void inquiryBoardView(Model model,@RequestParam(value="idx")String boardIdx) {
		int idx = Integer.parseInt(boardIdx);
		model.addAttribute("inquiryDetails",inquiryBoardService.viewInquiry(idx));
		model.addAttribute("inquiryComment",inquiryBoardCommentService.InquiryBoardComment(idx));
	}
	
	@PostMapping("inquiryBoardAdminCheck")
	public void inquiryBoardAdminCheck(@RequestParam(value="idx")String boardIdx) {
		int idx = Integer.parseInt(boardIdx);
		inquiryBoardService.inquiryBoardAdminCheck(idx);
		
	}
}
