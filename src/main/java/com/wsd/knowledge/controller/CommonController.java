package com.wsd.knowledge.controller;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*@Author EdsionGeng
*@Description 公共模块视图交互层
*@Date:9:18 2017/10/31
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
    @ApiOperation(value = "阅读文件接口", notes = "传递必要参数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Object", name = "department", value = "文件ID"),

    })
    @RequestMapping(value="getListByTree", method= RequestMethod.GET)
    @ResponseBody
    public List<NewDepartment> getListByTree(NewDepartment department) {
        return commonService.getListByTree(department);
    }



}
