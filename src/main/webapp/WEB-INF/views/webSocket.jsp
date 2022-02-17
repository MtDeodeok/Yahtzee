<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>채팅창</title>
	<script src="/js/jquery-3.5.1.min.js"></script>
	<script src="/js/sockjs.js"></script>
	<script src="/js/gameRoomChatWebSocket.js"></script>
	<script type="text/javascript">
		$(window).on('load', function() {
			webSocket.init({ url: '<c:url value="/webSocket" />' });
		});
	</script>
</head>
<body>
	<div id="gameRoom">
		<jsp:include page="gameRoom.jsp" />
	</div>
	
	<h3>채팅창</h3>
	<div style="width: 800px; height: 700px; padding: 10px; border: solid 1px #e1e3e9;">
		<div id="divChatData"></div>
	</div>
	<div style="width: 100%; height: 10%; padding: 10px;">
		<input type="text" id="message" size="100" onkeypress="if(event.keyCode==13){webSocket.sendChat();}" />
		<input type="button" id="btnSend" value="채팅 전송" onclick="webSocket.sendChat()" />
	</div>
</body>
</html>