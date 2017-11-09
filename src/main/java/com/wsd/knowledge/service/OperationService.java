package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author EdsionGeng
 * @Description 数量显示 业务逻辑层接口
 * @Date:15:34 2017/11/3
 */
public interface OperationService {
    /**
     * 用户个人历史下载
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    JsonResult showUserDownload(Integer userId, Integer page, Integer limit);

    /**
     * 所有用户历史下载
     *
     * @param page
     * @param limit
     * @return
     */
    JsonResult showAllDown(Integer page, Integer limit);

    /**
     * 用户历史上传
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    JsonResult showUserUp(Integer userId, Integer page, Integer limit);

    /**
     * 展示当天数据
     * @return
     */
    JsonResult showDayData();

    /**
     * 展示本周数据
     * @return
     */
    JsonResult showWeekData();

    /**
     * 展示当月数据
     * @return
     */
    JsonResult showMonthData();

    /**
     * 查看某一文件操作日志记录
     * @param fileId
     * @return
     */
    JsonResult showSingleFileLog(Integer fileId,Integer page,Integer limit,String  operationStyle,String departmentName);

}
