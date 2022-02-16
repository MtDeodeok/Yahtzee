<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form id='joiner' action="joiner" method='post'>
		<input type='text' style='display: none' readonly id='profile' name='userProfile' value=${sessionScope.loginMember.getUserProfile()}>
		<input type='text' readonly id='userEmail' name='userEmail' value=${sessionScope.loginMember.getUserEmail()}></br>
		<input type='text' readonly id='userProvider' name='userProvider' value=${sessionScope.loginMember.getUserProvider()}></br>
		<input type='text' readonly id='userName' name='userName' value=${sessionScope.loginMember.getUserName()}></br>
		
		<c:choose>
			<c:when test="${sessionScope.state eq '신규'}">
				<input type='text' id='userID' name='userID' value="아이디를 입력하세요"></br>
				<input type='text' id='userNickName' name='userNickName' value="닉네임을 입력하세요">
				<div>
					<button type='submit'>가입</button>
					<button type='button' onclick="location.href='login.jsp'">나가기</button>
				</div>
			</c:when>
			<c:otherwise>
				<input type='text' readonly id='userID' name='userID' value=${sessionScope.loginMember.getUserID()}></br>
				<input type='text' id='userNickName' name='userNickName' value=${sessionScope.loginMember.getUserNickName()}>
				<div>
					<button type='submit'>입장</button>
					<button type='button' onclick="location.href='login.jsp'">나가기</button>
				</div>
			</c:otherwise>
		</c:choose>
		
	</form>
</body>
</html>