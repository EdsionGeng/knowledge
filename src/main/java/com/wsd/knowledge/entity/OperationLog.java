package com.wsd.knowledge.entity;



import java.io.Serializable;

/**
 * @Author EdsionGeng
 * @Description 文件操作日志实体类
 * @Date:14:57 2017/11/1
 */

public class OperationLog implements Serializable {

   // @Id
   // @GeneratedValue
    private int id;

   // @Column(name = "departmentName", nullable = false, length=64,columnDefinition=" '部门名字'")
    private String departmentName;//部门名字

   // @Column(name = "userId", nullable = false, columnDefinition=" '用户ID'")
    private int userId;//用户ID

    //@Column(name = "username", nullable = false, columnDefinition=" '用户ID'")
    private String username;//用户ID

    private String companyId;

   // @Column(name = "fileId", nullable = false, columnDefinition=" '文件ID'")
    private int fileId;//文件ID


   // @Column(name = "operationStyle", nullable = false, columnDefinition=" '操作文档动作类型'")
    private int operationStyle;//操作类型 1添加文档 2删除文档  3更改文档 4 查阅文档 5下载附件

   //@Column(name = "operationTime", nullable = false, length=64,columnDefinition=" '操作时间'")
    private String operationTime;//操作文件时间

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getOperationStyle() {
        return operationStyle;
    }

    public void setOperationStyle(int operationStyle) {
        this.operationStyle = operationStyle;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public OperationLog(String departmentName,String username, int userId, int fileId, int operationStyle, String operationTime,String companyId) {
        this.departmentName = departmentName;
        this.username=username;
        this.userId = userId;
        this.fileId = fileId;
        this.operationStyle = operationStyle;
        this.operationTime = operationTime;
        this.companyId=companyId;
    }

}
