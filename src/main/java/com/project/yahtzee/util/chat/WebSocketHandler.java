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
import com.project.yahtzee.util.GameBoard;
import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.vo.MemberVO;
import com.project.yahtzee.vo.YahtzeeBoardVO;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private MemberVO member = new MemberVO();
	private Dice dice = new Dice();
	private GameBoard gameScoreBoard = new GameBoard();

	// 세션 리스트
	private List<Map<String, Object>> sessionList = new ArrayList<Map<String, Object>>();
	
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
		Map<String, Object> mapDiceReceive = objectMapper.readValue(message.getPayload(), Map.class);
		List<Integer> dices = new ArrayList<Integer>();
		switch (mapReceive.get("cmd")) {

		// CLIENT 입장
		case "GAME_ROOM_ENTER":
			gameRoomEnter(mapReceive, session, objectMapper);
			break;
		// CLIENT 메세지
		case "USER_CHAT_MSG":
			userChatMsg(mapReceive, session, objectMapper);
			break;
			
		// 보드 게임 관련 부분
		case "GAME_START":
			returnDiceList(mapReceive, session, objectMapper, dice.firstDiceList());
			break;
		case "FIXED_DICE_ROLLING":
			int num = (int) ((HashMap)mapDiceReceive.get("dices_lock")).get("dices_lock");
			returnDiceList(mapReceive, session, objectMapper, dice.fixedDice(num));
			System.out.println("다이스 롤링");
			break;
		case "ONES":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.ones(dices));
			System.out.println("ones!");
			break;
		case "TWOS":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.twos(dices));
			System.out.println("twos!");
			break;
		case "THREES":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.threes(dices));
			System.out.println("threes!");
			break;
		case "FOURS":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.fours(dices));
			System.out.println("fours!");
			break;
		case "FIVES":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.fives(dices));
			System.out.println("fives!");
			break;
		case "SIXS":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.sixs(dices));
			System.out.println("sixs!");
			break;
		case "CHOICE":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.choice(dices));
			System.out.println("choice!");
			break;
		case "ONEPAIR":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.onePair(dices));
			System.out.println("onePair!");
			break;
		case "TWOPAIR":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.twoPair(dices));
			System.out.println("twoPair!");
			break;
		case "THREEOFAKIND":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.threeOfAKind(dices));
			System.out.println("threeOfAKind!");
			break;
		case "FOUROFAKIND":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.fourOfAKind(dices));
			System.out.println("fourOfAKind!");
			break;
		case "FULLHOUSE":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.fullHouse(dices));
			System.out.println("fullHouse!");
			break;
		case "SMALLSTRAIGHT":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.smallStraight(dices));
			System.out.println("smallStraight!");
			break;
		case "LARGESTRAIGHT":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.largeStraight(dices));
			System.out.println("largeStraight!");
			break;
		case "YAHTZEE":
			dices = (List<Integer>) ((Map)(mapDiceReceive.get("diceList"))).get("dices");
			returnScore(mapReceive, session, objectMapper,gameScoreBoard.yahtzee(dices));
			System.out.println("yahtzee!");
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
	
	// 주사위 굴리기
	private void returnDiceList(Map<String, String> mapReceive, WebSocketSession session, ObjectMapper objectMapper, List<Integer> diceList) throws IOException {
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			if (bang_id.equals(mapReceive.get("bang_id"))) {
				Map<String, Object> mapToSend = new HashMap<String, Object>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", "DICE_ROLLING");
				mapToSend.put("diceList", diceList);
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}
	
	// 점수 리턴
	private void returnScore(Map<String, String> mapReceive, WebSocketSession session, ObjectMapper objectMapper,int score ) throws IOException {
		for (int i = 0; i < sessionList.size(); i++) {
			Map<String, Object> mapSessionList = sessionList.get(i);
			String bang_id = (String) mapSessionList.get("bang_id");
			WebSocketSession sess = (WebSocketSession) mapSessionList.get("session");
			if (bang_id.equals(mapReceive.get("bang_id"))) {
				Map<String, Object> mapToSend = new HashMap<String, Object>();
				mapToSend.put("bang_id", bang_id);
				mapToSend.put("cmd", mapReceive.get("cmd"));
				mapToSend.put("score", score);
				String jsonStr = objectMapper.writeValueAsString(mapToSend);
				sess.sendMessage(new TextMessage(jsonStr));
			}
		}
	}
}