<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<h1>게임룸</h1>
	<hr>
	<button id="btnSend" onclick="webSocket.gameStart()" >시작</button>
	<button id="btnSend" onclick="webSocket.rollingDice()" >굴리기</button>