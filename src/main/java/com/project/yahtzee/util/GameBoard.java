package com.project.yahtzee.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.project.yahtzee.vo.YahtzeeBoardVO;

public class GameBoard {

	// 시작된 게임 구별 (야찌 야츄 요트)
	private void gameTable(String gameName) {
		if (gameName == "야찌") {

		} else if (gameName == "야츄") {

		}
		// 요트
		else {

		}
	}

	// 게임별 보드
	private void yahtzeeBoard() {
		YahtzeeBoardVO yahtzeeBoardVO = new YahtzeeBoardVO();

	}
	
	
	//아래부터 족보에 따른 점수 체크 및 점수값 리턴 관련 메소드
	
	// ones
	public int ones(List<Integer> diceList) {
		int sum = 0;
		
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i)==1) {
				sum = sum + 1;
			} else {
				sum = sum + 0;
			}
		}
		return sum;
	}

	// twos
	public int twos(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i) == 2) {
				sum = sum + 2;
			}
		}
		return sum;
	}

	// threes
	public int threes(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i) == 3) {
				sum = sum + 3;
			}
		}
		return sum;
	}

	// fours
	public int fours(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i) == 4) {
				sum = sum + 4;
			}
		}
		return sum;
	}

	// fives
	public int fives(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i) == 5) {
				sum = sum + 5;
			}
		}
		return sum;
	}

	// sixs
	public int sixs(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			if (diceList.get(i) == 6) {
				sum = sum + 6;
			}
		}
		return sum;
	}

	// upperSectionBonus
	public int upperSectionBonus(int ones, int twos, int threes, int fours, int fives, int sixes) {
		int sum = ones + twos + threes + fours + fives + sixes;
		if (sum >= 65) {
			return 35;
		}
		return 0;
	}

	// choice
	public int choice(List<Integer> diceList) {
		int sum = 0;
		for (int i = 0; i < diceList.size(); i++) {
			sum = sum + diceList.get(i);
		}
		return sum;
	}

	// diceCheck
	public int diceCheck(List<Integer> diceList) {
		int result = 0;

		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;

		int smallStraight = 0;
		int largeStraight = 0;

		// 주사위 눈 체크
		Set<Integer> set = new HashSet<Integer>(diceList);
		for (int num : set) {
			int count = Collections.frequency(diceList, num);

			// 스트레이트 판단
			if (set.contains(2) && set.contains(3) && set.contains(4) && set.contains(5)) {
				if (set.contains(1)) {
					smallStraight = 1;
					largeStraight = 1;
				} else if (set.contains(6)) {
					smallStraight = 1;
					largeStraight = 1;
				}
			} else {
				if (set.contains(3) && set.contains(4)) {
					if (set.contains(2)) {
						if (set.contains(1)) {
							smallStraight = 1;
						} else if (set.contains(5)) {
							smallStraight = 1;
						}
					} else if (set.contains(5)) {
						if (set.contains(6)) {
							smallStraight = 1;
						}
					}
				}
			}

			// pair,three,four,yahtzee 판단
			if (count == 5) {
				five = 1;
			} else if (count == 4) {
				four = 1;
			} else if (count == 3) {
				three = 1;
			} else if (count == 2) {
				two += 1;
			}
		}

		// 점수 조건 체크
		if (five == 1) { // yathzee
			result = 6;
		} else if (three == 1 && two == 1) { // fullHouse
			result = 5;
		} else if (four == 1) { // fourOfAKind
			result = 4;
		} else if (three == 1) { // threeOfAKind
			result = 3;
		} else if (two == 2) { // twoPair
			result = 2;
		} else if (two == 1) { // onePair
			result = 1;
		} else if (largeStraight == 1) { // largeStraight
			result = 8;
		} else if (smallStraight == 1) { // smallStraight
			result = 7;
		}

		return result;
	}

	// onePair
	public int onePair(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check >= 1) {
			for (int j = 0; j < diceList.size(); j++) {
				sum = sum + diceList.get(j);
			}
			return sum;
		}
		return sum;
	}

	// twoPair
	public int twoPair(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 2 || check == 5) {
			for (int j = 0; j < diceList.size(); j++) {
				sum = sum + diceList.get(j);
			}
			return sum;
		}
		return sum;
	}

	// threeOfAKind
	public int threeOfAKind(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 3 || check == 4 || check == 5 || check == 6) {
			for (int j = 0; j < diceList.size(); j++) {
				sum = sum + diceList.get(j);
			}
			return sum;
		}
		return sum;
	}

	// fourOfAKind
	public int fourOfAKind(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 4 || check == 6) {
			for (int j = 0; j < diceList.size(); j++) {
				sum = sum + diceList.get(j);
			}
			return sum;
		}
		return sum;
	}

	// fullHouse
	public int fullHouse(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 5) {
			for (int j = 0; j < diceList.size(); j++) {
				sum = sum + diceList.get(j);
			}
			return sum;
		}
		return sum;
	}

	// yahtzee
	public int yahtzee(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 6) {
			return 50;
		}
		return sum;
	}

	// smallStraight
	public int smallStraight(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 7 || check == 8) {
			return 30;
		}
		return sum;
	}

	// largeStraight
	public int largeStraight(List<Integer> diceList) {
		int sum = 0;
		int check = diceCheck(diceList);
		if (check == 8) {
			return 40;
		}
		return sum;
	}
}
