package com.wsd.knowledge.entity;

import java.io.Serializable;
/**
*@Author EdsionGeng
*@Description 用户实体类
*@Date:9:21 2017/10/31
*/
public class SystemUser implements Serializable {
    private int id;
    private String username;
    private String password;
    private String department;
    private String usergroup;
    private String workWexinId;
    private String mobile;
    private String email;
    private String duty;
    private int isAdmin;
    private int userGroupId;
    private int job;
    private String tDate;

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getWorkWexinId() {
        return workWexinId;
    }

    public void setWorkWexinId(String workWexinId) {
        this.workWexinId = workWexinId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        userGroupId = userGroupId;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public SystemUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SystemUser(String username, String password,
                      String department, String usergroup,
                      String mobile, String email, String workWexinId,
                      String duty, int isAdmin, int userGroupId,
                      int job, String tDate) {
        this.username = username;
        this.password = password;
        this.department = department;
        this.usergroup = usergroup;
        this.mobile = mobile;
        this.email = email;
        this.workWexinId = workWexinId;
        this.duty = duty;
        this.isAdmin = isAdmin;
        this.userGroupId = userGroupId;
        this.job = job;
        this.tDate = tDate;
    }

    public SystemUser() {
    }
}
