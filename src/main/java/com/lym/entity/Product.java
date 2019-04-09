package com.lym.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
  * 商品
 * @author Administrator
 *
 */
@Data
public class Product {
    //主键id
	private Long productId;
    //商品名
	private String productName;
    //商品描述
	private String productDesc;
    //商品积分
    private Integer point;
	//缩略图
	private String imgAddr;
    //原价
	private String normalPrice;
    //现价(推广价格)
	private String promotionPrice;
    //权重(越高越靠前显示)
	private Integer priority;
    //创建时间
	private Date createTime;
    //修改时间
	private Date lastEditTime;
	//0:不可用 1:可用
	private Integer enableStatus;

	/**
	 * 详情图 一对多
	 */
	private List<ProductImg> productImgList;
	
	private ProductCategory productCategory;
	
	private Shop shop;


}
