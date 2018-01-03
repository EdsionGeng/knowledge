package com.wsd.knowledge.entity;


import java.io.Serializable;
/**
 *@Author EdsionGeng
 *@Description 系统里面公告实体类
 *@Date:13:46 2017/11/1
 */
//@Entity
public class CommonAdvertisement implements Serializable {
  // @Id
   // @GeneratedValue
    private int id;
   // @Column(name = "adTitle", nullable = false, length=96,columnDefinition=" '公告标题'")
    private String adTitle;//公告标题

  //  @Column(name = "adContent", nullable = false, length=1024,columnDefinition=" '公告内容'")
    private String adContent;//公告内容

   // @Column(name = "departmentName", nullable = false, length=32,columnDefinition=" '部门名字'")
    private String departmentName;

   // @Column(name = "addUser", nullable = false, length=1024,columnDefinition=" '公告人'")
    private String addUser;//添加公告人

  // @Column(name = "userId", nullable = false, columnDefinition = " '用户ID'")
    private int userId;

   // @Column(name = "sendTime",  length=96,columnDefinition=" '发送公告时间'")
    private String sendTime;//发送时间

   // @Column(name = "sendObject",  length=96,columnDefinition=" '发送具体对象'")
    private String sendObject;//发送具体对象

    //公告类型
    private String adStyle;

    //公司Id
    private int companyId;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdContent() {
        return adContent;
    }

    public String getAdStyle() {
        return adStyle;
    }

    public void setAdStyle(String adStyle) {
        this.adStyle = adStyle;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendObject() {
        return sendObject;
    }

    public void setSendObject(String sendObject) {
        this.sendObject = sendObject;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public CommonAdvertisement(String adTitle, String adContent, String departmentName, String addUser, int userId, String sendTime, String sendObject, String adStyle) {
        this.adTitle = adTitle;
        this.adContent = adContent;
        this.departmentName = departmentName;
        this.addUser = addUser;
        this.userId = userId;
        this.sendTime = sendTime;
        this.sendObject = sendObject;
        this.adStyle=adStyle;
    }

    public CommonAdvertisement(String adTitle, String adContent, String addUser, String sendTime, String adStyle,String departmentName) {
        this.adTitle = adTitle;
        this.adContent = adContent;
        this.addUser = addUser;
        this.sendTime = sendTime;
        this.adStyle = adStyle;
        this.departmentName=departmentName;
    }

    public CommonAdvertisement() {
    }
}
