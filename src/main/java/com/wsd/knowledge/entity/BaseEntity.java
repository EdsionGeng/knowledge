package com.wsd.knowledge.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/30.
 */
public class BaseEntity implements Serializable {

    private String id;
    private String delFalg;
    private Date addTime;
    private Date updTime;

    public void add(){
        this.delFalg = "0";
        this.addTime = new Date();
    }

    public void update(){
        this.updTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelFalg() {
        return delFalg;
    }

    public void setDelFalg(String delFalg) {
        this.delFalg = delFalg;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }
}
