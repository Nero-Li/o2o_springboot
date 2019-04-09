package com.lym.exception;

/**
 * @ClassName LocalAuthOperationException
 * @Author lyming
 * @Date 2019/4/7 14:23
 **/
public class LocalAuthOperationException extends RuntimeException {

    private static final long serialVersionUID = 3000794874918670615L;

    public LocalAuthOperationException(String message) {
        super(message);
    }
}
