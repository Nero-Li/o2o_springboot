package com.lym.dao;

import com.lym.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  * 店铺Dao
 * @author lyming
 *
 */
public interface ShopDao {

    /**
     * 分页查询店铺,可输入的条件:店铺名(模糊),店铺状态,店铺类别,区域id,owner
     *
     * @param shopCondition
     * @param rowIndex      从第几行开始取数据
     * @param pageSize      每页大小
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     *
     * @param shopCondition
     * @return
     */
    Integer queryShopCount(@Param("shopCondition") Shop shopCondition);
	
	/**
	  * 新增店铺
	 * @param shop
	 */
	public int insertShop(Shop shop);

    /**
     * 更新店铺
     *
     * @param shop
     * @return
     */
    public int updateShop(Shop shop);
    
    /**
     * 根据shopId查询店铺
     * @param shopId
     * @return
     */
    public Shop queryByShopId(Long shopId);
	
}
