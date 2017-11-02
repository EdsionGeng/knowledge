package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.mapper.FileMapper;
import com.wsd.knowledge.service.FileService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 根据参数判断是组合查询还是全部查询
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

        if (departmentName == null) {
            departmentName = "";
        }
        if (downType == null) {
            downType = "";
        }
        if (fileTimeType == null) {
            fileTimeType = "desc";
        }
        if (fileStyleId == null) {
            fileStyleId = "";
        }
        if (departmentName == "" && fileStyleId == "" && downType == "" &&fileTimeType == ""){
            List<Map> map = fileMapper.showAllFile(startSize,limit);
            return new JsonResult(0,map,"查询结果",fileMapper.countFile());

        } else {
            Map<String,Object> map=new HashMap<>();
            map.put("departmentName",departmentName);
            map.put("fileStyleId",fileStyleId);
            map.put("downType",downType);
            map.put("fileTimeType",fileTimeType);
            map.put("startSize",startSize);
            map.put("limit",limit);
            List<Map> maps=fileMapper.queryFileByIf(map);

            return new JsonResult(0,maps,"查询结果",fileMapper.countFilePcs(map));
        }

    }
}
