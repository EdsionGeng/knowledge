package com.wsd.knowledge.controller;


import com.alibaba.fastjson.JSONObject;

import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.service.UserService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author EdsionGeng
 * @Description 设计用户模块界面交互层
 * @Date:9:53 2017/10/31
 */
@RestController
@EnableAutoConfiguration
@Api(description = "用户类接口", value = "用户类接口")
public class UserController {
    @Autowired
    private UserService userService;
    JSONObject jsonObject = null;

    @ApiOperation(value = "用户登录接口", notes = "传递用户名和密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "login.htmls", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(HttpServletRequest req, @RequestBody String object) {
        req.getSession().setMaxInactiveInterval(3600);
//        SystemUser systemUser = userService.login(username, HashAlorgithum.getSHA256StrJava(password));
//        if (systemUser != null) {
//            return new JsonResult(0,0,"登录成功",0);
//        }
//       return new JsonResult(2,0,"登录失败",0);
        jsonObject = JSONObject.parseObject(object);
        String username = String.valueOf(jsonObject.get("username"));
        String password = String.valueOf(jsonObject.get("password"));
        SystemUser systemUser = userService.login(username, password);
        if (systemUser != null) {
            return new JsonResult(0, systemUser, "登陆成功", 0);
        }
            return new JsonResult(2, 0, "登陆失败", 0);
//        try {
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            System.out.println(token);
//            SecurityUtils.getSubject().login(token);
//            // RememberMe这个参数设置为true后，在登陆的时候就会在客户端设置remenberme的相应cookie
//            token.setRememberMe(true);
//            //存入Session
////            req.getSession().setAttribute("username", username);
//            return new JsonResult(0, 0, "登录成功", 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JsonResult(2, 0, "登录失败", 0);
        // }
    }

    @ApiOperation(value = "查找人员接口", notes = "传递人员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userGroupId", value = "部门", required = true)
    })
    @RequestMapping(value = "queryuser.htmls", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryUserByGroup(@RequestBody String object) {
        return userService.queryByGroupId(object);
    }

    @ApiOperation(value = " 调取知识库所有管理员", notes = "传递人员")
    @RequestMapping(value = "queryadmin.htmls", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryAdmin() {
        return userService.queryAdmin();
    }

}
