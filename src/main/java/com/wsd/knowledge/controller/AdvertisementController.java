package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.AdvertisementService;
import com.wsd.knowledge.service.MyRecAdService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author EdsionGeng
 * @Description 公告视图交互层
 * @Date:14:26 2017/11/2
 */
@Api(description = "公告类接口", value = "公告类接口")
@RestController
@EnableAutoConfiguration
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private MyRecAdService myRecAdService;

    /**
     * 添加公告
     *
     * @param title
     * @param content
     * @param sendDepartmentName
     * @param userId
     * @return
     */
    @ApiOperation(value = "添加公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "title", value = "标题ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendDepartmentName", value = "发送对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "content", value = "内容", required = true)
    })
    @RequestMapping(value = "insertAd.htmls", method = RequestMethod.PUT)
    public JsonResult insertAd(String title, String content, String sendDepartmentName, Integer userId) {
        return advertisementService.insertCommonAd(title, content, sendDepartmentName, userId);
    }

    /**
     * 批量删除公告
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "id", value = "文件ID", required = true)

    })
    @RequestMapping(value = "deleteAd.htmls", method = RequestMethod.DELETE)
    public JsonResult deleteAd(Integer[] id) {
        return advertisementService.deleteAd(id);
    }

    /**
     * 读取相应的公告公告
     *
     * @param userId
     * @param commonId
     * @return
     */
    @ApiOperation(value = "读取公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "userId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "commonId", value = "文件ID", required = true)
    })
    @RequestMapping(value = "readCommon.htmls", method = RequestMethod.DELETE)
    public JsonResult readAd(Integer userId, Integer commonId) {
        return advertisementService.readAd(userId, commonId);
    }


    /**
     * 展示个人接收到的公告信息
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("show/userrecad.htmls")
    public JsonResult showUserRecCommon(Integer userId, Integer page, Integer limit) {
        return myRecAdService.showAllRecAd(page, limit, userId);
    }

    /**
     * 发送公告给相应的人
     *
     * @param departmentId
     * @param commonId
     * @return
     */
    @RequestMapping("send/adtouser.htmls")
    public JsonResult sendAdToUser(Integer[] departmentId, Integer commonId) {
        return myRecAdService.sendAdToUser(departmentId, commonId);
    }


    /**
     * 展示所有公告及组合查询
     * @param title
     * @param date1
     * @param date2
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("show/allad.htmls")
    public JsonResult showAllAd(String title, String date1, String date2, Integer page, Integer limit) {
        return advertisementService.showAllAd(title, date1, date2, page, limit);
    }


}
