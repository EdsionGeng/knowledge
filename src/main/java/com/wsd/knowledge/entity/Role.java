package com.wsd.knowledge.entity;

/**
 * Created by Administrator on 2017/10/12.
 */

import java.io.Serializable;

/**
 * 系统角色 实体类
 */
public class Role implements Serializable{
    private String id;

    private String name;

    private String remark;//角色描述

    private String systemid;

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
