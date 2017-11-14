package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.UserPermission;
import com.wsd.knowledge.mapper.UserPermissionMapper;
import com.wsd.knowledge.service.PermissionService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author EdsionGeng
 * @Description 文件权限业务逻辑层实现类
 * @Date:9:08 2017/11/3
 */

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    /**
     * 添加文件人员相对应的权限
     *
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult insertUserPermission(Integer[] userids, Integer operationStyleId, Integer fileId) {
        if (userids == null || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "缺少参数", 0);
        }
        String strs = new DateUtil().cacheExist(String.valueOf(fileId));
        if (strs.equals("full")) {
            return new JsonResult(2, 0, "网络延时，稍后加载", 0);
        }
        int lengths = userids.length;
        Integer j = null;
        for (int i = 0; i < lengths; i++) {
            String str = new DateUtil().cacheExist(String.valueOf(userids[i]));
            if (str.equals("full")) {
                return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
            }
            UserPermission userPermission = new UserPermission(fileId, userids[i], operationStyleId, new DateUtil().getSystemTime());
            j = userPermissionMapper.insertUserPermission(userPermission);
        }
        if (j != 0) {
            return new JsonResult(0, 0, "添加成功", 0);
        }

        return new JsonResult(2, 0, "添加失败", 0);
    }

    /**
     * 查看操作文件权限
     *
     * @param userId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult showFilePermission(Integer userId, Integer fileId) {
        if (userId == null || fileId == null) {
            return new JsonResult(2, 0, "异常", 0);
        }
        UserPermission userPermission = userPermissionMapper.queryFilePermission(userId, fileId);
        if (userPermission != null) {
            return new JsonResult(0, userPermission, "查询成功", 0);
        }
        return new JsonResult(0, 0, "查询失败", 0);
    }

    /**
     * 添加修改文件权限
     *
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult updateFilePerMission(Integer[] userids, Integer operationStyleId, Integer fileId) {
        if (userids == null || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer result = null;
        int length = userids.length;
        for (int i = 0; i < length; i++) {
            result = userPermissionMapper.addUpdatePermission(userids[i], operationStyleId, fileId);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }
    /**
     * 添加删除文件权限
     *
     * @param userids
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult deleteFilePerMission(Integer[] userids, Integer operationStyleId, Integer fileId) {
        if (userids == null || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer result = null;
        int length = userids.length;
        for (int i = 0; i < length; i++) {
            result = userPermissionMapper.addDeletePermission(userids[i], operationStyleId, fileId);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);

    }
}
