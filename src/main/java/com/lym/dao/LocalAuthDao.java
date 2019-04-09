package com.lym.dao;

import com.lym.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthDao {
    /**
     * 通过账号密码查询对应的信息,登录用
     *
     * @param userName
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName,
                                         @Param("password") String password);

    /**
     * 通过用户id查询对应的localauth
     *
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     * 新增账号
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 更新账号信息
     *
     * @param
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId,
                        @Param("userName") String userName,
                        @Param("password") String password,
                        @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}
