package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;
/**
*@Author EdsionGeng
*@Description 公告操作业务逻辑层接口
*@Date:14:02 2017/11/2
*/
public interface AdvertisementService {

    JsonResult insertCommonAd(String title,String content,String sendDepartmentName,Integer userId);


    JsonResult deleteAd(Integer[] id);
}
