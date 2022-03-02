package com.project.yahtzee.util.chat;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.yahtzee.service.MemberService;
import com.project.yahtzee.util.Dice;
import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.vo.MemberVO;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	// 세션 리스트
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
	private MemberVO member = new MemberVO();
	private Dice dice = new Dice();
	// 시간 hh:mm:ss 채팅 끝에 출력
	String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

	public void loginMember(MemberVO membervo) {
		member = membervo;
	}

	// 메시지 엑세스xss 방어 처리
	public static String ConvertInputValue(String message) {
		message = message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&apos;").replace("\\", "&#x2F;").replace(" ", "&nbsp;").replace("\n", "<br />");
		return message;
	}

	// 접속 유저 닉네임
	public String userNickName(WebSocketSession session) {
		String userNickName = "";
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> sendMap = sessionList.get(i);
			String bang_id = (String) sendMap.get("bang_id");
			String nickName = (String) sendMap.get("nickName");
			WebSocketSession sess = (WebSocketSession) sendMap.get("session");

			if (session.equals(sess)) {
				userNickName = (String) sendMap.get("nickName");
				break;
			}
		}
		return userNickName;
	}

	// 클라이언트가 서버로 메세지 전송 처리
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);

		// JSON --> Map으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> mapReceive = objectMapper.readValue(message.getPayload(), Map.class);

		switch (mapReceive.get("cmd")) {

		// CLIENT 입장
		case "GAME_ROOM_ENTER":
			gameRoomEnter(mapReceive, session, objectMapper);
			break;

		// CLIENT 메세지
		case "USER_CHAT_MSG":
			// 같은 채팅방에 메세지 전송
			userChatMsg(mapReceive, session, objectMapper);
			break;
		case "GAME_START":
				dice.firstDiceList();
			break;
		case "FIXED_DICE_ROLLING":
			System.out.println(mapReceive);
			System.out.println(mapReceive.get("dices_lock"));
			int num = Integer.parseInt(mapReceive.get("dices_lock"));
			dice.fixedDice(num);
			System.out.println("다이스 롤링");
			break;
		}
		
	}

	// 클라이언트가 연결을 끊음 처리
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		gameRoomExit(session);

	}

	// 채팅룸 입장
	private void gameRoomEnter(Map<String, String> mapReceive, WebSocketSession session, ObjectMapper objectMapper)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bang_id", mapReceive.get("bang_id"));
		map.put("session", session);
		map.put("sessionID", session.getId());
		map.put("nickName", member.getUserNickName());
		sessionList.add(map);
		System.out.println("입장 : " + sessionList);

		String joinUser = userNickName(session);

		// 같은 채팅방에 입장 메세지 전송
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			String nickName = (String) mapSessionList.get("nickName");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			if (bang_id.equals(mapReceive.get("bang_id"))) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", "GAME_ROOM_ENTER");
				mapToSend.put("msg", ConvertInputValue(joinUser) + "님이 입장 했습니다. " + dateTime);
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
		System.out.println("입장 전송 : " + sessionList);
	}

	// 채팅메시지 전송
	private void userChatMsg(Map<String, String> mapReceive, WebSocketSession session, ObjectMapper objectMapper)
			throws IOException {
		String sendUser = userNickName(session);

		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			if (bang_id.equals(mapReceive.get("bang_id"))) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", "USER_CHAT_MSG");
				mapToSend.put("msg", sendUser + " : " + ConvertInputValue(mapReceive.get("msg")) + " " + dateTime);
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}

	// 채팅룸 퇴장
	private void gameRoomExit(WebSocketSession session) throws IOException {
		// JSON -> Map 변환
		ObjectMapper objectMapper = new ObjectMapper();
		String now_bang_id = "";

		String disconnectUser = null;
		// 사용자 세션을 리스트에서 제거
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> map = sessionList.get(i);
			String bang_id = (String) map.get("bang_id");
			String nickName = (String) map.get("nickName");
			WebSocketSession sess = (WebSocketSession) map.get("session");

			if (session.equals(sess)) {
				now_bang_id = bang_id;
				disconnectUser = nickName;
				sessionList.remove(map);
				break;
			}
		}
		// 같은 채팅방에 퇴장 메세지 전송
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			if (bang_id.equals(now_bang_id)) {
				Map<String, String> mapToSend = new HashMap<String, String>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", "GAME_ROOM_EXIT");
				mapToSend.put("msg", ConvertInputValue(disconnectUser) + "님이 퇴장 했습니다. " + dateTime);

				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}

}