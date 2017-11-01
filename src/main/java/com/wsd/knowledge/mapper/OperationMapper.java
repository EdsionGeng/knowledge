package com.wsd.knowledge.mapper;

import com.wsd.knowledge.entity.OperationLog;
import org.apache.ibatis.annotations.Insert;

/**
 * @Author EdsionGeng
 * @Description 文件操作日志持久层
 * @Date:17:20 2017/11/1
 */
public interface OperationMapper {

    /**
     * 添加对某一文件操作日志 更新 删除
     * @param operationLog
     * @return
     */
    @Insert("insert into OperationLog(departmentName,fileId,operationStyle,operationTime,userId) " +
            "values(#{departmentName},#{fileId},#{operationStyle},#{operationTime},#{userId})")
    Integer insertOperationLog(OperationLog operationLog);



}
