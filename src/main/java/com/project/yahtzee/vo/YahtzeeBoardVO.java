package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class YahtzeeBoardVO {
	
	int sheet;
	int ones;
	int twos;
	int threes;
	int fours;
	int fives;
	int sixes;
	int upperSectionBonus;
	int onePair;
	int twoPair;
	int threeOfAKind;
	int fourOfAKind;
	int fullHouse;
	int smallStraight;
	int largeStraight;
	int choice;
	int yahtzee;
	int yahtzeeBonus;
	int total;
}
