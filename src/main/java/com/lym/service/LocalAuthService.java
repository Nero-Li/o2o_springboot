package com.lym.service;

import com.lym.dto.LocalAuthExecution;
import com.lym.entity.LocalAuth;
import com.lym.exception.LocalAuthOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {
    /**
     * 通过账号和密码获取平台账号信息
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 通过userId获取平台账号信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 注册平台账号
     *
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution register(LocalAuth localAuth,
                                CommonsMultipartFile profileImg) throws LocalAuthOperationException;

    /**
     * 绑定微信,生成平台专属的账号
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws LocalAuthOperationException;

    /**
     * 修改平台账号的登录密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
