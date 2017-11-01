package com.wsd.knowledge.entity;

/**
 * @Author EdsionGeng
 * @Description 文件具体信息实体类
 * @Date:10:56 2017/11/1
 */


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class FileDetail implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    //@Column(name = "departmentName", nullable = false, length=32,columnDefinition=" '部门名字'")
    private String departmentName;//上传部门

    //@Column(name = "userId", nullable = false, columnDefinition=" '用户ID'")
    private int userId;//上传用户ID

    // @Column(name = "fileNo", nullable = false, length=32,columnDefinition=" '文件编号'")
    private String fileNo;//文件编号

    //@Column(name = "title", nullable = false, length = 96, columnDefinition = " '文件标题'")
    private String title;//文件标题

    //@Column(name = "fileStyle", nullable = false, length=32,columnDefinition=" '文件类型'")
    private String fileStyle;//文件类型

    //@Column(name = "fileContent", nullable = false, length=4896,columnDefinition=" '文件内容'")
    private String fileContent;//文件内容

    // @Column(name = "fileSize", nullable = false, length=64,columnDefinition=" '文件大小'")
    private String fileSize;//文件大小

    //@Column(name = "fileUrl",  length=688,columnDefinition=" '附件URL'")
    private String fileUrl;//附件URL

    //@Column(name = "photoUrl", nullable = false, length=64,columnDefinition=" '封面图片URL'")
    private String photoUrl;//封面图片URL

    //@Column(name = "addFileTime", nullable = false, columnDefinition=" '添加文件时间 yyyy-MM-dd hh:mm格式'")
    private String addFileTime;//添加文件时间

    //@Column(name = "lookPcs", nullable = false, columnDefinition=" '文件查看次数'")
    private int lookPcs;//文件查看次数

    //@Column(name = "downloadPcs", nullable = false, columnDefinition=" '文件下载次数'")
    private int downloadPcs;//文件下载次数

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileStyle() {
        return fileStyle;
    }

    public void setFileStyle(String fileStyle) {
        this.fileStyle = fileStyle;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getAddFileTime() {
        return addFileTime;
    }

    public void setAddFileTime(String addFileTime) {
        this.addFileTime = addFileTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getLookPcs() {
        return lookPcs;
    }

    public void setLookPcs(int lookPcs) {
        this.lookPcs = lookPcs;
    }

    public int getDownloadPcs() {
        return downloadPcs;
    }

    public void setDownloadPcs(int downloadPcs) {
        this.downloadPcs = downloadPcs;
    }

    public FileDetail(String departmentName, int userId, String fileNo, String title, String fileStyle, String fileContent, String fileUrl, String photoUrl, int lookPcs, int downloadPcs, String fileSize, String addFileTime) {
        this.departmentName = departmentName;
        this.userId = userId;
        this.fileNo = fileNo;
        this.title = title;
        this.fileStyle = fileStyle;
        this.fileContent = fileContent;
        this.fileUrl = fileUrl;
        this.photoUrl = photoUrl;
        this.lookPcs = lookPcs;
        this.downloadPcs = downloadPcs;
        this.fileSize = fileSize;
        this.addFileTime = addFileTime;
    }

    public FileDetail() {
    }
}
