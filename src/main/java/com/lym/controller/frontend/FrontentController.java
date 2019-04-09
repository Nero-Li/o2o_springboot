package com.lym.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 前端总路由
 * @ClassName FrontentController
 * @Author lyming
 * @Date 2019/3/18 1:22
 **/
@Controller
@RequestMapping("/frontend")
public class FrontentController {

    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    private String showMainPage() {
        return "frontend/mainpage";
    }

    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    private String showShopDetail() {
        return "frontend/shopdetail";
    }

    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    private String showShopList() {
        return "frontend/shoplist";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }

    @RequestMapping(value = "/mypoint", method = RequestMethod.GET)
    private String myPoint() {
        return "frontend/mypoint";
    }

    @RequestMapping(value = "/myrecord", method = RequestMethod.GET)
    private String myRecord() {
        return "frontend/myrecord";
    }

    @RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
    private String pointRecord() {
        return "frontend/pointrecord";
    }

    @RequestMapping(value = "/awarddetail", method = RequestMethod.GET)
    private String awardDetail() {
        return "frontend/awarddetail";
    }

    @RequestMapping(value = "/customerbind", method = RequestMethod.GET)
    private String customerBind() {
        return "frontend/customerbind";
    }
}
