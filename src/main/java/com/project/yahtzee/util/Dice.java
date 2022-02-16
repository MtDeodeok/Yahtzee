package com.project.yahtzee.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {
	
	Random randomInt = new Random();
	
	//주사위 굴리기
	public int randomDice() {
		int randomDice = randomInt.nextInt(6) + 1;
		return randomDice;
	}
	
	//주사위List
	public List<Integer> diceList() {
		List<Integer> diceList = new ArrayList<Integer>();

		diceList.add(0, randomDice());
		diceList.add(1, randomDice());
		diceList.add(2, randomDice());
		diceList.add(3, randomDice());
		diceList.add(4, randomDice());
		
		System.out.println("diceClass diceList : "+diceList);
		return diceList;
	}
	
	//주사위 고정
	
}
