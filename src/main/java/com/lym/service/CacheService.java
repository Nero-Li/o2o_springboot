package com.lym.service;

public interface CacheService {
    /**
     * 依据key前缀删除匹配该模式下的所有key-value
     * 如:传入shopCategory,则以shopCategory打头的key-value全部清空
     *
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
