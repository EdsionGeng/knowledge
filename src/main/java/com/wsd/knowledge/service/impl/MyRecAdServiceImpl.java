package com.wsd.knowledge.service.impl;


import com.wsd.knowledge.entity.RdPage;
import com.wsd.knowledge.entity.UserRecAdvertisement;
import com.wsd.knowledge.mapper.UserRecAdMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.MyRecAdService;
import com.wsd.knowledge.util.DateUtil;
import com.wsd.knowledge.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
     * @param current
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public JsonResult showAllRecAd(Integer current, Integer pageSize, Integer userId) {
        if (userId == null || current == null || pageSize == null) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        int startSize = (current - 1) * pageSize;
        List<Map> map = userRecAdMapper.showUserRecAd(userId, startSize, pageSize);
        List<Map> newList = new ArrayList(new HashSet(map));
        RdPage page = new RdPage();
        Integer sum = userRecAdMapper.countUserAdPcs(userId);
        page.setTotal(sum);
        page.setPages(sum % pageSize == 0 ? sum / pageSize : sum / pageSize + 1);
        page.setCurrent(current);
        page.setPageSize(pageSize);
        return new JsonResult(0,newList, "查询结果", page);
    }
    /**
     * 发送公告给相应的接收人员
     *
     * @param userIds
     * @param commonId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public JsonResult sendAdToUser(String userIds, Integer commonId) {


        if (commonId == null || userIds.equals("")) {
            return new JsonResult(2, 0, "参数为空", 0);
        }
        String str = new DateUtil().cacheExist(String.valueOf(commonId));
        if (str.equals("full")) {
            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
        }
        //转换为List集合，查找对应的组
        List<Integer> departmentId = new ArrayList<>();
        for (String id : userIds.split(",")) {
            departmentId.add(Integer.parseInt(id));
        }
        Integer result = 0;
        //查找部门下面人员ID
        for (int i = 0, len = departmentId.size(); i < len; i++) {
            //生成实体类，进行插入语句执行
            String results = new DateUtil().cacheExist(String.valueOf(departmentId.get(i)));
            if (results.equals("full")) {
                return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
            }
            UserRecAdvertisement userRecAdvertisement = new UserRecAdvertisement(commonId, departmentId.get(i), 0, new DateUtil().getSystemTime());
            result = userRecAdMapper.insertUserRecAd(userRecAdvertisement);
        }
        if (result != 0) {
            return new JsonResult(0, 0, "添加成功", 0);
        }
        return new JsonResult(2, 0, "添加失败", 0);
    }
//    /**
//     * 发送公告给相应的接收人员
//     *
//     * @param userIds
//     * @param commonId
//     * @return
//     */
//    @Override
//    @Transactional(readOnly = false)
//    public JsonResult sendAdToUser(String userIds, Integer commonId) {
//
//
//        if (commonId == null || userIds.equals("")) {
//            return new JsonResult(2, 0, "参数为空", 0);
//        }
//        String str = new DateUtil().cacheExist(String.valueOf(commonId));
//        if (str.equals("full")) {
//            return new JsonResult(2, 0, "网络延时，请稍后加载", 0);
//        }
////转换为List集合，查找对应的组
//        List<Integer> departmentId = new ArrayList<>();
//        for (String id : userIds.split(",")) {
////             System.out.println(id);
//            departmentId.add(Integer.parseInt(id));
//        }
//        List<Integer> groupList = new ArrayList<>();
//        for (int i = 0, len = departmentId.size(); i < len; i++) {
//            groupList.add(userRepositoty.queryGroupId(departmentId.get(i)));
//        }
//        //list集合去重
//        Set<Integer> set = new HashSet<>();
//        set.addAll(groupList);//给set填充
//        groupList.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的
//        groupList.addAll(set);//把set
//        //拿剩下的list查找对应的人
//       // System.out.println(groupIds);
//        String resu=groupIds.substring(1,groupIds.length());
//        List<Integer> allGroupList = new ArrayList<>();
//        for (String id : resu.split(",")) {
//            allGroupList.add(Integer.parseInt(id));
//            //System.out.print("组别的"+allGroupList);
//        }
//        allGroupList.removeAll(groupList);
//        List<Integer> userlist = new ArrayList<>();
//        for (int i = 0, len = allGroupList.size(); i < len; i++) {
//            userlist.addAll(userRepositoty.queryUserIdByGroup(allGroupList.get(i)));
//        }
//        departmentId.addAll(userlist);
//        Set<Integer> sets = new HashSet<>();
//        sets.addAll(departmentId);//给set填充
//        departmentId.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的
//        departmentId.addAll(sets);
//        //再次去重，帅选数据
//        Integer result = 0;
//        //查找部门下面人员ID
//        for (int i = 0, len = departmentId.size(); i < len; i++) {
//            //生成实体类，进行插入语句执行
//            UserRecAdvertisement userRecAdvertisement = new UserRecAdvertisement(commonId, departmentId.get(i), 0, new DateUtil().getSystemTime());
//            result = userRecAdMapper.insertUserRecAd(userRecAdvertisement);
//            System.out.println("此次所选的人"+departmentId.get(i));
//            //          }
//        }
//        if (result != 0) {
//            return new JsonResult(0, 0, "添加成功", 0);
//        }
//        return new JsonResult(2, 0, "添加失败", 0);
//    }
}
