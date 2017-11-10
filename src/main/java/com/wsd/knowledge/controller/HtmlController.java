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

    @RequestMapping("adminindex")
    public String adminIndex() {
        return "admin_index";
    }

    /**
     * 个人首页
     *
     * @return
     */

    @RequestMapping("personindex")
    public String personIndex() {
        return "person_index";
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
     * 跳转到全部文件展示页
     *
     * @return
     */
    @RequestMapping("showFile")
    public String showAllFile() {
        return "showAllFile";
    }

    /**
     * 跳转个人中心页面
     *
     * @return
     */
    @RequestMapping("turn/usermsg")
    public String showUserMsg() {
        return "mymessage";
    }

    /**
     * 跳转管理员下载页面
     *
     * @return
     */
    @RequestMapping("turn/admindown")
    public String showAdminDown() {
        return "admin_download";
    }

    /**
     * 跳转管理员下载页面
     *
     * @return
     */
    @RequestMapping("turn/persondown")
    public String showPersonDown() {
        return "user_download";
    }

    /**
     * 跳转文档管理页面
     *
     * @return
     */
    @RequestMapping("turn/docmanage")
    public String turnDocManage() {
        return "docmanage";
    }
    /**
     * 跳转文档目录管理页面
     *
     * @return
     */
    @RequestMapping("turn/doclist")
    public String turnDocList() {
        return "doclist";
    }
    /**
     * 跳转消息管理页面
     *
     * @return
     */
    @RequestMapping("turn/msgmanage")
    public String turnMsgManage() {
        return "msgmanage";
    }

    /**
     * 跳转管理员历史上传
     *
     * @return
     */
    @RequestMapping("turn/adminup")
    public String turnAdminUp() {
        return "admin_up";
    }


    /**
     * 跳转个人历史上传
     *
     * @return
     */
    @RequestMapping("turn/personup")
    public String turnPersonUp() {
        return "person_up";
    }
}
