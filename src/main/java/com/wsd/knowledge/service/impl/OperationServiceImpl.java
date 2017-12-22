package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.RdPage;
import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.service.OperationService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showUserUp(Integer userId, Integer current, Integer pageSize) {
        if (current == null || pageSize == null||userId==null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (current - 1) * pageSize;
        List<Map> map = operationMapper.showUserUpFile(userId, startSize, pageSize);
        Integer sum = operationMapper.countAllFilePcs(userId);
        if (sum != null) {
            RdPage page =new RdPage();
            page.setTotal(sum);
            page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            page.setCurrent(current);
            page.setPageSize(pageSize);
            return new JsonResult(0, map, "查询结果", page);
        }
        return new JsonResult(0, 0, "无数据", 0);

    }

    /**
     * 获取当天信息数据
     *
     * @return
     */
    @Override
    public JsonResult showDayData() {
        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.DayStartTime();
        String endTime = DateUtil.DayEndTime();
        Integer dayLookPcs = operationMapper.countDataLookPcs(startTime, endTime);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
        Integer dayDownPcs = operationMapper.countDataDownloadPcs(startTime, endTime);
        if (dayDownPcs == null) {
            dayDownPcs = 0;
        }
        Integer dayDeletePcs = operationMapper.countDataDeletePcs(startTime, endTime);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countDataUpdatePcs(startTime, endTime);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countDataAddPcs(startTime, endTime);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        map.put("lookpcs", dayLookPcs);
        map.put("downpcs", dayDownPcs);
        map.put("deletepcs", dayDeletePcs);
        map.put("updatepcs", dayUpdatePcs);
        map.put("addpcs", dayAddPcs);
        return new JsonResult(0, map, "查询结果", 0);
    }

    /**
     * 获取本周信息数据
     *
     * @return
     */
    @Override
    public JsonResult showWeekData() {
        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.mondayToSunday().get("beginDate");
        System.out.print(startTime);
        String endTime = DateUtil.mondayToSunday().get("endDate");
        System.out.print(endTime);
        Integer dayLookPcs = operationMapper.countWeekLookPcs(startTime, endTime);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
        Integer dayDownPcs = operationMapper.countWeekDownloadPcs(startTime, endTime);
        if (dayDownPcs == null) {
            dayDownPcs = 0;
        }
        Integer dayDeletePcs = operationMapper.countWeekDeletePcs(startTime, endTime);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countWeekUpdatePcs(startTime, endTime);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countWeekAddPcs(startTime, endTime);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        map.put("lookpcs", dayLookPcs);
        map.put("downpcs", dayDownPcs);
        map.put("deletepcs", dayDeletePcs);
        map.put("updatepcs", dayUpdatePcs);
        map.put("addpcs", dayAddPcs);
        return new JsonResult(0, map, "查询结果", 0);


    }

    /**
     * 获取本月信息数据
     *
     * @return
     */
    @Override
    public JsonResult showMonthData() {

        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.getMonthFirstDay();
        String endTime = DateUtil.getMonthLastDay();
        Integer dayLookPcs = operationMapper.countMonthLookPcs(startTime, endTime);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
        Integer dayDownPcs = operationMapper.countMonthDownloadPcs(startTime, endTime);
        if (dayDownPcs == null) {
            dayDownPcs = 0;
        }
        Integer dayDeletePcs = operationMapper.countMonthDeletePcs(startTime, endTime);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countMonthUpdatePcs(startTime, endTime);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countMonthAddPcs(startTime, endTime);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        map.put("lookpcs", dayLookPcs);
        map.put("downpcs", dayDownPcs);
        map.put("deletepcs", dayDeletePcs);
        map.put("updatepcs", dayUpdatePcs);
        map.put("addpcs", dayAddPcs);
        return new JsonResult(0, map, "查询结果", 0);

    }


    /**
     * 单个文件操作日志
     *
     * @param fileId
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showSingleFileLog(Integer fileId, Integer current, Integer pageSize, String  operationStyle, String departmentName) {
        if (fileId == null || current == null | pageSize == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        if (operationStyle == "null") {
            operationStyle ="";
        }
        if (departmentName == "null") {
            departmentName = "";
        }
        int startSize = (current - 1) * pageSize;
        List<Map> map = null;
        Integer sum = null;
        RdPage rdPage=new RdPage();
        if (operationStyle .equals("") && departmentName.equals("")) {
            map = operationMapper.showAllOperationLog(fileId, startSize, pageSize);
            sum = operationMapper.countOperationLog(fileId);
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        }
        else{
            Map<String,Object> maps=new HashMap<>();
            maps.put("operationStyle",operationStyle);
            maps.put("departmentName",departmentName);
            maps.put("startSize",startSize);
            maps.put("limit",pageSize);
            map=operationMapper.queryLogByIf(maps);
            sum=operationMapper.countLogByIf(maps);
            if(sum==null){
                sum=0;
            }
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        }
        if (map == null || sum == null) {
            return new JsonResult(2, 0, "无数据", 0);
        }
        return new JsonResult(0, map, "查询结果",rdPage);
    }
}
