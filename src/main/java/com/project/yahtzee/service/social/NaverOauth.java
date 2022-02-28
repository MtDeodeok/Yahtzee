package com.project.yahtzee.service.social;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.project.yahtzee.handler.WebSocketHandler;
import com.project.yahtzee.vo.MemberVO;

@Component
public class NaverOauth implements SocialOauth {

	private final String NAVER_SNS_BASE_URL = "https://nid.naver.com/oauth2.0/authorize";
	private final String NAVER_SNS_CLIENT_ID = "RfGcCZnltonIYA14PB0U";
	private final String NAVER_SNS_CLIENT_SECRET = "C6wjkh1DR8";
	private final String NAVER_SNS_CALLBACK_URL = "http://localhost:8084/auth/naver/callback";
	private final String NAVER_SNS_TOKEN_BASE_URL = "https://nid.naver.com/oauth2.0/token";
	private final String NAVER_SNS_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
	private final String NAVER_SNS_STATE = "STATE_STRING";
	
	private final MemberVO memberVO = new MemberVO();
	
	@Override
	public String getOauthRedirectURL() {

		Map<String, Object> params = new HashMap<>();

		params.put("response_type", "code");
		params.put("client_id", NAVER_SNS_CLIENT_ID);
		params.put("state", NAVER_SNS_STATE);
		params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);

		String parameterString = params.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
				.collect(Collectors.joining("&"));

		return NAVER_SNS_BASE_URL + "?" + parameterString;
	}

	@Override
	public String requestAccessToken(String code) {
		String access_Token = "";
		try {
			URL url = new URL(NAVER_SNS_TOKEN_BASE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setDoOutput(true);

			Map<String, Object> params = new HashMap<>();
			params.put("code", code);
			params.put("client_id", NAVER_SNS_CLIENT_ID);
			params.put("client_secret", NAVER_SNS_CLIENT_SECRET);
			params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);
			params.put("state", NAVER_SNS_STATE);
			params.put("grant_type", "authorization_code");

			String parameterString = params.entrySet().stream().map(x -> x.getKey() + "=" + x.getValue())
					.collect(Collectors.joining("&"));

			BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
			bous.write(parameterString.getBytes());
			bous.flush();
			bous.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String line;
			String result = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				result += line;
			}
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			if (conn.getResponseCode() == 200) {
				return access_Token;
			}
			return "네이버 로그인 요청 처리 실패";
		} catch (IOException e) {
			throw new IllegalArgumentException("알 수 없는 로그인 Access Token 요청 URL 입니다 :: " + NAVER_SNS_TOKEN_BASE_URL);
		}
	}

	@Override
	public MemberVO getUserInfo(String access_Token) {

		HashMap<String, Object> naverUserInfo = new HashMap<>();
		try {
			URL url = new URL(NAVER_SNS_PROFILE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();

			if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}

				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);
				JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();

				String name = response.getAsJsonObject().get("name").getAsString();
				String email = response.getAsJsonObject().get("email").getAsString();
				String profileImg = response.getAsJsonObject().get("profile_image").getAsString();

				naverUserInfo.put("name", name);
				naverUserInfo.put("email", email);
				naverUserInfo.put("profileImg", profileImg);
				
				memberVO.setUserName(name);
				memberVO.setUserProvider("Naver");
				memberVO.setUserEmail(email);
				memberVO.setUserProfile(profileImg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return memberVO;

	}
}