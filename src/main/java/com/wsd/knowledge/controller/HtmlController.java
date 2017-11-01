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

    @RequestMapping("login")
    public String login() {
        return "login";
    }


}
