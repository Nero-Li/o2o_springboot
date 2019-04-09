package com.lym.service;

import com.lym.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * 头条相关service
 */
public interface HeadLineService {

    /**
     * redis的key前缀
     */
    public static String HLLISTKEY = "headlinelist";
    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
