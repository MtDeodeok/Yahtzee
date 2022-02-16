package com.project.yahtzee.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.yahtzee.service.social.OauthService;
import com.project.yahtzee.util.constants.SocialLoginType;
import com.project.yahtzee.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth/*")
@Slf4j
public class OauthController {
    
	private final OauthService oauthService;
	
    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE, NAVER, KAKAO)
     */
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
		log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }
    
	/**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE, NAVER, KAKAO)
     * @param code API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */
    @GetMapping(value = "/{socialLoginType}/callback")
    public String callback(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType, @RequestParam(name = "code") String code,HttpServletRequest request) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String access_Token = oauthService.requestAccessToken(socialLoginType, code);
        HttpSession session = request.getSession();
        MemberVO memberVO = new MemberVO();
        memberVO = oauthService.getUserInfo(socialLoginType, access_Token);
        session.setAttribute("loginMember", memberVO);
        String htmlScript = "<script> location.href='/joiner' </script>";
        return htmlScript;
    }
    
	 
}