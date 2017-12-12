package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.*;
import com.wsd.knowledge.mapper.FileKindMapper;
import com.wsd.knowledge.mapper.FileMapper;
import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import com.wsd.knowledge.util.StringUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 文件操作业务逻辑层实现类
 * @Date:11:31 2017/11/2
 */
@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {


    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserRepositoty userRepositoty;
    @Autowired
    private FileKindMapper fileKindMapper;
    @Autowired
    private OperationMapper operationMapper;


    /**
     * 组合查询和全部查询
     *
     * @param departmentName
     * @param fileStyleId
     * @param title
     * @param startDate
     * @param endDate
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showAllFile(String departmentName, String fileStyleId, String title, String startDate, String endDate, Integer current, Integer pageSize) {


        if (departmentName == "null") {
            departmentName = "";
        }

        if (fileStyleId.equals("")) {
            fileStyleId = "";
        }
        if (title.equals("")) {
            title = "";
        }
        if (startDate.equals("")) {
            startDate = "2017-11-01 13:30";
        }
        if (endDate.equals("")) {
            endDate = "";
        }
        if (current == null || pageSize == null) {
            current = 1;
            pageSize = 20;
        }
        int startSize = (current - 1) * pageSize;
        if (departmentName.equals("") && fileStyleId.equals("") && title.equals("") && startDate.equals("2017-11-01 13:30") && endDate.equals("")) {
            List<Map> map = fileMapper.showAllFile(startSize, pageSize);
            Integer sum = fileMapper.countFile();
            if (sum == null) {
                sum = 0;
            }
            RdPage page = new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, map, "查询结果", page);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("departmentName", departmentName);
            map.put("fileStyleId", fileStyleId);
            map.put("title", title);
            map.put("startDate", startDate);
            map.put("endDate", endDate);
            map.put("startSize", startSize);
            map.put("limit", pageSize);
            List<Map> maps = fileMapper.queryFileByIf(map);
            Map<String, Object> mapss = new HashMap<>();
            mapss.put("departmentName", departmentName);
            mapss.put("fileStyleId", fileStyleId);
            mapss.put("title", title);
            mapss.put("startDate", startDate);
            mapss.put("endDate", endDate);
            Integer sum = fileMapper.countFilePcs(mapss);
            if (sum == null) {
                sum = 0;
            }
            RdPage page = new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, maps, "查询结果", page);
        }

    }

    /**
     * 添加文件
     *
     * @param title
     * @param content
     * @param photourl
     * @param fileurl
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult insertFile(String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize, String describe) {

        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        String fileNo = String.valueOf(new Date().getTime()).concat("888888");
        SystemUser systemUser = userRepositoty.findInfo(userId);
        FileKind fileKind = fileKindMapper.selectFileKind(fileStyleId);
        //生成实体类
        FileDetail fileDetail = new FileDetail(systemUser.getDepartment(), systemUser.getUsername(), userId, fileStyleId, fileNo, title
                , fileKind.getFileKindName(), content, fileurl, photourl, 0, 0, 0, filesize, 1, describe, new DateUtil().getSystemTime());
        if (fileMapper.insertFileDetail(fileDetail) != null) {
            //添加文件成功 ，获得此文件ID返回前台，执行权限添加操作
            Integer fileId = fileMapper.selectIdByIf(fileNo);
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, fileId, 1, new DateUtil().getSystemTime());
            operationMapper.insertOperationLog(operationLog);
            return new JsonResult(0, fileId, "添加成功", 0);
        }
        return new JsonResult(0, 0, "添加失败", 0);
    }

    /**
     * 批量删除文件
     *
     * @param ids
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteFile(String ids, Integer userId) {
        if (ids == "null" || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        Integer j = null;
        for (String id : ids.split(",")) {
            j = fileMapper.updateFileShow(id);
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, Integer.parseInt(id), 2, new DateUtil().getSystemTime());
            operationMapper.insertOperationLog(operationLog);//添加操作日志
        }
//        for (int i = 0; i < lengths; i++) {
//            j = fileMapper.updateFileShow(id[i]);//更改文件属性
//            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id[i], 2, new DateUtil().getSystemTime());
//            operationMapper.insertOperationLog(operationLog);//添加操作日志
//        }
        if (j != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 查看文件
     *
     * @param fileId
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult readFile(Integer fileId, Integer userId) {
        if (fileId == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        if (fileMapper.lookPcs(fileId) != 0) {
            SystemUser systemUser = userRepositoty.findInfo(userId);
            //添加操作日志
            if (operationMapper.queryLookLog(userId, fileId) == null) {
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, fileId, 4, new DateUtil().getSystemTime());
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                return new JsonResult(0, 0, "已查阅过日志", 0);
            }
        }
        if (j != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 下载文件执行相应的业务逻辑
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult downloadFile(Integer id, Integer userId) {
        if (id == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        if (fileMapper.updateDownPcs(id) != 0) {
            if (operationMapper.queryDownLog(userId, id) == null) {
                SystemUser systemUser = userRepositoty.findInfo(userId);
                //添加操作日志
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 5, new DateUtil().getSystemTime());
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                return new JsonResult(0, 0, "已下载过日志", 0);
            }
        }
        if (j != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 修改文件
     *
     * @param id
     * @param content
     * @param fileurl
     * @param fileStyleId
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult updateFileDetail(Integer id, String content, String fileurl, Integer fileStyleId, Integer userId) {
        if (id == null || content.equals("") || fileurl.equals("") || fileStyleId == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        Integer result = null;
        if (fileurl.equals("0")) {
            result = fileMapper.updateFileContent(fileStyleId, content, id);
        } else {
            result = fileMapper.updateFileContentUrl(fileStyleId, content, id, fileurl);
        }
        if (result != 0) {
            SystemUser systemUser = userRepositoty.findInfo(userId);
            //添加操作日志
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 3, new DateUtil().getSystemTime());
            Integer k = operationMapper.insertOperationLog(operationLog);
            if (k != 0) {
                return new JsonResult(0, 0, "操作成功", 0);
            }
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }


    /**
     * 查看个人能查看的文件
     *
     * @param userId
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showUserLookFile(Integer userId, Integer current, Integer pageSize, String fileStyleId, String departmentName) {
        if (current == null || pageSize == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }

        int startSize = (current - 1) * pageSize;
        RdPage page = new RdPage();
        List<Map> map = null;
        Integer sum = null;
        if (departmentName.equals("") && fileStyleId == null) {
            map = fileMapper.showUserLookFile(userId, startSize, pageSize);
            sum = fileMapper.countUserLookFile(userId);
            if (map != null) {
                page.setTotal(sum);
                page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
                page.setCurrent(current);
                page.setPageSize(pageSize);
                return new JsonResult(0, map, "查询结果", page);
            }
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("startSize", startSize);
            params.put("limit", pageSize);
            params.put("departmentName", departmentName);
            params.put("filestyleId", fileStyleId);
            map = fileMapper.showUserIfLookFile(params);
            if (map != null) {
                sum = fileMapper.showUserIfFilePcs(params);
                page.setTotal(sum);
                page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
                page.setCurrent(current);
                page.setPageSize(pageSize);
                return new JsonResult(0, map, "查询结果", page);
            }

        }
        return new JsonResult(2, 0, "查无结果", 0);
    }

    @Override
    public JsonResult showSearchFile(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer current = Integer.parseInt(String.valueOf(jsonObject.get("current")));
        Integer pageSize = Integer.parseInt(String.valueOf(jsonObject.get("pageSize")));
        String searchContent = String.valueOf(jsonObject.get("searchContent"));
        String departmentName = String.valueOf(jsonObject.get("departmentName"));
        String fileStyleId = String.valueOf(jsonObject.get("fileStyleId"));
        if (userId == null || current == null || pageSize == null || searchContent.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (current - 1) * pageSize;

        RdPage rdPage = new RdPage();
        List<Map> map = null;
        Integer sum = null;
        if (fileStyleId.equals("") && departmentName.equals("")) {
            map = fileMapper.showSearchFile(userId, searchContent, startSize, pageSize);
            sum = fileMapper.countSearchFile(userId, searchContent);
            if (map != null) {
                rdPage.setTotal(sum);
                rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
                rdPage.setCurrent(current);
                rdPage.setPageSize(pageSize);
                return new JsonResult(0, map, "查询结果", rdPage);
            }
        } else{

            Map<String,Object> params=new HashMap<>();
            params.put("userId",userId);
            params.put("startSize",startSize);
            params.put("limit",pageSize);
            params.put("searchContent",searchContent);
            params.put("departmentName",departmentName);
            params.put("fileStyleId",fileStyleId);
            map=fileMapper.showIfSearchFile(params);
            sum=fileMapper.countIfSearchFile(params);
        }
            return new JsonResult(2, 0, "查无结果", 0);
    }

    /**
     * 批量更新文件类型
     *
     * @param object
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult updateFileStyle(String object) {
        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer userId = Integer.parseInt(String.valueOf(jsonObject.get("userId")));
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        String fileIds = String.valueOf(jsonObject.get("fileIds"));
        if (userId == null || fileStyleId == null || fileIds.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        FileKind fileKind = fileKindMapper.selectFileKind(fileStyleId);
        Integer result = null;
        for (String id : fileIds.split(",")) {
            result = fileMapper.updateFileStyle(Integer.parseInt(id), fileStyleId, fileKind.getFileKindName());
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, Integer.parseInt(id), 3, new DateUtil().getSystemTime());
            operationMapper.insertOperationLog(operationLog);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    @Override
    public JsonResult searchFileStyleId(String object) {

        if (object.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer fileStyleId = Integer.parseInt(String.valueOf(jsonObject.get("fileStyleId")));
        Integer sum = fileMapper.countStylePcs(fileStyleId);
        if (sum == null) {
            sum = 0;
        }
        return new JsonResult(0, sum, "查询结果", 0);
    }

}
