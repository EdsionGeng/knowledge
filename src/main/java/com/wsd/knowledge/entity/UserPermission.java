package com.wsd.knowledge.entity;
import java.io.Serializable;
/**
*@Author EdsionGeng
*@Description 用户操作文件相应权限实体类
*@Date:13:13 2017/11/1
*/

public class UserPermission implements Serializable {
    //@Id
    //@GeneratedValue
    private int id;
   // @Column(name = "fileId", nullable = false, columnDefinition = " '文件ID'")
    private int fileId;//文件ID ，和FileDetail相关联

   // @Column(name = "userId", nullable = false, columnDefinition = " '用户ID'")
    private int userId;//用户ID

   // @Column(name = "readFile", nullable = false, columnDefinition = " '对此文件是否可查看  0禁止1可以'")
    private int readFile;//对此文件是否可查看  0禁止1可以

   //@Column(name = "deleteFile", nullable = false, columnDefinition = " '对此文件是否可删除 0禁止1可以'")
    private int deleteFile;//对此文件是否可删除 0禁止1可以

    //@Column(name = "updateFile", nullable = false, columnDefinition = " '对此文件是否可编辑 0禁止1可以'")
    private int updateFile;//对此文件是否可编辑 0禁止1可以

    //@Column(name = "addPermissionTime", nullable = false, length = 32, columnDefinition = " '添加操作权限时间'")
    private String addPermissionTime;//添加操作权限时间


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReadFile() {
        return readFile;
    }

    public void setReadFile(int readFile) {
        this.readFile = readFile;
    }

    public int getDeleteFile() {
        return deleteFile;
    }

    public void setDeleteFile(int deleteFile) {
        this.deleteFile = deleteFile;
    }

    public int getUpdateFile() {
        return updateFile;
    }

    public void setUpdateFile(int updateFile) {
        this.updateFile = updateFile;
    }

    public String getAddPermissionTime() {
        return addPermissionTime;
    }

    public void setAddPermissionTime(String addPermissionTime) {
        this.addPermissionTime = addPermissionTime;
    }

    public UserPermission(int fileId, int userId, int readFile,  String addPermissionTime,int deleteFile,int updateFile) {
        this.fileId = fileId;
        this.userId = userId;
        this.readFile = readFile;
        this.addPermissionTime = addPermissionTime;
        this.deleteFile=deleteFile;
        this.updateFile=updateFile;
    }
    public UserPermission() {
    }
}
