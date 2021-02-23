package com.zzh.uidemo.recyclerview.bean;

import java.util.List;

public class RightBean {
    private String type;

    private String id;

    private String name;

    private String status;

    private int node;

    private String parentId;

    private boolean leaf;

    private List<InnerBean> childList;

    private boolean isChecked;

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setNode(int node){
        this.node = node;
    }
    public int getNode(){
        return this.node;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    public String getParentId(){
        return this.parentId;
    }
    public void setLeaf(boolean leaf){
        this.leaf = leaf;
    }
    public boolean getLeaf(){
        return this.leaf;
    }
    public void setChildList(List<InnerBean> childList){
        this.childList = childList;
    }
    public List<InnerBean> getChildList(){
        return this.childList;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
