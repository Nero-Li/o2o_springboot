package com.lym.service.impl;

import com.lym.cache.JedisUtil;
import com.lym.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @ClassName CacheServiceImpl
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/5 9:35 PM
 **/
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keyStrings = jedisKeys.keys(keyPrefix + "*");
        for (String key : keyStrings) {
            jedisKeys.del(key);
        }
    }
}
