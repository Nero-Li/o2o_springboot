package com.lym.exception;

/**
 * @ClassName ProductCategoryOperationException
 * @Description 商品类别操作异常
 * @Author lyming
 * @Date 2019/1/15 11:53 AM
 **/
public class ProductCategoryOperationException extends RuntimeException {


    private static final long serialVersionUID = 7320676268147508231L;

    /**
     * 商品类别操作异常信息
     */
    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
