package com.lym.dto;

import com.lym.entity.ProductCategory;
import com.lym.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * @ClassName ProductCategoryExecution
 * @Description 商品类别返回DTO
 * @Author lyming
 * @Date 2019/1/15 11:47 AM
 **/
public class ProductCategoryExecution {

    private int state;

    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    /**
     * 操作失败时使用的构造器
     */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 操作成功时使用的构造器
     */
    public ProductCategoryExecution(List<ProductCategory> productCategoryList, ProductCategoryStateEnum stateEnum) {
        this.productCategoryList = productCategoryList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
