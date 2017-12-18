package com.wsd.knowledge.service;


import com.wsd.knowledge.util.JsonResult;

import java.util.Map;

public interface UserService {
    Map login(String username, String password);
    JsonResult queryByGroupId(String object);
    JsonResult queryAdmin();
    Integer  isAdmin(Object userId);

}
