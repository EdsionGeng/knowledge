package com.wsd.test;

import com.wsd.knowledge.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TestMethod {


    @Autowired
    private MenuService menuService;

    @Test
    public void tets(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       System.out.print( menuService.getMenus(request,1));
    }
}
