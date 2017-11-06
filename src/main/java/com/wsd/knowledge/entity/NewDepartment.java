package com.wsd.knowledge.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
public class NewDepartment extends BaseEntity {

    private String deptno;//名称
    private String no;//外部系统编号
    private String pid;//上级id
    private String type;//级别阶级（1.部门2.组3.岗位）
    private List<NewDepartment> children;
    private int checked;//是否选中

    private String name;

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<NewDepartment> getChildren() {
        return children;
    }

    public void setChildren(List<NewDepartment> children) {
        this.children = children;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
