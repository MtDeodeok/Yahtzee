package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class InquiryBoardVO {
	int idx;
	String title;
	String userID;
	String content;
	String writeDate;
	String modifiyDate;
	int state;
}
