package com.lym.dao;

import com.lym.entity.WechatAuth;

public interface WechatAuthDao {

    /**
     * 通过openId查询对应本平台的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     * 添加对应本平台的微信账号
     *
     * @param wechatAuth
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);
}
