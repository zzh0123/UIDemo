package com.zzh.uidemo.choose.bean;

import java.util.List;

/**
 * @author: zzh
 * data : 2020/6/29
 * description：省份
 */
public class Province {
    private String code;
    private String name;
    private List<City> cityList;

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

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
