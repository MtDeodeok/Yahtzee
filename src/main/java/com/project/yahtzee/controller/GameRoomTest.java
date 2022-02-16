package com.project.yahtzee.controller;

import com.project.yahtzee.util.gameRoom.GameRoom;
import com.project.yahtzee.util.gameRoom.GameUser;
import com.project.yahtzee.util.gameRoom.RoomManager;

public class GameRoomTest {

    public void roomCreateTest(){

        // #1. 유저 로그인
    	// 유저 로그인 시 정보 넣을 공간. 
    	// DB에서 idx와 닉네임 또는 유저 id 넣을지 생각 중
        GameUser gameUser = new GameUser(1, "gompang");
        GameUser gameUser2 = new GameUser(2, "apple");
        GameUser gameUser3 = new GameUser(3, "banana");

        // #2. 특정 유저가 방을 만드려고 함
        GameRoom gameRoom = RoomManager.createRoom(gameUser);
        System.out.println("owner : "+gameRoom.getOwner().getNickName());
        // #3. 그 방에 나머지 유저가 들어가려고 함
        gameRoom.enterUser(gameUser2);
        gameRoom.enterUser(gameUser3);

        System.out.println("userList : "+gameRoom.getUserList());
        // -- 로직 진행~ 게임, 채팅 등 --

        // #4. 유저가 방에서 나감
        gameRoom.exitUser(gameUser2);
        System.out.println("user2 exit userList : "+gameRoom.getUserList());
        // #5. 방장이 방에서 나감 -> gameUser3이 방장이 됨
        gameRoom.exitUser(gameUser);
        System.out.println("user1 exit userList : "+gameRoom.getUserList());
        System.out.println("owner : "+gameRoom.getOwner().getNickName());
        
        // #6. gameUser3이 방에서 나감 -> size가 0이 되면서 방이 없어짐
        gameRoom.exitUser(gameUser3);
        System.out.println("user3 exit userList : "+gameRoom.getUserList());
    
        System.out.println("gameRoom : "+gameRoom);
        // #7. 아래 결과는 null 이겠지
        GameRoom room = RoomManager.getRoom(gameRoom);
        
        System.out.println("room : "+room);
        if(room != null){
            System.out.println("방이 아직 있네");
        }
        else{
            System.out.println("방이 없어졌네");
        }
    }

    public void roomTest(){
        // #1. 유저 로그인
        GameUser gameUser = new GameUser(1, "gompang");
        GameUser gameUser2 = new GameUser(2, "apple");
        GameUser gameUser3 = new GameUser(3, "banana");

        // #2. 특정 유저가 방을 만드려고 함
        GameRoom gameRoom = RoomManager.createRoom(gameUser);

        // #3. 그 방에 나머지 유저가 들어가려고 함
        gameRoom.enterUser(gameUser2);
        gameRoom.enterUser(gameUser3);

        // -- 방 모든유저에게 데이터 전송 --
        byte[] data = "방 유저에게 broadcast할 데이터".getBytes();
        gameRoom.broadcast(data);

        // #4. 누군가가 gompang이라는 닉네임으로 방의 유저를 검색함(귓속말, 거래 등의 목적으로)
        GameUser gompang = gameRoom.getUserByNickName("gompang");

        // #5. 서버에서 무언가의 이유로 gameRoom을 삭제함(모든 유저 퇴장처리)
        RoomManager.removeRoom(gameRoom);
    }

}
