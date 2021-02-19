package com.zzh.uidemo.choose.bean;

import java.io.Serializable;

/**
 * @author: zzh
 * data : 2020/6/29
 * description：区县
 */
public class Area implements Serializable {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

