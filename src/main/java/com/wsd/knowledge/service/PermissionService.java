package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

import java.util.List;

/**
 * @Author EdsionGeng
 * @Description 添加文件对应人相应权限 业务逻辑层接口
 * @Date:8:59 2017/11/3
 */
public interface PermissionService {
    /**
     * 添加权限文件操作权限
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    JsonResult insertUserPermission(List<Integer> userids, Integer operationStyleId, Integer fileId);


    /**
     * 查询文件对应操作权限
     * @param userId
     * @param fileId
     * @return
     */
    JsonResult showFilePermission(Integer userId,Integer fileId);


    /**
     *添加修改文件权限
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    JsonResult updateFilePerMission(List<Integer> userids, Integer operationStyleId, Integer fileId);


    /**
     * 添加删除文件权限
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    JsonResult deleteFilePerMission(List<Integer> userids, Integer operationStyleId, Integer fileId);
}
