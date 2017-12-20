package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.service.PermissionService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author EdsionGeng
 * @Description 添加文件执行 相应人员权限
 * @Date:8:57 2017/11/3
 */
@Api(description = "文件权限接口", value = "文件权限接口")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    JSONObject jsonObject = null;
    /**
     * 添加查看文件相应权限
     *
     * @param object
     * @return
     */
    @RequestMapping(value = "insert/filepermission.htmls", method = RequestMethod.POST)
    @ApiOperation(value = "添加查看文件权限人数", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "fileId", value = "文件Id"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "userIds", value = "人员id拼接的字符串"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "date2", value = "操作类型Id 1可以查看 0 不能查看")
    })
    public JsonResult insertFilePerMission(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String userIds = String.valueOf(jsonObject.get("userIds"));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Integer operationStyleId = Integer.parseInt(String.valueOf(jsonObject.get("operationStyleId")));
        return permissionService.insertUserPermission(userIds, operationStyleId, fileId);
    }

    /**
     * 添加文件修改权限
     *
     * @param object
     * @return
     */
    @RequestMapping(value = "update/permission.htmls", method = RequestMethod.POST)
    @ApiOperation(value = "添加可以修改文件权限的人", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "fileId", value = "文件Id"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "userIds", value = "人员id拼接的字符串"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "operationStyleId", value = "操作类型Id 1可以修改 0 不能修改")
    })
    public JsonResult updateFilePerMission(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String userIds = String.valueOf(jsonObject.get("userIds"));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Integer operationStyleId = Integer.parseInt(String.valueOf(jsonObject.get("operationStyleId")));
        return permissionService.updateFilePerMission(userIds, operationStyleId, fileId);
    }

    /**
     * 删除文件权限
     *
     * @param object
     * @return
     */
    @RequestMapping(value = "delete/permission.htmls", method = RequestMethod.POST)
    @ApiOperation(value = "添加删除文件权限的人", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "fileId", value = "文件Id"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "userIds", value = "人员id拼接的字符串"),
            @ApiImplicitParam(paramType = "query", dataType = "String ", name = "operationStyleId", value = "操作类型Id 1可以删除 0 不能删除")
    })
    public JsonResult deleteFilePerMission(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        String userIds = String.valueOf(jsonObject.get("userIds"));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        Integer operationStyleId = Integer.parseInt(String.valueOf(jsonObject.get("operationStyleId")));
        return permissionService.deleteFilePerMission(userIds, operationStyleId, fileId);
    }
    /**
     * 点击单个文件查看详情，然后同时查出对此文件相应的操作权限
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "查看用户对应相应文件权限接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileId", value = "文件ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "userId", value = "用户ID", required = true),
    })
    @RequestMapping(value = "show/usermission.htmls", method = RequestMethod.POST
    )
    public JsonResult showFilePermission(@RequestBody String object) {
        jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        return permissionService.showFilePermission(userId, fileId);
    }

}
