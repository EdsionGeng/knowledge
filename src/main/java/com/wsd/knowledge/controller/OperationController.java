package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.service.OperationService;
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

/**
 * @Author EdsionGeng
 * @Description 数值展示界面交互层
 * @Date:15:21 2017/11/3
 */
@Api(description = "数据展示视图层接口", value = "数据展示视图层接口")
@RestController
@EnableAutoConfiguration
public class OperationController {


    @Autowired
    private OperationService operationService;

//    /**
//     * 展示个人历史下载
//     *
//     * @param userId
//     * @param page
//     * @param limit
//     * @return
//     */
//    @ApiOperation(value = "查看个人下载文件接口", notes = "传递必要参数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "每页数量", required = true),
//    })
//    @RequestMapping(value = "show/userdownload.htmls", method = RequestMethod.GET)
//    public JsonResult showUserDown(Integer userId, Integer page, Integer limit) {
//        return operationService.showUserDownload(userId, page, limit);
//    }
//
//    /**
//     * 查询所有历史下载
//     *
//     * @param page
//     * @param limit
//     * @return
//     */
//    @ApiOperation(value = "查看所有下载文件接口", notes = "传递必要参数")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "每页数量", required = true),
//    })
//    @RequestMapping(value = "show/alldownload.htmls", method = RequestMethod.GET)
//    public JsonResult showAllDown(Integer page, Integer limit) {
//        return operationService.showAllDown(page, limit);
//    }

    /**
     * 查询个人历史上传文件
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "查看个人历史上传文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "current", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量", required = true),
    })
    @RequestMapping(value = "show/allupload.htmls", method = RequestMethod.POST)
    public JsonResult showAllUpload(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String  userId = String.valueOf(jsonObject.get("userId"));
        if(userId.equals("null")){
            return new JsonResult(2,0,"请登陆" ,0);
        }
        String  sorType = String.valueOf(jsonObject.get("sortType"));
        Integer current = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        return operationService.showUserUp(Integer.parseInt(userId),sorType,current, pageSize);
    }

    /**
     * 展示当天信息数据
     *
     * @return
     */
    @ApiOperation(value = "查看当天信息数据", notes = "传递必要参数")
    @RequestMapping(value = "show/daydata.htmls", method = RequestMethod.GET)
    public JsonResult showDayData() {
        return operationService.showDayData();
    }

    /**
     * 展示本周信息数据
     *
     * @return
     */
    @ApiOperation(value = "查看本周数据记录", notes = "传递必要参数")
    @RequestMapping(value = "show/weekdata.htmls", method = RequestMethod.GET)
    public JsonResult showWeekData() {
        return operationService.showWeekData();
    }

    /**
     * 展示本月信息数据
     *
     * @return
     */
    @ApiOperation(value = "查看本月数据记录", notes = "传递必要参数")
    @RequestMapping(value = "show/monthdata.htmls", method = RequestMethod.GET)
    public JsonResult showMonthData() {
        return operationService.showMonthData();
    }

    /**
     * 展示单个文件操作日志记录
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "查看单个文件操作日志接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "current", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "operationStyle", value = "操作类型"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "departmentName", value = "部门名字")
    })
    @RequestMapping(value = "show/fileLog.htmls", method = RequestMethod.POST)
    public JsonResult showSingleFileLog(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String operationStyle = String.valueOf(jsonObject.get("operationStyle"));
        String departmentName = String.valueOf(jsonObject.get("departmentName"));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Integer current = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        return operationService.showSingleFileLog(fileId, current, pageSize, operationStyle, departmentName);
    }
}
