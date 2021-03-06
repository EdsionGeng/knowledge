package com.wsd.knowledge.service;

import com.wsd.knowledge.entity.FileKind;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.util.JsonResult;

import java.util.List;

/**
 * @Author EdsionGeng
 * @Description 公共模块业务逻辑层接口
 * @Date:9:18 2017/10/31
 */
public interface CommonService {



    /**
     * 公司树形架构
     * @param department
     * @return
     */
    List<NewDepartment> getListByTree(NewDepartment department);


    /**
     * 组织架构树
     * @param department
     * @return
     */
    List<NewDepartment> getDepByTree(NewDepartment department);
    /**
     * 文件目录树形结构
     * @param fileKind
     * @return
     */
    List<FileKind> getKindTree(FileKind fileKind);

    /**
     * 添加目录
     * @param parentid
     * @param docName
     * @return
     */
    JsonResult insertRule(Integer parentid,String docName);

    /**
     * 删除文档类型
     * @param object
     * @return
     */
    JsonResult deleteRule(String object);

    /**
     * 修改文档名字
     * @param object
     * @return
     */
    JsonResult updateRule(String object);
}
