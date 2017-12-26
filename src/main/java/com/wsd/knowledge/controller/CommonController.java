package com.wsd.knowledge.controller;

import com.alibaba.fastjson.JSONObject;
import com.wsd.knowledge.entity.FileKind;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.service.CommonService;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author EdsionGeng
 * @Description 公共模块视图交互层
 * @Date:9:18 2017/10/31
 */
@Api(description = "公共方法接口", value = "公共方法接口")
@RestController
@EnableAutoConfiguration
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * 获取部门树形
     *
     * @param department
     * @return
     */
    @ApiOperation(value = "获取部门树形结构", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Object", name = "department", value = "部门实体类"),
    })
    @RequestMapping(value = "getListByTree", method = RequestMethod.POST)

    public List<NewDepartment> getListByTree(@RequestBody NewDepartment department) {
        return commonService.getListByTree(department);
    }

    /**
     * 获取部门树形
     *
     * @param department
     * @return
     */
    @ApiOperation(value = "获取部门树形结构", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Object", name = "department", value = "部门实体类"),
    })
    @RequestMapping(value = "getDepByTree", method = RequestMethod.POST)
    public List<NewDepartment> getDepByTree( @RequestBody NewDepartment department) {
        return commonService.getDepByTree(department);
    }

    /**
     * 文件目录树形结构
     *
     * @param fileKind
     * @return
     */
    @ApiOperation(value = "获取目录树形结构", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Object", name = "fileKind", value = "文件")
    })
    @RequestMapping(value = "getKindByTree", method = RequestMethod.POST)
    public List<FileKind> getKindTree(@RequestBody FileKind fileKind) {
        return commonService.getKindTree(fileKind);
    }


    /**
     * 添加目录
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "添加文档目录", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "parentId", value = "父ID"),
            @ApiImplicitParam(paramType = "query", dataType = " String", name = "docName", value = "目录名称")
    })
    @RequestMapping(value = "insert/rule.htmls", method = RequestMethod.POST)
    public JsonResult insertRule(@RequestBody String object) {
        JSONObject jsonObject = JSONObject.parseObject(object);
        Integer parentId = Integer.parseInt(String.valueOf(jsonObject.get("parentId")));
        String docName = String.valueOf(jsonObject.get("docName"));
        return commonService.insertRule(parentId, docName);
    }

    /**
     * 添加文档目录
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "添加文档目录", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "parentId", value = "父ID"),
            @ApiImplicitParam(paramType = "query", dataType = " String", name = "docName", value = "目录名称")
    })
    @RequestMapping(value = "delete/rule.htmls", method = RequestMethod.POST)
    public JsonResult Rule(@RequestBody String object) {
        return commonService.deleteRule(object);
    }

    @ApiOperation(value = "添加文档目录", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "fileStyleId", value = "父ID"),
            @ApiImplicitParam(paramType = "query", dataType = " String", name = "fileName", value = "目录名称")
    })
    @RequestMapping(value = "update/docname.htmls", method = RequestMethod.POST)
    public JsonResult updateDocName(@RequestBody String object) {
        return commonService.updateRule(object);
    }
}
