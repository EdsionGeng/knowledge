package com.wsd.knowledge.service.impl;

import com.wsd.knowledge.entity.CommonAdvertisement;
import com.wsd.knowledge.entity.RdPage;
import com.wsd.knowledge.entity.SystemUser;
import com.wsd.knowledge.mapper.AdvertisementMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.AdvertisementService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public JsonResult insertCommonAd(String title, String content, String sendDepartmentName, Integer userId,String adStyle) {
        if (title .equals("") || content .equals("")|| sendDepartmentName .equals("")|| userId == null||adStyle.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }


        SystemUser systemUser = userRepositoty.findInfo(userId);
        CommonAdvertisement commonAdvertisement = new CommonAdvertisement(title, content, systemUser.getDepartment(), systemUser.getUsername(),
                userId, new DateUtil().getSystemTime(), sendDepartmentName,adStyle);
        String str = new DateUtil().cacheExist(String.valueOf(userId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        if (advertisementMapper.insertAd(commonAdvertisement) != 0) {
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
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult deleteAd(String ids) {
        if (ids == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer j = null;
        for (String id : ids.split(",")) {
            j=advertisementMapper.deleteAd(id);
            advertisementMapper.deleteRecAd(id);
        }

        if (j != 0) {

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
        if (advertisementMapper.updateAdStatus(userId, commonId) != 0) {
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
     * @param current
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult showAllAd(String title, String date1, String date2,String adStyle,String sortType ,Integer current, Integer pageSize) {
        if (current == null || pageSize == null) {
            current = 1;
            pageSize = 20;
        }
        if (title=="") {
            title = "";
        }
        if (date1.equals("")) {
            date1 = "2016-11-01 00:00:00";
        }
        if (date2 .equals("")) {
            date2 = "";
        }
        if(adStyle.equals("")){
            adStyle="";
        }

        List<Map> map = new ArrayList<>();
        int sum = 0;
        RdPage rdPage =new RdPage();
        int startSize = (current - 1) * pageSize;
        if (title.equals("")  && date2.equals("")&&adStyle.equals("")) {
            //展示所有
            map = advertisementMapper.showAllCommon(startSize, pageSize,sortType);
            sum = advertisementMapper.countAdPcs();
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        } else {
            //组合查询
            Map<String, Object> maps = new HashMap<>();
            maps.put("title", title);
            maps.put("date1", date1);
            maps.put("date2", date2);
            maps.put("adStyle", adStyle);
            maps.put("sortType", sortType);
            maps.put("startSize", startSize);
            maps.put("limit", pageSize);
            map = advertisementMapper.showAllCommonByIf(maps);
            sum = advertisementMapper.countCommonByIf(maps);
            rdPage.setTotal(sum);
            rdPage.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
            rdPage.setCurrent(current);
            rdPage.setPageSize(pageSize);
        }
            return new JsonResult(0, map, "查询结果", rdPage);
    }

    /**
     * 展示某一公告已读未读人数
     *
     * @param commonId
     * @return
     */
    @Override
    public JsonResult showAdPcs(Integer commonId) {
        if (commonId == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        Integer pcs = advertisementMapper.countAdRead(commonId);//总数
        if (pcs == null) {
            pcs = 0;
        }
        Integer isRead = advertisementMapper.countAdNoRead(commonId);//已读数量
        if (isRead == null) {
            isRead = 0;
        }
        Integer noRead = pcs - isRead;//未读数量

        Map<String,Object> map=new HashMap<>();
        map.put("isRead",isRead);
        map.put("noRead",noRead);
        return new JsonResult(0,map,"查询结果",0);
    }
}
