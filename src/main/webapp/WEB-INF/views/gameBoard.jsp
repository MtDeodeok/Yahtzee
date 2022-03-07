<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="gameBoard">
	<h1>게임룸</h1>
	<hr>
	<button id="btnSend" onclick="webSocket.gameStart()" >시작</button>
	
	<button id="btnSend" onclick="webSocket.rollingDice()" >굴리기</button>
	<div id="dices">
		<input type="button" id="dicebtn1" value="1" />
		<input type="button" id="dicebtn2" value="1" />
		<input type="button" id="dicebtn3" value="1" />
		<input type="button" id="dicebtn4" value="1" />
		<input type="button" id="dicebtn5" value="1" />
		
	</div>
	ones : <input type="button" id="sheet_ones" value="0" onclick="webSocket.ones()" />
	twos : <input type="button" id="sheet_twos" value="0" onclick="webSocket.twos()" />
	threes : <input type="button" id="sheet_threes" value="0" onclick="webSocket.threes()" />
	fours : <input type="button" id="sheet_fours" value="0" onclick="webSocket.fours()" />
	fives : <input type="button" id="sheet_fives" value="0" onclick="webSocket.fives()" />
	sixs : <input type="button" id="sheet_sixs" value="0" onclick="webSocket.sixs()" />
</div>