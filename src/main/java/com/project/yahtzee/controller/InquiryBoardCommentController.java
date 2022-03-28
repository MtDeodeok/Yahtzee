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
public class InquiryBoardCommentController {

	private final InquiryBoardCommentService inquiryBoardCommentService;
	private final InquiryBoardService inquiryBoardService;

	@PostMapping("inquiryBoardRecomment")
	@ResponseBody
	public JSONObject inquiryBoardRecomment(HttpServletRequest request, HttpSession session, InquiryBoardCommentVO inquiryBoardCommentVO) {
		String comment = request.getParameter("comment");
		int idx = Integer.parseInt(request.getParameter("idx"));
		comment = comment.replaceAll("&lt;", "<");
		comment = comment.replaceAll("&gt;", ">");
		
		System.out.println("comment");
		inquiryBoardCommentVO.setComment(comment);
		inquiryBoardCommentVO.setBoardIdx(idx);
		
		inquiryBoardCommentService.insertInquiryBoardComment(inquiryBoardCommentVO);
		inquiryBoardService.inquiryBoardRecommentUpdate(idx);
		List<InquiryBoardCommentVO> commentList = inquiryBoardCommentService.InquiryBoardComment(idx);
		System.out.println(commentList);
		JSONObject recomment = new JSONObject();
		recomment.put("recomment", commentList);
		
		return recomment;
	}

}
