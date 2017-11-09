package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.FileDetail;
import com.wsd.knowledge.entity.FileKind;
import com.wsd.knowledge.entity.OperationLog;
import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper.FileKindMapper;
import com.wsd.knowledge.mapper.FileMapper;
import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import com.wsd.knowledge.util.StringUtils;
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
     * 根据参数判断是组合查询还是全部查询
     *
     * @param departmentName
     * @param fileStyleId
     * @param downType
     * @param fileTimeType
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResult showAllFile(String departmentName, String fileStyleId, String downType, String fileTimeType, Integer page, Integer limit) {

        int startSize = (page - 1) * limit;

        if (departmentName==null) {
            departmentName = "";
        }
        if (downType==null) {
            downType = "";
        }
        if (fileTimeType==null) {
            fileTimeType = "desc";
        }
        if (fileStyleId==null) {
            fileStyleId = "";
        }
        if (departmentName.equals("") && fileStyleId.equals("") && downType.equals("") && fileTimeType.equals("desc")) {
            List<Map> map = fileMapper.showAllFile(startSize, limit);
            Integer j = fileMapper.countFile(startSize, limit);
            if (j == null) {
                j = 0;
            }
            return new JsonResult(0, map, "查询结果", j);

        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("departmentName", departmentName);
            map.put("fileStyleId", fileStyleId);
            map.put("downType", downType);
            map.put("fileTimeType", fileTimeType);
            map.put("startSize", startSize);
            map.put("limit", limit);
            List<Map> maps = fileMapper.queryFileByIf(map);
            Integer j = fileMapper.countFilePcs(map);
            if (j == null) {
                j = 0;
            }
            return new JsonResult(0, maps, "查询结果", j);
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
    public JsonResult insertFile(String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize) {

        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        String fileNo = String.valueOf(new Date().getTime()).concat("000010");
        SystemUser systemUser = userRepositoty.findInfo(userId);
        FileKind fileKind = fileKindMapper.selectFileKind(fileStyleId);
        //生成实体类
        FileDetail fileDetail = new FileDetail(systemUser.getDepartment(), systemUser.getUsername(), userId, fileStyleId, fileNo, title
                , fileKind.getFileKindName(), content, fileurl, photourl, 0, 0, 0, filesize, 1, new DateUtil().getSystemTime());
        Integer j = null;
//        this.departmentName = departmentName;
//        this.username=username;
//        this.fileStyleId = fileStyleId;
//        this.userId = userId;
//        this.fileNo = fileNo;
//        this.title = title;
//        this.fileStyle = fileStyle;
//        this.fileContent = fileContent;
//        this.fileUrl = fileUrl;
//        this.photoUrl = photoUrl;
//        this.lookPcs = lookPcs;
//        this.downloadPcs = downloadPcs;
//        this.updatePcs = updatePcs;
//        this.fileSize = fileSize;
//        this.fileDisplay = fileDisplay;
//        this.addFileTime = addFileTime;
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
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteFile(Integer[] id, Integer userId) {
        if (id == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        Integer j = null;
        int lengths = id.length;
        for (int i = 0; i < lengths; i++) {
            j = fileMapper.updateFileShow(id[i]);//更改文件属性
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id[i], 2, new DateUtil().getSystemTime());
            operationMapper.insertOperationLog(operationLog);//添加操作日志
        }
        if (j != null) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    @Override
    @Transactional(readOnly = false)
    public JsonResult readFile(Integer fileId, Integer userId) {
        if (fileId == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        if (fileMapper.lookPcs(fileId) != null) {
            SystemUser systemUser = userRepositoty.findInfo(userId);
            //添加操作日志
            if (operationMapper.queryLookLog(userId, fileId) == null) {
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, fileId, 4, new DateUtil().getSystemTime());
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                return new JsonResult(0, 0, "已查阅过日志", 0);
            }
        }
        if (j != null) {
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
        if (fileMapper.updateDownPcs(id) != null) {
            if (operationMapper.queryDownLog(userId, id) == null) {
                SystemUser systemUser = userRepositoty.findInfo(userId);
                //添加操作日志
                OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 5, new DateUtil().getSystemTime());
                j = operationMapper.insertOperationLog(operationLog);
            } else {
                return new JsonResult(0, 0, "已下载过日志", 0);
            }
        }
        if (j != null) {
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
        if (id == null || content == null || fileurl == null || fileStyleId == null || userId == null) {
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
        if (result != null) {
            SystemUser systemUser = userRepositoty.findInfo(userId);
            //添加操作日志
            OperationLog operationLog = new OperationLog(systemUser.getDepartment(), systemUser.getUsername(), userId, id, 3, new DateUtil().getSystemTime());
            Integer k = operationMapper.insertOperationLog(operationLog);
            if (k != null) {
                return new JsonResult(0, 0, "操作成功", 0);
            }

        }
             return new JsonResult(2, 0, "操作失败", 0);
    }


}
