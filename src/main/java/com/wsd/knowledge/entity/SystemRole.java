package com.wsd.knowledge.entity;

/**
 * Created by Administrator on 2017/10/11.
 */

import java.io.Serializable;

/**
 * 系统角色
 */
public class SystemRole implements Serializable{

    private int rid;

    private String name;

    private int checked;

    private int systemid;

    public int getSystemid() {
        return systemid;
    }

    public void setSystemid(int systemid) {
        this.systemid = systemid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
