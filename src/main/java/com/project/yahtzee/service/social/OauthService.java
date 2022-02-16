package com.project.yahtzee.service.social;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.project.yahtzee.util.constants.SocialLoginType;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OauthService {
	private final List<SocialOauth> socialOauthList;
	private final HttpServletResponse response;
	
	public void request(SocialLoginType socialLoginType) {
		SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken(code);
    }
    
    public MemberVO getUserInfo (SocialLoginType socialLoginType, String access_Token) {
    	SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
    	return socialOauth.getUserInfo(access_Token);
    }
    
    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
	
}