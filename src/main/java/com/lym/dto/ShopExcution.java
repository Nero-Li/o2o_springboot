package com.lym.dto;

import com.lym.entity.Shop;
import com.lym.enums.ShopStateEnum;

import java.util.List;

/**
 * @ClassName ShopExcution
 * @Author lyming
 * @Date 2019/1/6 18:12
 **/
public class ShopExcution {

    /**
     * 结果状态
     */
    private int state;

    /**
     * 状态标识
     */
    private String stateInfo;

    /**
     * 店铺数量
     */
    private int count;

    /**
     * 操作的Shop(增删改的时候用到)
     */
    private Shop shop;

    /**
     * Shop列表(查询店铺列表的时候用)
     */
    private List<Shop> shopList;

    public ShopExcution() {
    }

    /**
     * 针对失败情况,只返回状态
     *
     * @param stateEnum
     */
    public ShopExcution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 成功后返回对象
     *
     * @param stateEnum
     * @param Shop
     */
    public ShopExcution(ShopStateEnum stateEnum, Shop Shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = Shop;
    }

    /**
     * 成功后返回列表
     *
     * @param stateEnum
     * @param shopList
     */
    public ShopExcution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
