package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoty userRepositoty;


    @Override
    public SystemUser login(String username, String password) {
        return userRepositoty.findUser(username, password);
    }
}
