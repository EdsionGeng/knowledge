package com.wsd.knowledge.controller;

import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author EdsionGeng
 * @Description 文件视图交互层
 * @Date:11:11 2017/11/2
 */
@RestController
public class FileController {
    @Autowired
    private FileService fileService;


    /**
     * 查询所有上传文件 组合查询 共用同一个接口
     * @param departmentName
     * @param fileStyleId
     * @param downType
     * @param fileTimeType
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("show/allFile")
    public JsonResult showAllFile(String departmentName, String fileStyleId, String downType, String fileTimeType, Integer page, Integer limit) {

        return fileService.showAllFile(departmentName,fileStyleId,downType,fileTimeType,page,limit);

    }

}
