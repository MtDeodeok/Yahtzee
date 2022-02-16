package com.project.yahtzee.util.gameRoom;
import java.net.Socket;

// 실제로 게임을 플레이하는 유저의 클래스이다.

public class GameUser {

	private int id; 			// Unique ID
	private String nickName;	// 닉네임 : 유저ID로 바꿀지 고민 해볼 것
	private GameRoom room; 		// 유저가 속한 룸이다.
	private Socket sock; 		// 소켓 object

	// 게임에 관련된 변수 설정
	// ...
	//

	private PlayerGameInfo.Location playerLocation; // 게임 정보
	private PlayerGameInfo.Status playerStatus; // 게임 정보

	public GameUser() { // 아무런 정보가 없는 깡통 유저를 만들 때
	}

    /**
     * 유저 생성
     * @param nickName 닉네임
     */
    public GameUser(String nickName) { // 닉네임 정보만 가지고 생성
		this.nickName = nickName;
	}

    /**
     * 유저 생성
     * @param id ID
     * @param nickName 닉네임
     */
    public GameUser(int id, String nickName) { // UID, 닉네임 정보를 가지고 생성
		this.id = id;
		this.nickName = nickName;
	}

    /**
     * 방에 입장시킴
     * @param room  입장할 방
     */
    public void enterRoom(GameRoom room) {
		this.room = room; // 유저가 속한 방을 룸으로 변경한다.(중요)
	}

    /**
     * 방에서 퇴장
     * @param room 퇴장할 방
     */
    public void exitRoom(GameRoom room){
        this.room = null;
        // 퇴장처리(화면에 메세지를 준다는 등)
        // ...
    }

	public void setPlayerStatus(PlayerGameInfo.Status status) { // 유저의 상태를 설정
		this.playerStatus = status;
	}

	public void setPlayerLocation(PlayerGameInfo.Location location) { // 유저의 위치를 설정
		this.playerLocation = location;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameRoom getRoom() {
        return room;
    }

    public void setRoom(GameRoom room) {
        this.room = room;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public PlayerGameInfo.Location getPlayerLocation() {
        return playerLocation;
    }

    public PlayerGameInfo.Status getPlayerStatus() {
        return playerStatus;
    }

    /*
        equals와 hashCode를 override 해줘야, 동일유저를 비교할 수 있다
        비교할 때 -> gameUser 간 equals 비교, list에서 find 등
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameUser gameUser = (GameUser) o;

        return id == gameUser.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
