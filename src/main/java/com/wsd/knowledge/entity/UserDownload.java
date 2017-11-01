package com.wsd.knowledge.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author EdsionGeng
 * @Description用户下载实体类
 * @Date:14:30 2017/11/1
 */
@Entity
public class UserDownload implements Serializable {
    @Id
    @GeneratedValue

    private int id;
    @Column(name = "userId", nullable = false, columnDefinition = " '用户ID'")
    private int userId;

    @Column(name = "fileId", nullable = false, columnDefinition = " '文件ID'")
    private int fileId;

    @Column(name = "downloadTime", nullable = false, length = 32, columnDefinition = " '文件下载时间'")
    private String downloadTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(String downloadTime) {
        this.downloadTime = downloadTime;
    }

    public UserDownload(int userId, int fileId, String downloadTime) {

        this.userId = userId;
        this.fileId = fileId;
        this.downloadTime = downloadTime;
    }

    public UserDownload() {
    }
}
