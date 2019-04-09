package com.lym.exception;

/**
 * 店铺操作异常
 *
 * @ClassName ShopOperationException
 * @Author lyming
 * @Date 2019/1/6 23:10
 **/
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 714482677163069614L;

    /**
     * 店铺操作异常信息
     *
     * @param msg
     */
    public ShopOperationException(String msg) {
        super(msg);
    }
}
