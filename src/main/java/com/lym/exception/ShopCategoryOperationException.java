package com.lym.exception;

/**
 * @ClassName ShopCategoryOperationException
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/5 5:33 PM
 **/
public class ShopCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = -4846186421097415000L;

    public ShopCategoryOperationException(String message) {
        super(message);
    }
}
