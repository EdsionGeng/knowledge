package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
 * @Author EdsionGeng
 * @Description 文件操作业务逻辑层接口
 * @Date:11:19 2017/11/2
 */
public interface FileService {

    /**
     * 查询所有文件，组合查询
     *
     * @param departmentName
     * @param fileStyleId
     * @param title
     * @param startDate
     * @param endDate
     * @param page
     * @param limit
     * @return
     */
    JsonResult showAllFile(String departmentName, String fileStyleId, String title, String startDate, String endDate, Integer page, Integer limit);

    /**
     * 添加文件
     *
     * @param title
     * @param content
     * @param photourl
     * @param fileurl
     * @param userId
     * @param fileStyleId
     * @param filesize
     * @return
     */
    JsonResult insertFile(String title, String content, String photourl, String fileurl, Integer userId, Integer fileStyleId, String filesize, String describe,Integer fileSpecies);

    /**
     * 批量删除文件
     *
     * @param ids
     * @return
     */
    JsonResult deleteFile(String ids, Integer userId);


    /**
     * 查阅文件修改此文件被查看次数
     *
     * @param fileId
     * @param userId
     * @return
     */
    JsonResult readFile(Integer fileId, Integer userId);

    /**
     * 下载文件执行相应逻辑
     *
     * @param id
     * @param userId
     * @return
     */
    JsonResult downloadFile(Integer id, Integer userId);


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
    JsonResult updateFileDetail(Integer id, String content, String fileurl, Integer fileStyleId, Integer userId,String chooseUser,String fileSize,String photourl,String describle,String fileStyleName,Integer fileSpecies);

    /**
     * 展示个人能看到的所有文件
     *
     * @param userId
     * @param current
     * @param pageSize
     * @return
     */
    JsonResult showUserLookFile(Integer userId, Integer current, Integer pageSize,String fileStyleId,String departmentName,Integer userGroupId);


    /**
     * 展示搜索结果
     * @param object
     * @return
     */
    JsonResult showSearchFile(String object);


    /**
     * 批量更改文件类型
     * @param object
     * @return
     */
    JsonResult updateFileStyle(String object);

    /**
     *根据文档类型id找文件
     * @param object
     * @return
     */
    JsonResult  searchFileStyleId(String object);

    /**
     * 删除单个文件
     * @param object
     * @return
     */
    JsonResult deleteFileStyle(String object);

    /**
     * 查找单个文件
     * @param object
     * @return
     */
    JsonResult searchSingleFile(String object);
}
