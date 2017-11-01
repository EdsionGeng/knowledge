package com.wsd.knowledge.controller;


import com.wsd.knowledge.util.JsonResult;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class AdvertisementController {

    @RequestMapping()
    public JsonResult insertAd(String title,String content,String sendDepartmentName,Integer departmentId[]){



        return new JsonResult();
    }

}
