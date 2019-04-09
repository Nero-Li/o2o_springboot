package com.lym.service.impl;

import com.lym.dao.LocalAuthDao;
import com.lym.dto.LocalAuthExecution;
import com.lym.entity.LocalAuth;
import com.lym.enums.LocalAuthStateEnum;
import com.lym.exception.LocalAuthOperationException;
import com.lym.service.LocalAuthService;
import com.lym.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

/**
 * @ClassName LocalAuthServiceImpl
 * @Author lyming
 * @Date 2019/4/7 14:24
 **/
@Service
@Slf4j
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) throws LocalAuthOperationException {
        return null;
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        //空值判断,传入的localauth账号密码,用户信息,特别是userId不能为空,否则直接返回错误信息
        if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
                || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        //查询此用户之前是否已经绑定过平台账号
        LocalAuth templocalAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (templocalAuth != null) {
            //如果绑定过,则直接退出,以保证平台账号唯一性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        //如果之前没有绑定过平台账号,则创建一个平台账号与该用户信息绑定
        try {
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            //对密码进行MD5加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectNum = localAuthDao.insertLocalAuth(localAuth);
            //判断是否创建成功
            if (effectNum <= 0) {
                throw new LocalAuthOperationException("账号创建失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
            }

        } catch (Exception e) {
            throw new LocalAuthOperationException("insert localAuth error:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
        //非空判断,判断传入的用户Id,账号,新旧密码是否为null,新旧密码是否相同,若不满足则返回错误信息
        if (userId != null && userName != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                //更新密码,对新密码行进md5加密
                int effectedNum = localAuthDao.updateLocalAuth(userId,
                        userName, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                //判断是否更新成功
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
