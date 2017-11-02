package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.mapper.UserRecAdMapper;
import com.wsd.knowledge.service.MyRecAdService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class MyRecAdServiceImpl implements MyRecAdService {

    @Autowired
    private UserRecAdMapper userRecAdMapper;

    /**
     * 展示个人接收所有公告数
     *
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    @Override
    public JsonResult showAllRecAd(Integer page, Integer limit, Integer userId) {
        int startSize = (page - 1) * limit;
        List<Map> map = userRecAdMapper.showUserRecAd(userId, startSize, limit);
        return new JsonResult(0, map, "查询结果", userRecAdMapper.countUserAdPcs());
    }


}
