package com.wsd.knowledge.service;


import com.wsd.knowledge.util.JsonResult;

/**
*@Author EdsionGeng
*@Description 个人接收公告业务逻辑层接口
*@Date:13:24 2017/11/2
*/
public interface MyRecAdService {

    JsonResult showAllRecAd(Integer page,Integer limit,Integer userId);
    JsonResult sendAdToUser(Integer[] departmentId,Integer commonId);

}
