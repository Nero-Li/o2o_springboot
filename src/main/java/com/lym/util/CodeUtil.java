package com.lym.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {

	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//将输入的验证码全部转换成大写,Kaptcha给定的验证码字符在web.xml里设置的是大写字母
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual").toUpperCase();
		if(verifyCodeActual==null||!verifyCodeExpected.equals(verifyCodeActual)) {
			return false;
		}
		return true;
	}
}
