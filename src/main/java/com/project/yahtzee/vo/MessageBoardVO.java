package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class MessageBoardVO {
	int idx;
	String userID;
	String content;
	String writeDate;
	String modifiyDate;
	int state;
}
