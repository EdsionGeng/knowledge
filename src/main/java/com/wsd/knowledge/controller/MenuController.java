package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.Menu;
import com.wsd.knowledge.service.MenuService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@EnableAutoConfiguration
public class MenuController {


    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单
     *
     * @param
     * @return
     */
    @RequestMapping(value="getMenus.htmls",method = RequestMethod.POST)
    @ApiOperation(value = "得到用户菜单", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "userId", value = "用户"),

    })
    public JsonResult getMenusList(@RequestBody String object,HttpServletRequest request) {
//        List<Menu> list = (List<Menu>) request.getSession().getAttribute("menus");
//        if (list != null) {
//            return new JsonResult(0, list);
//        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer id = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        //Integer id = (Integer) request.getSession().getAttribute("LOGIN_USER_ID");
        return menuService.getMenus(request, id);
    }

}
