package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
 * @Author EdsionGeng
 * @Description 公告操作业务逻辑层接口
 * @Date:14:02 2017/11/2
 */
public interface AdvertisementService {


    /**
     * 添加公告
     *
     * @param title
     * @param content
     * @param sendDepartmentName
     * @param userId
     * @return
     */
    JsonResult insertCommonAd(String title, String content, String sendDepartmentName, Integer userId, String adStyle, String companyId);

    /**
     * 删除公告
     *
     * @param ids
     * @return
     */
    JsonResult deleteAd(String ids);

    /**
     * 读取公告
     *
     * @param userId
     * @param commonId
     * @return
     */
    JsonResult readAd(Integer userId, Integer commonId);

    /**
     * 展示所有公告 加组合查询
     *
     * @param title
     * @param date1
     * @param date2
     * @param current
     * @param pageSize
     * @return
     */
    JsonResult showAllAd(String title, String date1, String date2, String adStyle, String sortType, String companyId, Integer current, Integer pageSize);


    /**
     * 查看某一公告已读人数，未读人数
     *
     * @param commonId
     * @return
     */
    JsonResult showAdPcs(Integer commonId);


    /**
     * 查看公告具体阅读信息
     * @param object
     * @return
     */
    JsonResult showAdInfo(String object);
}
