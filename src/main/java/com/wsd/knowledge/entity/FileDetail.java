package com.wsd.knowledge.entity;

import java.util.Comparator;

/**
 * @Author EdsionGeng
 * @Description 文件具体信息实体类
 * @Date:10:56 2017/11/1
 */
//@Entity
public class FileDetail implements Comparable<FileDetail> {

    // @Id
    //@GeneratedValue
    private int id;

    // @Column(name = "departmentName", nullable = false, length = 32, columnDefinition = " '部门名字'")
    private String departmentName;//上传部门

    // @Column(name = "userId", nullable = false, columnDefinition = " '用户ID'")
    private int userId;//上传用户ID

    //文件种类  0 普通文件 1部门文件 2 公司文件
    private int fileSpecies;

    //@Column(name = "username", nullable = false, length = 32, columnDefinition = " '用户名字'")
    private String username;//上传用户ID
    //@Column(name = "fileNo", nullable = false, length = 32, columnDefinition = " '文件编号'")
    private String fileNo;//文件编号

    // @Column(name = "title", nullable = false, length = 96, columnDefinition = " '文件标题'")
    private String title;//文件标题

    // @Column(name = "fileStyle", nullable = false, length = 32, columnDefinition = " '文件类型'")
    private String fileStyle;//文件类型

    // @Column(name = "fileContent", nullable = false, length = 4896, columnDefinition = " '文件内容'")
    private String fileContent;//文件内容

    // @Column(name = "fileSize", nullable = false, length = 64, columnDefinition = " '文件大小'")
    private String fileSize;//文件大小

    //  @Column(name = "fileUrl", length = 688, columnDefinition = " '附件URL'")
    private String fileUrl;//附件URL

    // @Column(name = "fileStyleId", nullable = false, columnDefinition = " '文件类型层级ID'")
    private int fileStyleId;

    // @Column(name = "photoUrl", nullable = false, length = 64, columnDefinition = " '封面图片URL'")
    private String photoUrl;//封面图片URL

    // @Column(name = "addFileTime", nullable = false, columnDefinition = " '添加文件时间 yyyy-MM-dd hh:mm格式'")
    private String addFileTime;//添加文件时间

    //@Column(name = "lookPcs", nullable = false, columnDefinition = " '文件查看次数'")
    private int lookPcs;//文件查看次数

    //@Column(name = "downloadPcs", nullable = false, columnDefinition = " '文件下载次数'")
    private int downloadPcs;//文件下载次数

    //@Column(name = "updatePcs", nullable = false, columnDefinition = " '文件修改次数'")
    private int updatePcs;//文件下载次数
    //@Column(name = "fileDisplay", columnDefinition = " '文件是否显示 0 不显示 1显示'")
    private int fileDisplay;//文件是否显示 0 不显示 1显示
    //附件描述
    private String enclosureInfo;

    //人员组别id
    private int userGroupId;

    //各公司Id
    private String  companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public int getFileStyleId() {
        return fileStyleId;
    }

    public void setFileStyleId(int fileStyleId) {
        this.fileStyleId = fileStyleId;
    }

    public int getUpdatePcs() {
        return updatePcs;
    }

    public void setUpdatePcs(int updatePcs) {
        this.updatePcs = updatePcs;
    }

    public int getFileDisplay() {
        return fileDisplay;
    }

    public void setFileDisplay(int fileDisplay) {
        this.fileDisplay = fileDisplay;
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

    public String getEnclosureInfo() {
        return enclosureInfo;
    }

    public void setEnclosureInfo(String enclosureInfo) {
        this.enclosureInfo = enclosureInfo;
    }

    public int getFileSpecies() {
        return fileSpecies;
    }

    public void setFileSpecies(int fileSpecies) {
        this.fileSpecies = fileSpecies;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public FileDetail(String departmentName, String username, int userId, int fileStyleId, String fileNo, String title, String fileStyle, String fileContent, String fileUrl, String photoUrl, int lookPcs, int downloadPcs, int updatePcs, String fileSize, int fileDisplay, String enclosureInfo, String addFileTime, int fileSpecies, int userGroupId,String companyId) {
        this.departmentName = departmentName;
        this.username = username;
        this.fileStyleId = fileStyleId;
        this.userId = userId;
        this.fileNo = fileNo;
        this.title = title;
        this.fileStyle = fileStyle;
        this.fileContent = fileContent;
        this.fileUrl = fileUrl;
        this.photoUrl = photoUrl;
        this.lookPcs = lookPcs;
        this.downloadPcs = downloadPcs;
        this.updatePcs = updatePcs;
        this.fileSize = fileSize;
        this.fileDisplay = fileDisplay;
        this.enclosureInfo = enclosureInfo;
        this.addFileTime = addFileTime;
        this.fileSpecies = fileSpecies;
        this.userGroupId = userGroupId;
        this.companyId=companyId;
    }
//    f.id,f.departmentName,f.username,f.fileSize,f.fileNo,f.title,f.fileUrl,f.photoUrl,f.enclosureInfo,f.addFileTime

    public FileDetail(int id, String departmentName, String username, String fileNo, String title, String fileSize, String fileUrl, String photoUrl, String addFileTime, String enclosureInfo) {
        this.id = id;
        this.departmentName = departmentName;
        this.username = username;
        this.fileNo = fileNo;
        this.title = title;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.photoUrl = photoUrl;
        this.addFileTime = addFileTime;
        this.enclosureInfo = enclosureInfo;
    }

    public FileDetail() {
    }



    @Override
    public int compareTo(FileDetail o) {
        //按创建时间排序
        if (o.getAddFileTime().compareTo(this.getAddFileTime()) > 0) {

            return 1;
        }
        if (o.getAddFileTime().compareTo(this.getAddFileTime()) < 0) {

            return -1;
        }
        return 0;
    }
}
