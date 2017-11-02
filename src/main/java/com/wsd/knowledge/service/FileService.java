package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;

/**
*@Author EdsionGeng
*@Description 文件操作业务逻辑层接口
*@Date:11:19 2017/11/2
*/
public interface FileService {

    JsonResult showAllFile(String departmentName,String  fileStyleId,String downType,String fileTimeType,Integer page,Integer limit);
}
