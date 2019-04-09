package com.lym.service.impl;

import com.lym.dao.ShopDao;
import com.lym.dto.ImageHolder;
import com.lym.dto.ShopExcution;
import com.lym.entity.Shop;
import com.lym.enums.ShopStateEnum;
import com.lym.exception.ShopOperationException;
import com.lym.service.ShopService;
import com.lym.util.ImageUtil;
import com.lym.util.PageCalculator;
import com.lym.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ShopServiceImpl
 * @Author lyming
 * @Date 2019/1/6 19:24
 **/
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExcution addShop(Shop shop, ImageHolder imageHolder) {
        // 空值判断
        if (shop == null) {
            return new ShopExcution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (imageHolder.getImage() != null) {
                    try {
                        // 存储图片
                        addShopImg(shop, imageHolder);
                    } catch (Exception e) {
                        throw new ShopOperationException("add shopImg error:" + e.getMessage());
                    }
                    // 更新店铺图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            /**
             * 只有抛出RuntimeException,事务才会生效,Exception不会回滚,不抛也不会回滚
             */
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExcution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, ImageHolder imageHolder) {
        // 获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnal(imageHolder, dest);
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExcution updateShop(Shop shop, ImageHolder imageHolder)
            throws ShopOperationException {

        if (shop == null || null == shop.getShopId()) {
            return new ShopExcution(ShopStateEnum.NULL_SHOP);
        }
        /**
         * 1.判断是否需要处理图片
         */
        try {
            if (imageHolder.getImage() != null && imageHolder.getImageName() != null && !"".equals(imageHolder.getImageName())) {
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                if (null != tempShop.getShopImg()) {
                    ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                }
                addShopImg(shop, imageHolder);
            }
            /** 2.更新店铺信息 */
            shop.setLastEditTime(new Date());
            int effctiveNum = shopDao.updateShop(shop);
            if (effctiveNum <= 0) {
                return new ShopExcution(ShopStateEnum.INNER_ERROR);
            } else {
                shop = shopDao.queryByShopId(shop.getShopId());
                return new ShopExcution(ShopStateEnum.SUCCESS, shop);
            }
        } catch (Exception e) {
            throw new ShopOperationException("updateShop error:" + e.getMessage());
        }
    }

    @Override
    public ShopExcution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExcution se = new ShopExcution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }
}
