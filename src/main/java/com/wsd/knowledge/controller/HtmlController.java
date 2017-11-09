package com.wsd.knowledge.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author EdsionGeng
 * @Description 用于跳转页面 视图交互层
 * @Date:10:32 2017/11/1
 */
@Controller
@EnableAutoConfiguration
public class HtmlController {

    /**
     * 跳到登录页
     *
     * @return
     */
    @RequestMapping("login")
    public String login() {
        return "userlogin";
    }

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    /**
     * 添加文件首页
     *
     * @return
     */
    @RequestMapping("addfile")
    public String addFile() {
        return "addFile";
    }

    /**
     * 管理员首页
     *
     * @return
     */

    @RequestMapping("adminIndex")
    public String adminIndex() {
        return "admin_index";
    }

    /**
     * 跳转到具体文件的相关操作日志
     *
     * @return
     */
    @RequestMapping("turn/filelog")
    public String turnFileLog() {
        return "fileOperationLog";
    }

    /**
     * 跳转到具体文件的相关操作日志
     *
     * @return
     */
    @RequestMapping("showFile")
    public String showAllFile() {
        return "showAllFile";
    }

}
