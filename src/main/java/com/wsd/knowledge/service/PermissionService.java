package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
 * @Author EdsionGeng
 * @Description 添加文件对应人相应权限 业务逻辑层接口
 * @Date:8:59 2017/11/3
 */
public interface PermissionService {
    /**
     * 添加权限文件操作权限
     * @param userids
     * @param operationStyleIds
     * @param fileId
     * @return
     */
    JsonResult insertUserPermission(Integer[] userids, Integer[] operationStyleIds, Integer fileId);


    /**
     * 查询文件对应操作权限
     * @param userId
     * @param fileId
     * @return
     */
    JsonResult showFilePermission(Integer userId,Integer fileId);
}
