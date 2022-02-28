package com.project.yahtzee.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.project.yahtzee.vo.DiceVO;

public class Dice {

	Random randomInt = new Random();
	List<Integer> diceList = new ArrayList<Integer>();

	// 주사위 굴리기
	// 주사위 1개를 1~6 랜덤으로 값 나오도록 굴리는 부분
	private int randomDice() {
		int randomDice = randomInt.nextInt(6) + 1;
		return randomDice;
	}

	// 주사위List
	// 주사위 5개에 랜덤 주사위 값을 집어 넣는 부분
	private List<Integer> firstDiceList() {

		diceList.add(0, randomDice());
		diceList.add(1, randomDice());
		diceList.add(2, randomDice());
		diceList.add(3, randomDice());
		diceList.add(4, randomDice());

		System.out.println("diceClass diceList : " + diceList);
		return diceList;
	}

	// 주사위 고정
	// 고정값을 2진수로 받아와서 체크
	private List<Integer> fixedDiceList(int num) {
		

		System.out.println("diceClass diceList : " + diceList);
		return diceList;
	}
}
