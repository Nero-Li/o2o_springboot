package com.lym.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName LocalController
 * @Author lyming
 * @Date 2019/4/7 20:53
 **/
@Controller
@RequestMapping("/local")
public class LocalController {

    /**
     * 绑定账号路由
     *
     * @return
     */
    @RequestMapping(value = "/accountbind", method = RequestMethod.GET)
    private String accountbind() {
        return "local/accountbind";
    }

    /**
     * 修改密码页面路由
     *
     * @return
     */
    @RequestMapping(value = "/changepsw", method = RequestMethod.GET)
    private String changepwd() {
        return "/local/changepsw";
    }

    /**
     * 登录页路由
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        return "/local/login";
    }
}
