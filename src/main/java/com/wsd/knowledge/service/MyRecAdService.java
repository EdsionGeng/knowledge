package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

import java.util.List;

/**
 * @Author EdsionGeng
 * @Description 个人接收公告业务逻辑层接口
 * @Date:13:24 2017/11/2
 */
public interface MyRecAdService {

    /**
     * 展示个人消息
     *
     * @param current
     * @param pageSize
     * @param userId
     * @return
     */
    JsonResult showAllRecAd(Integer current, Integer pageSize, Integer userId);

    /**
     * 发送公告给相应的人
     *
     * @param departmentId
     * @param commonId
     * @return
     */
    JsonResult sendAdToUser(List<Integer> departmentId, Integer commonId);

}
