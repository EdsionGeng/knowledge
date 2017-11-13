package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;
/**
*@Author EdsionGeng
*@Description 公告操作业务逻辑层接口
*@Date:14:02 2017/11/2
*/
public interface AdvertisementService {


    /**
     * 添加公告
     * @param title
     * @param content
     * @param sendDepartmentName
     * @param userId
     * @return
     */
    JsonResult insertCommonAd(String title,String content,String sendDepartmentName,Integer userId);

    /**
     * 删除公告
     * @param id
     * @return
     */
    JsonResult deleteAd(Integer[] id);

    /**
     * 读取公告
     * @param userId
     * @param commonId
     * @return
     */
    JsonResult readAd(Integer  userId,Integer commonId);

    /**
     * 展示所有公告 加组合查询
     * @param title
     * @param date1
     * @param date2
     * @param page
     * @param limit
     * @return
     */
    JsonResult showAllAd(String title,String date1,String date2,Integer page,Integer limit);


    /**
     * 查看某一公告已读人数，未读人数
     * @param commonId
     * @return
     */
    JsonResult showAdPcs(Integer commonId);
}
