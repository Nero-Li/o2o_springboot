package com.lym.exception;

/**
 * @ClassName 商品操作异常
 * @Description TODO
 * @Author lyming
 * @Date 2019/1/23 6:38 PM
 **/
public class ProductOperationException extends RuntimeException {

    private static final long serialVersionUID = 3568077489000323868L;

    /**
     * 商品操作异常信息
     *
     * @param message
     */
    public ProductOperationException(String message) {
        super(message);
    }
}
