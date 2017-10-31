package com.wsd.knowledge.controller;

import com.wsd.knowledge.util.ApiResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author EdsionGeng
 * @Description 设计用户模块界面交互层
 * @Date:9:53 2017/10/31
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("user")
public class UserController {

    @ApiOperation(value = "用户登录接口", notes = "传递用户名和密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "login.htmls", method = RequestMethod.POST)
    public String  login(HttpServletRequest req, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        req.getSession().setMaxInactiveInterval(3600);
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            //RememberMe这个参数设置为true后，在登陆的时候就会在客户端设置remenberme的相应cookie
            //token.setRememberMe(true);
            //存入Session
            req.getSession().setAttribute("username", username);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
           return "";
        }
    }
}
