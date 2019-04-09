package com.lym.entity;

import lombok.Data;

import java.util.Date;

/**
 * 顾客已领取的奖品映射
 *
 * @ClassName UserAwardMap
 * @Author lyming
 * @Date 2019/4/9 1:54
 **/
@Data
public class UserAwardMap {

    //主键id
    private Long userAwardId;
    //创建时间
    private Date createTime;
    //使用状态 0:未兑换 1:已兑换
    private Integer userStatus;
    //领取奖品所消耗的积分
    private Integer point;
    //顾客信息实体类
    private PersonInfo user;
    //奖品信息实体类
    private Award award;
    //店铺信息实体类
    private Shop shop;
    //操作员信息实体类
    private PersonInfo operator;


}
