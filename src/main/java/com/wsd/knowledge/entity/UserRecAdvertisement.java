package com.wsd.knowledge.entity;


import java.io.Serializable;
/**
 * @Author EdsionGeng
 * @Description 用户接收公告实体类
 * @Date:14:10 2017/11/1
 */
//@Entity
public class UserRecAdvertisement implements Serializable {
   // @Id
   // @GeneratedValue
    private int id;
    //@Column(name = "commonId", nullable = false, columnDefinition = " '公告ID'")
    private int commonId;//公告ID
   // @Column(name = "userId", nullable = false, columnDefinition = " '用户ID'")
    private int userId;//用户ID
   // @Column(name = "ifRead", nullable = false, columnDefinition = " '是否已查阅此公告 0未读 1 已读'")
    private int ifRead;//是否已查阅此公告 0未读 1 已读
   // @Column(name = "recAdTime", nullable = false, length = 96, columnDefinition = " '收到此公告时间 '")
    private String departmentName;
    private String username;
    private String recAdTime;//收到此公告时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommonId() {
        return commonId;
    }

    public void setCommonId(int commonId) {
        this.commonId = commonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIfRead() {
        return ifRead;
    }

    public void setIfRead(int ifRead) {
        this.ifRead = ifRead;
    }

    public String getRecAdTime() {
        return recAdTime;
    }

    public void setRecAdTime(String recAdTime) {
        this.recAdTime = recAdTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRecAdvertisement(int commonId, int userId, int ifRead,String departmentName,String username,String recAdTime) {
        this.commonId = commonId;
        this.userId = userId;
        this.ifRead = ifRead;
        this.departmentName=departmentName;
        this.username=username;
        this.recAdTime = recAdTime;
    }

    public UserRecAdvertisement() {
    }
}
