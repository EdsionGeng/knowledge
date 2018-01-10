package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.RdPage;
import com.wsd.knowledge.mapper.OperationMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
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

    @Autowired
    private UserRepositoty userRepositoty;
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
     * 获取当天信息数据
     *
     * @return
     */
    @Override
    public JsonResult showDayData(String object) {
        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.DayStartTime();
        String endTime = DateUtil.DayEndTime();
        JSONObject jsonObject = JSONObject.parseObject(object);
        String companyId = jsonObject.getString("companyId");
        if (companyId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        if(companyId.equals("1")){
            companyId=getCompanyIds();
        }
        Integer dayLookPcs = operationMapper.countDataLookPcs(startTime, endTime, companyId);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
//        Integer dayDownPcs = operationMapper.countDataDownloadPcs(startTime, endTime);
//        if (dayDownPcs == null) {
//            dayDownPcs = 0;
//        }
        Integer dayDeletePcs = operationMapper.countDataDeletePcs(startTime, endTime, companyId);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countDataUpdatePcs(startTime, endTime, companyId);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countDataAddPcs(startTime, endTime, companyId);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        map.put("lookpcs", dayLookPcs);
        //   map.put("downpcs", dayDownPcs);
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
    public JsonResult showWeekData(String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String companyId = jsonObject.getString("companyId");
        if (companyId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        if(companyId.equals("1")){
            companyId=getCompanyIds();
        }
        Map<String, Object> map = new HashMap<>();
        String startTime = DateUtil.mondayToSunday().get("beginDate");
        //System.out.print(startTime);
        String endTime = DateUtil.mondayToSunday().get("endDate");
        //System.out.print(endTime);

        Integer dayLookPcs = operationMapper.countDataLookPcs(startTime, endTime, companyId);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
//        Integer dayDownPcs = operationMapper.countWeekDownloadPcs(startTime, endTime);
//        if (dayDownPcs == null) {
//            dayDownPcs = 0;
//        }
        Integer dayDeletePcs = operationMapper.countDataDeletePcs(startTime, endTime, companyId);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countDataUpdatePcs(startTime, endTime, companyId);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countDataAddPcs(startTime, endTime, companyId);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        map.put("lookpcs", dayLookPcs);
        //map.put("downpcs", dayDownPcs);
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
    public JsonResult showMonthData(String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        String companyId = jsonObject.getString("companyId");
        if (companyId.equals("null")) {
            return new JsonResult(2, 0, "请登陆", 0);
        }
        if(companyId.equals("1")){
            companyId=getCompanyIds();
        }
        String startTime = DateUtil.getMonthFirstDay();
        String endTime = DateUtil.getMonthLastDay();
        Integer dayLookPcs = operationMapper.countDataLookPcs(startTime, endTime, companyId);
        if (dayLookPcs == null) {
            dayLookPcs = 0;
        }
//        Integer dayDownPcs = operationMapper.countMonthDownloadPcs(startTime, endTime);
//        if (dayDownPcs == null) {
//            dayDownPcs = 0;
//        }
        Integer dayDeletePcs = operationMapper.countDataDeletePcs(startTime, endTime, companyId);
        if (dayDeletePcs == null) {
            dayDeletePcs = 0;
        }
        Integer dayUpdatePcs = operationMapper.countDataUpdatePcs(startTime, endTime, companyId);
        if (dayUpdatePcs == null) {
            dayUpdatePcs = 0;
        }
        Integer dayAddPcs = operationMapper.countDataAddPcs(startTime, endTime, companyId);
        if (dayAddPcs == null) {
            dayAddPcs = 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("lookpcs", dayLookPcs);
        //map.put("downpcs", dayDownPcs);
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
    public JsonResult showSingleFileLog(Integer fileId, Integer current, Integer pageSize, String operationStyle, String departmentName) {
        if (fileId == null || current == null | pageSize == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        if (operationStyle == "null") {
            operationStyle = "";
        }
        if (departmentName == "null") {
            departmentName = "";
        }
        int startSize = (current - 1) * pageSize;
        List<Map> map = null;
        Integer sum = 0;
        RdPage rdPage = new RdPage();
        if (operationStyle.equals("") && departmentName.equals("")) {
            map = operationMapper.showAllOperationLog(fileId, startSize, pageSize);
            sum = operationMapper.countOperationLog(fileId);
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        } else {
            Map<String, Object> maps = new HashMap<>();
            maps.put("fileId", fileId);
            maps.put("operationStyle", operationStyle);
            maps.put("departmentName", departmentName);
            maps.put("startSize", startSize);
            maps.put("limit", pageSize);
            map = operationMapper.queryLogByIf(maps);
            sum = operationMapper.countLogByIf(maps);
            if (sum == null) {
                sum = 0;
            }
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        }
        if (map == null || sum == null) {
            return new JsonResult(2, 0, "无数据", 0);
        }
        return new JsonResult(0, map, "查询结果", rdPage);
    }

    String getCompanyIds() {
        List<Integer> companyList = userRepositoty.queryCompanyIds();
        String ss = "";
        if (companyList.size() != 0) {
            for (int i = 0, len = companyList.size(); i < len; i++) {
                if (i > 0) {
                    ss += ",";
                }
                ss += "'" + companyList.get(i) + "'";
            }

        }
        return ss;
    }
}
