package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.AdvertisementService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author EdsionGeng
 * @Description 公告视图交互层
 * @Date:14:26 2017/11/2
 */
@RestController
@EnableAutoConfiguration
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    /**
     * 添加公告
     *
     * @param title
     * @param content
     * @param sendDepartmentName
     * @param userId
     * @return
     */
    @RequestMapping("insertAd.htmls")
    public JsonResult insertAd(String title, String content, String sendDepartmentName, Integer userId) {

        return advertisementService.insertCommonAd(title, content, sendDepartmentName, userId);
    }

    /**
     * 批量删除公告
     * @param id
     * @return
     */
    @RequestMapping("deleteAd.htmls")
    public JsonResult deleteAd(Integer[] id) {
        return advertisementService.deleteAd(id);
    }


}
