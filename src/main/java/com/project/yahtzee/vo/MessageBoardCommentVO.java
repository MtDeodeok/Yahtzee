package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class MessageBoardCommentVO {
	int idx;
	int messageBoardIdx;
	String userID;
	String comment;
	String writeDate;
	String modifiyDate;
}
