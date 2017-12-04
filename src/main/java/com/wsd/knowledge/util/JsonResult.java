package com.wsd.knowledge.util;
import com.wsd.knowledge.entity.RdPage;

import java.io.Serializable;

public  class JsonResult implements Serializable {


    private static final long serialVersionUID = -2565603013486909913L;


    private int code;//0:代表成功;1或其他�?�代表处理失�?
    private Object data;//接收返回的数�?
    private String msg;//定义提示 信息
    private int count;//返回记录总条数
    private RdPage rdPage;
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static int getSuccess() {
        return SUCCESS;
    }

    public static int getError() {
        return ERROR;
    }

    public RdPage getRdPage() {
        return rdPage;
    }

    public void setRdPage(RdPage rdPage) {
        this.rdPage = rdPage;
    }

    public JsonResult() {
        super();
        // TODO Auto-generated constructor stub
    }

    public JsonResult(int code, Throwable e) {
        this.code = code;
        msg = e.getMessage();
        data = null;
    }

    public JsonResult(int code, Object data, String msg, int count) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
    public JsonResult(int code, Object data, String msg, RdPage rdPage) {
        super();
        this.code = code;
        this.msg = msg;
        this.rdPage = rdPage;
        this.data = data;
    }
    public JsonResult(Throwable e) {
        code = ERROR;
        msg = e.getMessage();
        data = null;
    }

    public JsonResult(int count, Object data) {
        code = SUCCESS;
        msg = "";
        this.count = count;
        this.data = data;


    }

    public JsonResult(Object data) {
        code = SUCCESS;
        msg = "";
        this.count = 1;
        this.data = data;
    }


    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        count = 0;
        data = null;
    }
}