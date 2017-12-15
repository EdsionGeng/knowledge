package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.service.AdvertisementService;
import com.wsd.knowledge.service.MyRecAdService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    JSONObject jsonObject = null;

    /**
     * 添加公告
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "添加公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "title", value = "标题ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "sendDepartmentName", value = "发送对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "content", value = "内容", required = true)
    })
    @RequestMapping(value = "insertAd.htmls", method = RequestMethod.POST)
    public JsonResult insertAd(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String title = String.valueOf(jsonObject.get("title"));
        String content = String.valueOf(jsonObject.get("content"));
        String adStyle = String.valueOf(jsonObject.get("adStyle"));
        String sendDepartmentName = String.valueOf(jsonObject.get("sendDepartmentName"));
        Integer userId = Integer.parseInt(String.valueOf((jsonObject.get("userId"))));
        return advertisementService.insertCommonAd(title, content, sendDepartmentName, userId,adStyle);
    }

    /**
     * 批量删除公告
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "删除公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "ids", value = "公告ID", required = true)
    })
    @RequestMapping(value = "deleteAd.htmls", method = RequestMethod.POST)
    public JsonResult deleteAd(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String ids = String.valueOf(jsonObject.get("ids"));
        return advertisementService.deleteAd(ids);
    }

    /**
     * 读取相应的公告公告
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "读取公告接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "userId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "commonId", value = "文件ID", required = true)
    })
    @RequestMapping(value = "readCommon.htmls", method = RequestMethod.POST)
    public JsonResult readAd(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer commonId = Integer.parseInt(String.valueOf(jsonObject.get("commonId")));
        return advertisementService.readAd(userId, commonId);
    }


    /**
     * 展示个人接收到的公告信息
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "个人消息接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "userId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "current", value = "页码数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "pageSize", value = "页面数量", required = true)
    })
    @RequestMapping(value = "show/userrecad.htmls", method = RequestMethod.POST)
    public JsonResult showUserRecCommon( @RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer current= Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        return myRecAdService.showAllRecAd(current, pageSize, userId);
    }

    /**
     * 发送公告给相应的人
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "发送公告", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "userIds", value = "人员拼接ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "commonId", value = "公告ID", required = true)
    })
    @RequestMapping(value = "send/adtouser.htmls", method = RequestMethod.POST)
    public JsonResult sendAdToUser(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String userIds = String.valueOf(jsonObject.get("userIds"));
        Integer commonId = Integer.parseInt(String.valueOf(jsonObject.get("commonId")));
        return myRecAdService.sendAdToUser(userIds, commonId);
    }

    /**
     * 展示所有公告及组合查询
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "展示公告页面", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "title", value = "标题"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "date1", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "date2", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "current", value = "页码数", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "pageSize", value = "页面数量", required = true),
    })
    @RequestMapping(value = "showallad.htmls", method = RequestMethod.POST)
    public JsonResult showAllAd(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String title = String.valueOf(jsonObject.get("title"));
        String date1 = String.valueOf(jsonObject.get("date1"));
        String date2 = String.valueOf(jsonObject.get("date2"));
        String adStyle= String.valueOf(jsonObject.get("adStyle"));
        Integer page = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer limit= Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        return advertisementService.showAllAd(title, date1, date2,adStyle, page, limit);
    }

    /**
     * 统计某一公告已读未读人数
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "某公告具体信息", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "commonId", value = "公告ID")
    })
    @RequestMapping(value = "show/adpcs.htmls", method = RequestMethod.POST)
    public JsonResult showAdPcs( @RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        Integer commonId = Integer.parseInt(String.valueOf(jsonObject.get("commonId")));
        return advertisementService.showAdPcs(commonId);
    }
}
