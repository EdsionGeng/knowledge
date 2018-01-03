package com.wsd.knowledge.controller;


import com.alibaba.fastjson.JSONObject;

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
import java.util.Map;

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


    @ApiOperation(value = "用户登录接口", notes = "传递用户名和密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "login.htmls", method = RequestMethod.POST)
    public JsonResult login(@RequestBody String object, HttpServletRequest req ) {
        req.getSession().setMaxInactiveInterval(3600);
        JSONObject jsonObject = JSONObject.parseObject(object);
        String username = String.valueOf(jsonObject.get("username"));
        String password = String.valueOf(jsonObject.get("password"));
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            System.out.println(token);
            SecurityUtils.getSubject().login(token);
            // RememberMe这个参数设置为true后，在登陆的时候就会在客户端设置remenberme的相应cookie
            Map map=(Map) SecurityUtils.getSubject().getPrincipal();
            token.setRememberMe(true);
            Integer isAdmin=userService.isAdmin(map.get("id"));
            return new JsonResult(0, map, "登录成功",isAdmin);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(2, 0, "登录失败", 0);
         }
    }

//    @ApiOperation(value = "查找人员接口", notes = "传递人员")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "String", name = "userGroupId", value = "部门", required = true)
//    })
//    @RequestMapping(value = "queryuser.htmls", method = RequestMethod.POST)
//    @ResponseBody
//    public JsonResult queryUserByGroup(@RequestBody String object) {
//        return userService.queryByGroupId(object);
//    }

    @ApiOperation(value = " 调取知识库所有管理员", notes = "传递人员")
    @RequestMapping(value = "queryadmin.htmls", method = RequestMethod.POST)
    public JsonResult queryAdmin() {
        return userService.queryAdmin();
    }

}
