package com.lym.service.impl;

import com.lym.dao.PersonInfoDao;
import com.lym.entity.PersonInfo;
import com.lym.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PersonInfoServiceImpl
 * @Author lyming
 * @Date 2019/4/1 0:21
 **/
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfoById(userId);
    }
}
