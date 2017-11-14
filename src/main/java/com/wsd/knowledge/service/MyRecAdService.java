package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
 * @Author EdsionGeng
 * @Description 个人接收公告业务逻辑层接口
 * @Date:13:24 2017/11/2
 */
public interface MyRecAdService {

    /**
     * 展示个人消息
     *
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    JsonResult showAllRecAd(Integer page, Integer limit, Integer userId);

    /**
     * 发送公告给相应的人
     *
     * @param departmentId
     * @param commonId
     * @return
     */
    JsonResult sendAdToUser(Integer[] departmentId, Integer commonId);

}
