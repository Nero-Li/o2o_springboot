package com.lym.dto;

import com.lym.entity.Product;
import com.lym.enums.ProductStateEnum;

import java.util.List;

/**
 * @ClassName ProductExecution
 * @Description 商品处理包装类
 * @Author lyming
 * @Date 2019/1/23 6:36 PM
 **/
public class ProductExecution {

    /**
     * 查询状态
     */
    private int state;

    /**
     * 查询标识
     */
    private String stateInfo;

    /**
     * 商品数量
     */
    private int count;

    /**
     * 操作的Product(增删改商品时用)
     */
    private Product product;

    /**
     * 获取的商品列表(查询列表时用)
     */
    private List<Product> productList;

    public ProductExecution() {
    }

    /**
     * 失败时用的构造器
     *
     * @param productStateEnum
     */
    public ProductExecution(ProductStateEnum productStateEnum) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
    }

    /**
     * 成功时用的构造器
     **/
    public ProductExecution(ProductStateEnum productStateEnum, Product product) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }

    /**
     * 成功时用的构造器
     **/
    public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.productList = productList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
