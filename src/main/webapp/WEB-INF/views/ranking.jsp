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
	<table border="1">
		<tr>
			<td>랭킹</td>
			<td>유저ID</td>
			<td>전적</td>
			<td>승리</td>
			<td>무승부</td>
			<td>패배</td>
		</tr>
		<c:forEach var="ranking" items="${ranking}">
			<tr>
				<td>${ranking.userRanking }</td>
				<td>${ranking.userID}</td>
				<td>${ranking.userScore}</td>
				<td>${ranking.userWin}</td>
				<td>${ranking.userDraw}</td>
				<td>${ranking.userLose}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>