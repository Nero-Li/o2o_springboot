package com.lym.entity;

import lombok.Data;

import java.util.Date;

/**
 * 顾客消费的商品映射
 *
 * @ClassName UserProductMap
 * @Author lyming
 * @Date 2019/4/9 2:11
 **/
@Data
public class UserProductMap {
    //主键id
    private Long userProductId;
    //创建时间
    private Date createTime;
    //消费商品所获得的积分
    private Integer point;
    //顾客信息实体类
    private PersonInfo user;
    //商品信息实体类
    private Product product;
    //店铺信息实体类
    private Shop shop;
    //操作员信息实体类
    private PersonInfo operator;

}
