package com.lym.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 因为html放在WEB-INF里,不能直接访问,需要通过SpringMVC路由
 */
@Controller
@RequestMapping(value = "shopadmin", method = RequestMethod.GET)
public class ShopAdminController {

    /**
     * 跳转至店铺注册/编辑页面
     *
     * @return
     */
    @RequestMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    /**
     * 跳转至店铺列表页面
     * @return
     */
    @RequestMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    /**
     * 跳转至商品管理页面
     * @return
     */
    @RequestMapping("/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    /**
     * 跳转至商品类别管理页面
     * @return
     */
    @RequestMapping("/productcategorymanagement")
    public String productCategoryManagement() {
        return "shop/productcategorymanagement";
    }

    /**
     * 跳转至商品添加/编辑页面
     *
     * @return
     */
    @RequestMapping("/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }

    /**
     * 跳转至商品管理页面
     * @return
     */
    @RequestMapping(value = "/productmanagement", method = RequestMethod.GET)
    public String productManage() {
        return "shop/productmanagement";
    }

}
