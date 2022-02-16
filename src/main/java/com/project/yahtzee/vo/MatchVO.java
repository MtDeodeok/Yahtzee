package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class MatchVO {
	int idx;
	String userID;
	int userScore;
	int userWin;
	int userDraw;
	int userLose;
	String lastWinTime;
	int userRanking;
}
