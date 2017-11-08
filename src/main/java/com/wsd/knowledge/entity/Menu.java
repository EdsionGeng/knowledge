package com.wsd.knowledge.entity;

import java.io.Serializable;
import java.util.List;
/**
*@Author EdsionGeng
*@Description 菜单
*@Date:9:10 2017/11/8
*/
public class Menu implements Serializable{

    private Integer id;
    private String name;
    private String menuurl;
    private Integer parentId;
    private String scriptid;
    private String systemId;
    private String isButton;
    private List<Menu> menuList;
    private List<Menu> children;
    private int checked;//是否选中

    public Menu(){

    }

    public Menu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuurl() {
        return menuurl;
    }

    public void setMenuurl(String menuurl) {
        this.menuurl = menuurl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getScriptid() {
        return scriptid;
    }

    public void setScriptid(String scriptid) {
        this.scriptid = scriptid;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getIsButton() {
        return isButton;
    }

    public void setIsButton(String isButton) {
        this.isButton = isButton;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
