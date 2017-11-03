package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.PermissionService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @param userids
     * @param operationStyleId
     * @return
     */
    @RequestMapping("insert/filepermission.htmls")
    public JsonResult insertFilePerMission(Integer[] userids, Integer[] operationStyleId,Integer fileId) {


        return permissionService.insertUserPermission(userids,operationStyleId,fileId);
    }


}
