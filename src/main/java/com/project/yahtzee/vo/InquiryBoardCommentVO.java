package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class InquiryBoardCommentVO {
	int idx;
	int boardIdx;
	int boardState;
	String comment;
	String writeDate;
}
