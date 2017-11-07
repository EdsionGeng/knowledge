package com.wsd.knowledge.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

//@Entity
public class FileKind {
    //    @Id
//    @GeneratedValue
    private int id;

    // @Column(name = "fileKindName", nullable = false, length = 32, columnDefinition = " '文件类型名称'")
    private String fileKindName;

    //@Column(name = "fileParentId", nullable = false, columnDefinition = " '文件的上一级Id'")
    private int fileParentId;//文件的上一级Id

    // @Column(name = "operationTime", nullable = false, length = 32, columnDefinition = " '操作时间'")
    private String operationTime;

    private int checked;//是否选中

    private List<FileKind> children;

    public int getChecked() {
        return checked;
    }

    public List<FileKind> getChildren() {
        return children;
    }

    public void setChildren(List<FileKind> children) {
        this.children = children;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileKindName() {
        return fileKindName;
    }

    public void setFileKindName(String fileKindName) {
        this.fileKindName = fileKindName;
    }

    public int getFileParentId() {
        return fileParentId;
    }

    public void setFileParentId(int fileParentId) {
        this.fileParentId = fileParentId;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public FileKind(String fileKindName, int fileParentId, String operationTime) {
        this.fileKindName = fileKindName;
        this.fileParentId = fileParentId;
        this.operationTime = operationTime;
    }

    public FileKind() {
    }
}
