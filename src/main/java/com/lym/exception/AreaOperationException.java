package com.lym.exception;

/**
 * @ClassName AreaOperationException
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/3 9:39 PM
 **/
public class AreaOperationException extends RuntimeException {


    private static final long serialVersionUID = -1243902659782495261L;

    public AreaOperationException(String msg) {
        super(msg);
    }
}
