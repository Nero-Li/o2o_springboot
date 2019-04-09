package com.lym.controller.local;

import com.lym.dto.LocalAuthExecution;
import com.lym.entity.LocalAuth;
import com.lym.entity.PersonInfo;
import com.lym.enums.LocalAuthStateEnum;
import com.lym.exception.LocalAuthOperationException;
import com.lym.service.LocalAuthService;
import com.lym.util.CodeUtil;
import com.lym.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoaclAuthController
 * @Author lyming
 * @Date 2019/4/7 15:31
 **/
@Controller
@RequestMapping(value = "local", method = {RequestMethod.GET, RequestMethod.POST})
public class LoaclAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台账号进行绑定
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //获取输入的账号信息
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //从session中获取当前用户信息(用户一旦通过微信登录后,便能获取到用户信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //非空判断,要求账号密码以及当前用户的session不能为null
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            //创建LocalAuth对象并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            //绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //获取账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        //从session中获取当前用户信息(用户一旦用微信登录后,便能获取到用户的信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //非空判断,要求账号新旧密码以及当前的用户session非空,且新旧密码不相同
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
                && !password.equals(newPassword)) {
            try {
                //查看原先账号,看看与输入的账号是否一致,不一致则认为是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUsername().equals(userName)) {
                    //不一致则直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的账号非本次登录的账号");
                    return modelMap;
                }
                //修改平台账号的用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        return modelMap;
    }

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginchek", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> longinCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //非空校验
        if (userName != null && password != null) {
            //传入账号密码去获取平台账号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                //若能获取到账号信息则登录成功
                modelMap.put("success", true);
                //同时把用户信息存在session中
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "账号密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 当用户点击登出按钮时后台注销session
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //将用户session置空
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }
}
