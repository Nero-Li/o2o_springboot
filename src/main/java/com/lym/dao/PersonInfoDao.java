package com.lym.dao;

import com.lym.entity.PersonInfo;

public interface PersonInfoDao {

    /**
     * 通过用户id查询用户
     *
     * @param userid
     * @return
     */
    PersonInfo queryPersonInfoById(long userid);

    /**
     * 添加用户信息
     *
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);
}
