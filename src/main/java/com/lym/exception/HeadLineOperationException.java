package com.lym.exception;

/**
 * @ClassName HeadLineOperationException
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/5 1:56 PM
 **/
public class HeadLineOperationException extends RuntimeException {


    private static final long serialVersionUID = 7295952877435434850L;

    public HeadLineOperationException(String message) {
        super(message);
    }
}
