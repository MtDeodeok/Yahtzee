package com.project.yahtzee.util.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import com.project.yahtzee.util.constants.SocialLoginType;


@Configuration
public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {
	@Override
	public SocialLoginType convert(String s) {
		return SocialLoginType.valueOf(s.toUpperCase());
	}
}