package com.lym.entity;

import lombok.Data;

import java.util.Date;

/**
 * 店铺授权
 *
 * @ClassName ShopAuthMap
 * @Author lyming
 * @Date 2019/4/9 2:27
 **/
@Data
public class ShopAuthMap {
    //主键id
    private Long shopAuthId;
    //职称名
    private String title;
    //职称符号(可用于权限控制,保留字段)
    private Integer titleFlag;
    //授权有效状态, 0:无效 1:有效
    private Integer enableStatus;
    //创建时间
    private Date createTime;
    //最后一次更新时间
    private Date lastEditTime;
    //员工信息实体类
    private PersonInfo employee;
    //店铺实体类
    private Shop shop;
}
