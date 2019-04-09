package com.lym.exception;

/**
 * @ClassName WechatAuthOeprationException
 * @Author lyming
 * @Date 2019/3/31 23:49
 **/
public class WechatAuthOeprationException extends RuntimeException {

    private static final long serialVersionUID = 3525097331421885753L;

    public WechatAuthOeprationException(String message) {
        super(message);
    }
}
