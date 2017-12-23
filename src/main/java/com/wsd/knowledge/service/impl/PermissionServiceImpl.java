package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.UserPermission;
import com.wsd.knowledge.mapper.FileMapper;
import com.wsd.knowledge.mapper.UserPermissionMapper;
import com.wsd.knowledge.service.PermissionService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author EdsionGeng
 * @Description 文件权限业务逻辑层实现类
 * @Date:9:08 2017/11/3
 */

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserPermissionMapper userPermissionMapper;
    @Autowired
    private FileMapper fileMapper;

    /**
     * 添加文件人员相对应的权限
     *
     * @param userIds
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public synchronized JsonResult insertUserPermission(String userIds, Integer operationStyleId, Integer fileId) {
        if (userIds.equals("") || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "缺少参数", 0);
        }
//        String str = new DateUtil().cacheExist(String.valueOf(fileId));
//        if (str.equals("full")) {
//            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
//        }
        List<Integer> userlist = new ArrayList<>();
        for (String id : userIds.split(",")) {
            userlist.add(Integer.parseInt(id));
        }
        List<Integer> newList = new ArrayList(new HashSet(userlist));
        Integer j = null;
        for (int i = 0, lengths = newList.size(); i < lengths; i++) {
            UserPermission userPermission = new UserPermission(fileId, newList.get(i), operationStyleId, new DateUtil().getSystemTime(), 0, 0);
//            String strss = new DateUtil().cacheExist(String.valueOf(userlist.get(i)));
//            if (strss.equals("full")) {
//                fileMapper.deleteFile(fileId);
//                userPermissionMapper.deletePerByFileId(fileId);
//                return new JsonResult(2, 0, "并发问题" , 0);
//            }
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
        return new JsonResult(2, 0, "查询失败", 0);
    }

    /**
     * 添加修改文件权限
     *
     * @param userIds
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult updateFilePerMission(String userIds, Integer operationStyleId, Integer fileId) {
        if (userIds.equals("") || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer result = null;
//        String str = new DateUtil().cacheExist(String.valueOf(fileId));
//        if (str.equals("full")) {
//            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
//        }
        List<Integer> userlist = new ArrayList<>();

        for (String id : userIds.split(",")) {
            userlist.add(Integer.parseInt(id));
        }
        List<Integer> newList = new ArrayList(new HashSet(userlist));
        for (int i = 0, length = newList.size(); i < length; i++) {
            result = userPermissionMapper.addUpdatePermission(newList.get(i), operationStyleId, fileId);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 添加删除文件权限
     *
     * @param userIds
     * @param operationStyleId
     * @param fileId
     * @return
     */
    @Override
    public JsonResult deleteFilePerMission(String userIds, Integer operationStyleId, Integer fileId) {
        if (userIds == null || operationStyleId == null || fileId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer result = null;
//        String str = new DateUtil().cacheExist(String.valueOf(fileId));
//        if (str.equals("full")) {
//            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
//        }
        List<Integer> userlist = new ArrayList<>();
        for (String id : userIds.split(",")) {
            userlist.add(Integer.parseInt(id));
        }
        List<Integer> newList = new ArrayList(new HashSet(userlist));
        for (int i = 0, length = newList.size(); i < length; i++) {
            result = userPermissionMapper.addDeletePermission(newList.get(i), operationStyleId, fileId);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);

    }

    /**
     * 文件权相关的人
     * @param object
     * @return
     */
    @Override
    public JsonResult queryPerUsers(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer fileId = Integer.parseInt(String.valueOf(jsonObject.get("fileId")));
        List<Integer>  looklist = userPermissionMapper.queryPerReadUserId(fileId);
        List<Integer> updatelist = userPermissionMapper.queryPerUpdateUserId(fileId);
        List<Integer> deletelist = userPermissionMapper.queryPerDeleteUserId(fileId);
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        list.add(looklist);
        list.add(updatelist);
        list.add(deletelist);
        if(list!=null){
            return new JsonResult(0,list,"查询结果",0);
        }
        return new JsonResult(2,0,"查询失败",0);
    }
}
