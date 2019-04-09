package com.lym.dao;

import com.lym.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName HeadLine
 * @Description TODO
 * @Author lyming
 * @Date 2019/3/12 8:54 PM
 **/
public interface HeadLineDao {

    /**
     * 根据传入的查询条件(头条名查询头条)
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 根据id查询头条信息
     *
     * @param lineId
     * @return
     */
    HeadLine queryHeadLineById(long lineId);

    /**
     * @param lineIdList
     * @return
     */
    List<HeadLine> queryHeadLineByIds(List<Long> lineIdList);

    /**
     * 新增头条信息
     *
     * @param headLine
     * @return
     */
    int insertHeadLine(HeadLine headLine);

    /**
     * 更新头条信息
     *
     * @param headLine
     * @return
     */
    int updateHeadLine(HeadLine headLine);

    /**
     * 删除单个头条
     *
     * @param headLineId
     * @return
     */
    int deleteHeadLine(long headLineId);

    /**
     * 批量删除头条
     *
     * @param lineIdList
     * @return
     */
    int batchDeleteHeadLine(List<Long> lineIdList);
}
