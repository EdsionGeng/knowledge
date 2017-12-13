package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.UserService;
import com.wsd.knowledge.util.HashAlorgithum;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description
 * @Date:14:29 2017/11/8
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty userRepositoty;
    private final static String LOGIN_USER = "LOGIN_USER";
    private final static String LOGIN_USER_ID = "LOGIN_USER_ID";

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public SystemUser login(String username, String password) {

        SystemUser systemUser = userRepositoty.findUser(username, HashAlorgithum.getSHA256StrJava(password));
        if (systemUser != null) {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            HttpSession session = request.getSession();
//            session.setAttribute(LOGIN_USER, systemUser);
//            session.setAttribute(LOGIN_USER_ID, systemUser.getId());
            return systemUser;
        }
        return null;
    }

    @Override
    public JsonResult queryByGroupId(String object) {
        if(object.equals("")){
            return new JsonResult(2,0," 缺少参数",0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userGroupId = Integer.parseInt(String.valueOf(jsonObject.get("userGroupId")));
        List<SystemUser> systemUsers=userRepositoty.queryGroup(userGroupId);
        return new JsonResult(0,systemUsers,"查询结果",0);
    }

    @Override
    public JsonResult queryAdmin() {
        List<Integer> list=userRepositoty.queryAdmin();
        return new JsonResult(0,list,"查询结果",0);
    }
}
