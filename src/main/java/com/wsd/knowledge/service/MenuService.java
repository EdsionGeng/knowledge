package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

import javax.servlet.http.HttpServletRequest;
/**
*@Author EdsionGeng
*@Description 权限业务逻辑层接口
*@Date:9:56 2017/11/8
*/
public interface MenuService {

    /**
     * 获取菜单
     * @param request
     * @param id
     * @return
     */
    JsonResult getMenus(HttpServletRequest request, Integer id);
}
