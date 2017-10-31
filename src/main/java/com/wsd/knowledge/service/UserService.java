package com.wsd.knowledge.service;

import com.wsd.knowledge.entity.SystemUser;
public interface UserService {
    SystemUser login(String username,String password);
}
