package com.wsd.knowledge.service.impl;


import com.wsd.knowledge.entity.UserRecAdvertisement;
import com.wsd.knowledge.mapper.UserRecAdMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.MyRecAdService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class MyRecAdServiceImpl implements MyRecAdService {

    @Autowired
    private UserRecAdMapper userRecAdMapper;

    @Autowired
    private UserRepositoty userRepositoty;

    /**
     * 展示个人接收所有公告数
     *
     * @param page
     * @param limit
     * @param userId
     * @return
     */
    @Override
    public JsonResult showAllRecAd(Integer page, Integer limit, Integer userId) {
        if (userId == null || page == null || limit == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (page - 1) * limit;
        List<Map> map = userRecAdMapper.showUserRecAd(userId, startSize, limit);
        return new JsonResult(0, map, "查询结果", userRecAdMapper.countUserAdPcs());
    }


    /**
     * 发送公告给相应的接收人员
     * @param departmentId
     * @param commonId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult sendAdToUser(Integer[] departmentId, Integer commonId) {
        if (commonId == null || departmentId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        String str= new DateUtil().cacheExist(String.valueOf(commonId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        Integer result = null;
        int length = departmentId.length;
        for (int i = 0; i < length; i++) {
            List<Integer> list = userRepositoty.queryUserIDByGroup(departmentId[i]);
            //查找部门下面人员ID

            for (int j = 0, len = list.size(); i < len; i++) {
                 //生成实体类，进行插入语句执行
                UserRecAdvertisement userRecAdvertisement = new UserRecAdvertisement(commonId, list.get(j), 0, new DateUtil().getSystemTime());
                result = userRecAdMapper.insertUserRecAd(userRecAdvertisement);
            }
        }
        if (result !=0) {
            return  new JsonResult(0,0,"添加成功",0);
        }
        return new JsonResult(2,0,"添加失败",0);
    }
}
