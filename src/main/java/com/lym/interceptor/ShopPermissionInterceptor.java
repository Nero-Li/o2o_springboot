package com.lym.interceptor;

import com.lym.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店家管理系统操作验证拦截器
 *
 * @ClassName ShopPermissionInterceptor
 * @Author lyming
 * @Date 2019/4/8 15:02
 **/
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取当前选择的店铺
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        @SuppressWarnings("unchecked")
        //从session中获取当前用户可操作性的店铺列表
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        //非空判断
        if (currentShop != null && shopList != null) {
            //遍历可操作性的店铺列表
            for (Shop shop : shopList) {
                //如果当前店铺在可操作列表里则返回true,允许下一步操作
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        //若不满足拦截器的验证则返回false,终止用户操作的执行
        return false;
    }
}
