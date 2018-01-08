package com.wsd.knowledge.controller;
import com.wsd.knowledge.service.DepartmentService;
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
/**
 * Created by EdsionGeng on 2018/1/6.
 */
@Api(description = "部门视图层接口", value = "部门视图层接口")
@RestController
@EnableAutoConfiguration
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "展示初始高级部门", notes = "无需参数")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "show/senior/dept.htmls", method = RequestMethod.GET)
    public JsonResult showSeniorDept() {
        return departmentService.showSeniorDept();
    }


    @ApiOperation(value = "展示层级部门", notes = "无需参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "deptId", value = "部门ID", required = true),
    })
    @RequestMapping(value = "show/son/dept.htmls", method = RequestMethod.POST)
    public JsonResult showSonDept(@RequestBody String object) {
        return departmentService.showSonDept(object);
    }

    @ApiOperation(value = "展示初始高级目录", notes = "无需参数")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "show/senior/doc.htmls", method = RequestMethod.GET)
    public JsonResult showSeniorDoc() {
        return departmentService.showSeniorDoc();
    }

    @ApiOperation(value = "展示层级目录", notes = "无需参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer ", name = "docId", value = "文档ID", required = true),
    })
    @RequestMapping(value = "show/son/doc.htmls", method = RequestMethod.POST)
    public JsonResult showSonDoc(@RequestBody String object) {
        return departmentService.showSonDoc(object);
    }
}
