package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.CommonAdvertisement;
import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper.AdvertisementMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.AdvertisementService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author EdsionGeng
 * @Description 公告实现类
 * @Date:14:02 2017/11/2
 */

@Service
@Transactional(readOnly = true)
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private UserRepositoty userRepositoty;
    @Autowired
    private AdvertisementMapper advertisementMapper;

    /**
     * 添加公告 然后发送给相应的人
     *
     * @param title
     * @param content
     * @param sendDepartmentName
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult insertCommonAd(String title, String content, String sendDepartmentName, Integer userId) {
        if (title == null || content == null || sendDepartmentName == null || userId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        CommonAdvertisement commonAdvertisement = new CommonAdvertisement(title, content, systemUser.getDepartment(), systemUser.getUsername(),
                userId, new DateUtil().getSystemTime(), sendDepartmentName);
        if (advertisementMapper.insertAd(commonAdvertisement) != null) {
            //发送公告给相应的人
            //返回公告ID
            Integer commonId = advertisementMapper.queryCommonID(title, userId);

            return new JsonResult(0, commonId, "添加成功", 0);
        }
        return new JsonResult(2, 0, "添加失败", 0);
    }


    /**
     * 删除公告操作 业务逻辑处理
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteAd(Integer[] id) {
        if (id == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        int lengths = id.length;
        for (int i = 0; i < lengths; i++) {
            j = advertisementMapper.deleteAd(id[i]);
        }
        if (j != null) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 公告已读状态
     *
     * @param userId
     * @param commonId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult readAd(Integer userId, Integer commonId) {
        if (userId == null || commonId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        if (advertisementMapper.updateAdStatus(userId, commonId) != null) {
            return new JsonResult(0, 0, "操作成功", 0);
        }
        return new JsonResult(2, 0, "操作失败", 0);
    }

    /**
     * 展示公告和组合查询
     *
     * @param title
     * @param date1
     * @param date2
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResult showAllAd(String title, String date1, String date2, Integer page, Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 20;
        }
        if (title == null) {
            title = "";
        }
        if (date1 == null) {
            date1 = "2016-11-01 00:00:00";
        }
        if (date2 == null) {
            date2 = "";
        }
        List<Map> map = null;
        Integer pcs = null;
        int startSize = (page - 1) * limit;
        if (title.equals("") && date1.equals("2016-11-01 00:00:00") && date2.equals("")) {
            //展示所有
            map = advertisementMapper.showAllCommon(startSize, limit);
            pcs = advertisementMapper.countAdPcs(startSize, limit);
        } else {
            //组合查询
            Map<String, Object> maps = new HashMap<>();
            maps.put("title", title);
            maps.put("date1", date1);
            maps.put("date2", date2);
            maps.put("startSize", startSize);
            maps.put("limit", limit);
            map = advertisementMapper.showAllCommonByIf(maps);
            pcs = advertisementMapper.countCommonByIf(maps);
        }
        if (map != null && pcs != null) {
            return new JsonResult(0, map, "查询结果", pcs);
        }

        return new JsonResult(2, 0, "查无结果", 0);
    }
}
