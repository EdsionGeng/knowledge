package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.PermissionService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author EdsionGeng
 * @Description 添加文件执行 相应人员权限
 * @Date:8:57 2017/11/3
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 添加文件相应权限
     *
     * @param userids
     * @param operationStyleId
     * @return
     */
    @RequestMapping("insert/filepermission.htmls")
    public JsonResult insertFilePerMission(Integer[] userids, Integer[] operationStyleId, Integer fileId) {


        return permissionService.insertUserPermission(userids, operationStyleId, fileId);
    }

    /**
     * 点击单个文件查看详情，然后同时查出对此文件相应的操作权限
     *
     * @param userId
     * @param fileId
     * @return
     */
    @ApiOperation(value = "查看用户对应相应文件权限接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value="show/usermission.htmls",method = RequestMethod.GET)
    public JsonResult showFilePermission(Integer userId, Integer fileId) {
        return permissionService.showFilePermission(userId, fileId);
    }

}
