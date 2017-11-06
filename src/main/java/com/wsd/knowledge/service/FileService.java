package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
 * @Author EdsionGeng
 * @Description 文件操作业务逻辑层接口
 * @Date:11:19 2017/11/2
 */
public interface FileService {

    /**
     * 查询所有文件业务逻辑接口
     * @param departmentName
     * @param fileStyleId
     * @param downType
     * @param fileTimeType
     * @param page
     * @param limit
     * @return
     */
    JsonResult showAllFile(String departmentName, String fileStyleId, String downType, String fileTimeType, Integer page, Integer limit);

    /**
     * 添加文件
     * @param title
     * @param content
     * @param photourl
     * @param fileurl
     * @param userId
     * @param fileStyleId
     * @param filesize
     * @return
     */
    JsonResult insertFile(String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize);

    /**
     * 批量删除文件
     * @param id
     * @return
     */
    JsonResult deleteFile(Integer[] id,Integer  userId);


    /**
     * 查阅文件修改此文件被查看次数
     * @param fileId
     * @param userId
     * @return
     */
    JsonResult readFile(Integer fileId,Integer userId);

    /**
     * 下载文件执行相应逻辑
     * @param id
     * @param userId
     * @return
     */
    JsonResult downloadFile(Integer id, Integer userId);
}
