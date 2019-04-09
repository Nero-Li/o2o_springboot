package com.lym.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lym.cache.JedisUtil;
import com.lym.dao.ShopCategoryDao;
import com.lym.entity.ShopCategory;
import com.lym.exception.ShopCategoryOperationException;
import com.lym.service.ShopCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;


	@Override
    @Transactional
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        //定义redis的key前缀
        String key = SCLISTKEY;
        //定义接收对象
        List<ShopCategory> shopCategoryList = null;
        //定义jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出redis的key
        if (shopCategoryCondition == null) {
            //若查询条件为null,则列出所有首页大类,即parentId为null的店铺类别
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
                && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            //若parentId不为null,则列出该parentId下所有子类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            key = key + "_allsecondlevel";
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)) {
            //若不存在,则从数据库里面取出相应数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            //将相关实体类集合转换成String,并存入redis
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(shopCategoryList);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            //若存在,则直接从redis里面取相应数据
            String jsonString = jedisStrings.get(key);
            //指定要将string转换成的集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                //将key对应的value里的String转换成对象的实体类集合
                shopCategoryList = objectMapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
	}

}
