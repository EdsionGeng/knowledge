package com.wsd.knowledge.service;

import com.wsd.knowledge.util.JsonResult;


/**
 * Created by EdsionGeng on 2018/1/6.
 */
public interface DepartmentService {
    /**
     * 展示初始高级部门
     * @return
     */
    JsonResult showSeniorDept();

    /**
     * 展示子部门
     * @param object
     * @return
     */
    JsonResult showSonDept(String object);

    /**
     * 展示高级目录
     * @return
     */

    JsonResult showSeniorDoc();

    /**
     * 展示子部门
     * @param object
     * @return
     */
    JsonResult showSonDoc(String object);

    /**
     * 展示层级目录
     * @return
     */
    JsonResult showAllDoc();
}
