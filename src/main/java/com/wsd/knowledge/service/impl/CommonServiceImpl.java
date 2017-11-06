package com.wsd.knowledge.service.impl;

import com.google.common.collect.Lists;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.mapper.CommonMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.CommonService;
import com.wsd.knowledge.util.JsonResult;
import com.wsd.knowledge.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author EdsionGeng
 * @Description 公共模块业务逻辑层实现类
 * @Date:9:19 2017/10/31
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private UserRepositoty userRepositoty;

    @Override
    public List<NewDepartment> getListByTree(NewDepartment department) {
        return bulid(userRepositoty.findList(department),department.getPid());
    }

        /**
         * 部门树形操作
         *
         * @param treeNodes
         * @return
         */
        public List<NewDepartment> bulid(List<NewDepartment> treeNodes, String pid) {
            List<NewDepartment> trees = new ArrayList<>();
            for (NewDepartment treeNode : treeNodes) {
                if (StringUtils.equals(pid, treeNode.getId())) {
                    treeNode.setChecked(1);
                }
                if (StringUtils.equals("0", treeNode.getPid())) {
                    trees.add(treeNode);
                }
                for (NewDepartment it : treeNodes) {
                    if (StringUtils.equals(it.getPid(), treeNode.getId())) {
                        if (treeNode.getChildren() == null) {
                            treeNode.setChildren(new ArrayList<>());
                        }
                        treeNode.getChildren().add(it);
                    }
                }
            }
            List<NewDepartment> list = Lists.newArrayList();
            NewDepartment zero = new NewDepartment();
            zero.setId("0");
            zero.setName("公司");
            zero.setChildren(trees);
            if (StringUtils.equals(pid, "0")) {
                zero.setChecked(1);
            }
            list.add(zero);
            return list;
        }

}
