<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대기실</title>
<script src="/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	function join(bangID) {
		var href = "/gameRoom?bang_id=" + bangID;
		location.href = href;
	}
</script>
</head>
<body>
	<button>방 만들기</button>
	
	<form action="gameRoomCreate" method="post">
		<div>
			<input type="radio" name="game" value="요트" checked="checked">요트
			<input type="radio" name="game" value="야찌">야찌
			<input type="radio" name="game" value="야르">야르
		</div>
		방이름<input type="text" name="gameRoomName">
		<button type="submit">만들기</button>
	</form>
	
	<hr>

	<c:forEach items="${roomList}" var="room" varStatus="status">
		bangID :<button id="join" onclick="join('${status.index+1}')">${room }</button>
	</c:forEach>
</body>
</html>