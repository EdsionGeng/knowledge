package com.wsd.knowledge.controller;

import com.wsd.knowledge.entity.Menu;
import com.wsd.knowledge.service.MenuService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @param request
     * @return
     */
    @RequestMapping("getMenus.htmls")
    public JsonResult getMenusList(HttpServletRequest request) {
        List<Menu> list = (List<Menu>) request.getSession().getAttribute("menus");
        if (list != null) {
            return new JsonResult(0, list);
        }
        Integer id = (Integer) request.getSession().getAttribute("LOGIN_USER_ID");
        return menuService.getMenus(request, id);
    }

}
