package com.wsd.knowledge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.mapper.FileKindMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.DepartmentService;
import com.wsd.knowledge.util.JsonResult;
import com.wsd.knowledge.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by EdsionGeng on 2018/1/6.
 */

@Service
public class DepartServiceImpl implements DepartmentService {
    @Autowired
    private UserRepositoty userRepositoty;
    @Autowired
    private FileKindMapper fileKindMapper;

    /**
     * 展示初始高级部门
     *
     * @return
     */
    @Override
    public JsonResult showSeniorDept() {
        return new JsonResult(0, userRepositoty.showSeniorDept(), "查询结果", 0);
    }

    /**
     * 层级联动显示部门
     *
     * @param object
     * @return
     */
    @Override
    public JsonResult showSonDept(String object) {
        if (("null").equals(object)) {
            return new JsonResult(2, 0, "参数有误", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        String deptId = jsonObject.getString("deptId");
        if (("null").equals(deptId)) {
            return new JsonResult(2, 0, "参数有误", 0);
        }
        List<Map> map = userRepositoty.showSonDept(Integer.parseInt(deptId));
        if (0 != map.size()) {
            return new JsonResult(0, map, "查询结果", 0);
        }
        return new JsonResult(2, 0, "已是最低部门", 0);
    }

    /**
     * 展示初始高级文档目录
     *
     * @return
     */
    @Override
    public JsonResult showSeniorDoc() {
        return new JsonResult(0, fileKindMapper.showSeniorDoc(), "查询结果", 0);
    }

    /**
     * 根据上级Id查找子目录
     * @param object
     * @return
     */
    @Override
    public JsonResult showSonDoc(String object) {
        if (("null").equals(object)) {
            return new JsonResult(2, 0, "参数有误", 0);
        }
        JSONObject jsonObject = JSONObject.parseObject(object);
        String docId = jsonObject.getString("docId");
        if (("null").equals(docId)) {
            return new JsonResult(2, 0, "参数有误", 0);
        }
        List<Map> map = fileKindMapper.showSonDoc(Integer.parseInt(docId));
        if (0 != map.size()) {
            return new JsonResult(0, map, "查询结果", 0);
        }
        return new JsonResult(2, 0, "已是最低级目录", 0);
    }

}
