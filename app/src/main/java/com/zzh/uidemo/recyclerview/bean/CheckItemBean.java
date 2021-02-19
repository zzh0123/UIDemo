package com.zzh.uidemo.recyclerview.bean;

import java.io.Serializable;
import java.util.List;

public class CheckItemBean implements Serializable {

    private String title;
    private int num;
    private int editNum;
    private String imgUrl;
    private boolean isChecked;

    public CheckItemBean(String title, int num, int editNum, String imgUrl) {
        this.title = title;
        this.num = num;
        this.editNum = editNum;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getEditNum() {
        return editNum;
    }

    public void setEditNum(int editNum) {
        this.editNum = editNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
