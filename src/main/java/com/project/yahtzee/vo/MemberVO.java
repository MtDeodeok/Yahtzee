package com.project.yahtzee.vo;

import lombok.Data;

@Data
public class MemberVO {
	int idx;
	String userID;
	String userName;
	String userNickName;
	String userEmail;
	String userProfile;
	String userProvider;
	int userState;
}
