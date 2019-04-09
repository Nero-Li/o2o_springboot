package com.lym.controller.frontend;

import com.lym.dto.ShopExcution;
import com.lym.entity.Area;
import com.lym.entity.Shop;
import com.lym.entity.ShopCategory;
import com.lym.service.AreaService;
import com.lym.service.ShopCategoryService;
import com.lym.service.ShopService;
import com.lym.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ShopListController
 * @Author lyming
 * @Date 2019/3/18 22:20
 **/
@Controller
@RequestMapping("/frontend")
@Slf4j
public class ShopListController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/listshoppageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //从前端获取parentId
        Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1L) {
            //如果parentId存在,则取出该一级shopCategory下的二级ShopCategory列表\
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            try {
                //如果parentid不存在,则取出所有一级ShopCategoty(用户在首页选择的是全部商品列表)
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            //获取区域列表信息
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的店铺列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listshops", method = RequestMethod.GET)
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //非空判断
        if (pageIndex > -1 && pageSize > -1) {
            //试着获取一级类别id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //试着获取特定二级类表id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //试着获取区域id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //组合查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            //根据查询条件和分页信息获取店铺列表,并返回总数
            ShopExcution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or PageIndex");
        }
        return modelMap;
    }

    /**
     * 组合查询条件,并将条件封装到ShopCondition对象里面返回
     *
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            //查询某一级ShopCategory下面所有的二级ShopCategory里面的店铺列表
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            //查询某个二级shopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            //查询某个区域id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            //查询名字里包括shopName的店铺列表
            shopCondition.setShopName(shopName);
        }
        //前端展示的都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
