package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.service.OperationService;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 数据展示 业务逻辑层 实现类
 * @Date:15:47 2017/11/3
 */
@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationMapper operationMapper;

    /**
     * 展示个人用户下载记录
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResult showUserDownload(Integer userId, Integer page, Integer limit) {
        if (userId == null || page == null || limit == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (page - 1) * limit;
        List<Map> map = operationMapper.queryUserDownload(userId, startSize, limit);
        Integer j = operationMapper.counUserDownPcs(userId);
        if (j != null) {
            return new JsonResult(0, map, "查询结果", j);
        }
        return new JsonResult(2, 0, "查询失败", 0);

    }

    /**
     * 超管页面历史下载业务逻辑层处理
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResult showAllDown(Integer page, Integer limit) {
        if (page == null || limit == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (page - 1) * limit;
        List<Map> map = operationMapper.queryAllDownload(startSize, limit);
        Integer j = operationMapper.counAllDownPcs();
        if (j != null) {
            return new JsonResult(0, map, "查询结果", j);
        }
        return new JsonResult(2, 0, "查询失败", 0);
    }

    /**
     * 用户历史上传业务逻辑层处理
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResult showUserUp(Integer userId, Integer page, Integer limit) {
        if (page == null || limit == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (page - 1) * limit;
        List<Map> map = operationMapper.showUserUpFile(userId, startSize, limit);
        Integer j = operationMapper.countAllFilePcs(userId,startSize,limit);
        if (j != null) {
            return new JsonResult(0, map, "查询结果", j);
        }
        return new JsonResult(2, 0, "查询失败", 0);

    }
}
