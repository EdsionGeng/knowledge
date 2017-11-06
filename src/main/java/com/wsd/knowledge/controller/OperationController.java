package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.OperationService;
import com.wsd.knowledge.util.JsonResult;
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
 * @Description 数值展示界面交互层
 * @Date:15:21 2017/11/3
 */
@RestController
@EnableAutoConfiguration
public class OperationController {


    @Autowired
    private OperationService operationService;

    /**
     * 展示个人历史下载
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查看个人下载文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "每页数量", required = true),
    })
    @RequestMapping(value="show/userdownload.htmls",method= RequestMethod.GET)
    public JsonResult showUserDown(Integer userId, Integer page, Integer limit) {
        return operationService.showUserDownload(userId, page, limit);
    }

    /**
     * 查询所有历史下载
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查看所有下载文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "每页数量", required = true),
    })
    @RequestMapping(value="show/alldownload.htmls",method=RequestMethod.GET)
    public JsonResult showAllDown(Integer page, Integer limit) {
        return operationService.showAllDown(page, limit);
    }

    /**
     * 查询个人历史上传文件
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查看个人历史上传文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "limit", value = "每页数量", required = true),
    })
    @RequestMapping(value="show/allupload.htmls",method=RequestMethod.GET)
    public JsonResult showAllUpload(Integer userId, Integer page, Integer limit) {
        return operationService.showUserUp(userId, page, limit);
    }
}
