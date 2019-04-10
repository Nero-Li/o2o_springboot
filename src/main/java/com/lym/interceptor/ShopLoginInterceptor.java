package com.lym.interceptor;

import com.lym.entity.PersonInfo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 店家管理系统登录验证拦截器
 *
 * @ClassName ShopLoginInterceptor
 * @Author lyming
 * @Date 2019/4/8 14:09
 **/
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 主要做事前兰倩,即用户操作发生之前,改写prehandle里的逻辑,进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session里取出用户信息
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            //若用户信息不为空,则将session里的用户信息转换成PersonInfo实体类对象
            PersonInfo user = (PersonInfo) userObj;
            //空值判断,确保userId不为空并且该账号的可用状态为1,且用户类型为2
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1) {
                //若通过验证则返回true,拦截器返回true之后,用户接下来的操作正常执行
                return true;
            }
        }
        //若不满足登录验证,则直接跳转到账号登录页面
        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<script>");
        out.print("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        out.print("</script>");
        out.print("</html>");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
