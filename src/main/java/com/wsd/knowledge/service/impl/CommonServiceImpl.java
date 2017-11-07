package com.wsd.knowledge.service.impl;

import com.google.common.collect.Lists;
import com.wsd.knowledge.entity.FileKind;
import com.wsd.knowledge.entity.NewDepartment;
import com.wsd.knowledge.mapper.CommonMapper;
import com.wsd.knowledge.mapper1.UserRepositoty;
import com.wsd.knowledge.service.CommonService;
import com.wsd.knowledge.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 部门树形结构
     * @param department
     * @return
     */
    @Override
    public List<NewDepartment> getListByTree(NewDepartment department) {
        Map<String, Object> map = new HashMap<>();
        map.put("pid", department.getPid());
        return bulid(userRepositoty.findList(map), department.getPid());
    }

    /**
     * 文档目录树形结构
     * @param fileKind
     * @return
     */
    @Override
    public List<FileKind> getKindTree(FileKind fileKind) {
        Map<String, Object> map = new HashMap<>();
        map.put("pid", fileKind.getFileParentId());
        return bulidFileTree(commonMapper.findKindList(map), String.valueOf(fileKind.getFileParentId()));
    }
    /**
     * 部门树形操作
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

    /**
     * 文档目录树形操作
     *
     * @param treeNodes
     * @return
     */
    public List<FileKind> bulidFileTree(List<FileKind> treeNodes, String pid) {
        List<FileKind> trees = new ArrayList<>();
        for (FileKind treeNode : treeNodes) {
            if (StringUtils.equals(pid, String.valueOf(treeNode.getFileParentId()))) {
                treeNode.setChecked(1);
            }
            if (StringUtils.equals("0", String.valueOf(treeNode.getFileParentId()))) {
                trees.add(treeNode);
            }
            for (FileKind it : treeNodes) {
                if (StringUtils.equals(String.valueOf(it.getFileParentId()), String.valueOf(treeNode.getId()))) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        List<FileKind> list = Lists.newArrayList();
        FileKind zero = new FileKind();
        zero.setId(0);
        zero.setFileKindName("文档目录");
        zero.setChildren(trees);
        if (StringUtils.equals(pid, "0")) {
            zero.setChecked(1);
        }
        list.add(zero);
        return list;
    }

}
