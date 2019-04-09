package com.lym.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lym.cache.JedisUtil;
import com.lym.dao.HeadLineDao;
import com.lym.entity.HeadLine;
import com.lym.exception.HeadLineOperationException;
import com.lym.service.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HeadLineServiceImpl
 * @Description TODO
 * @Author lyming
 * @Date 2019/3/14 10:05 PM
 **/
@Service
@Slf4j
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;


    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        //定义redis的key前缀
        String key = HLLISTKEY;
        //定义接受对象
        List<HeadLine> headLineList = null;
        //定义jackson转换对象
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出redis的key
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        //判断redis中key是否存在
        if (!jedisKeys.exists(key)) {
            //若不存在,则从数据库中取出数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            //将相关实体类转换成json字符串存入redis中
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(headLineList);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            //如果存在,则直接从redis中获取数据
            String jsonString = jedisStrings.get(key);
            //将jsonString转换成对应的集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLineList = objectMapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
