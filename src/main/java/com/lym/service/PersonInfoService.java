package com.lym.service;

import com.lym.entity.PersonInfo;

public interface PersonInfoService {

    /**
     * 根据用户id获取PersonInfo信息
     *
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById(Long userId);
}
