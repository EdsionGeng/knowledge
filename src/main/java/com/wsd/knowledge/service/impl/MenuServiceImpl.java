package com.wsd.knowledge.service.impl;

import com.google.common.collect.Lists;
import com.wsd.knowledge.entity.Menu;
import com.wsd.knowledge.mapper1.MenuMapper;
import com.wsd.knowledge.service.MenuService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 权限业务逻辑层实现类
 * @Date:9:57 2017/11/8
 */
@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public JsonResult getMenus( Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        param.put("systemId", "2004");
        List<String> rids = menuMapper.getRoleIds(param);
        if (rids != null && rids.size() > 0) {
            param.put("rids", rids);
            List<Menu> list = build(menuMapper.getMenus(param));
          //  request.getSession().setAttribute("menus", list);
            return new JsonResult(0, list, "菜单列表", 0);
        }
        return new JsonResult(0, null, "该用户没有任何访问权限", 0);
    }

    public List<Menu> build(List<Menu> menus) {
        List<Menu> data = Lists.newArrayList();
        for (Menu menu : menus) {
            if (menu.getParentId() == 0) {//取一级菜单
                data.add(menu);
            }
            for (Menu menu2 : menus) {//取子菜单
                if (menu.getId().compareTo(menu2.getParentId().intValue()) == 0) {
                    if (menu.getChildren() == null) {
                        menu.setChildren(new ArrayList<>());
                    }
                    menu.getChildren().add(menu2);
                }
            }
        }
        return data;
    }
}
