package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class DiceVO {
	int idx;
	String userID;

	int dice1;
	int dice2;
	int dice3;
	int dice4;
	int dice5;
}
