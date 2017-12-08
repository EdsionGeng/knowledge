package com.wsd.knowledge.service;

import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.util.JsonResult;


public interface UserService {
    SystemUser login(String username,String password);
    JsonResult queryByGroupId(String object);

}
