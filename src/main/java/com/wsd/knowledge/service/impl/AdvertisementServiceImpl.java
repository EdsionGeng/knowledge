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

        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        SystemUser systemUser = userRepositoty.findInfo(userId);
        CommonAdvertisement commonAdvertisement = new CommonAdvertisement(title, content, systemUser.getDepartment(), systemUser.getUsername(),
                userId, new DateUtil().getSystemTime(), sendDepartmentName);
        if (advertisementMapper.insertAd(commonAdvertisement) != null) {
            //发送公告给相应的人
            return new JsonResult(0, 0, "添加成功", 0);
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
            id[0] = 0;
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
}
