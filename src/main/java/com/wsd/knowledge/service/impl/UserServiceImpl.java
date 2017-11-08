package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
*@Author EdsionGeng
*@Description
*@Date:14:29 2017/11/8
*/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty userRepositoty;
    private final static String LOGIN_USER = "LOGIN_USER";
    private final static String LOGIN_USER_ID = "LOGIN_USER_ID";

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public SystemUser login(String username, String password) {
        SystemUser systemUser = userRepositoty.findUser(username, password);
        if (systemUser != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute(LOGIN_USER, systemUser);
            session.setAttribute(LOGIN_USER_ID, systemUser.getId());
        }
        return systemUser;
    }
}
