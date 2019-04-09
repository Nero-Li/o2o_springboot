package com.lym.service;

import com.lym.dto.WechatAuthExecution;
import com.lym.entity.WechatAuth;
import com.lym.exception.WechatAuthOeprationException;

public interface WechatAuthService {
    /**
     * 通过openId查找平台对应的微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 注册本平台的微信账号
     *
     * @param wechatAuth
     * @return
     * @throws RuntimeException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOeprationException;
}
