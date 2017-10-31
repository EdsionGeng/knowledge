package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.mapper.CommonMapper;
import com.wsd.knowledge.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
*@Author EdsionGeng
*@Description 公共模块业务逻辑层实现类
*@Date:9:19 2017/10/31
*/
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;


}
