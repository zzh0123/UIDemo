package com.zzh.uidemo;

/**
 * Author: zzhh
 * Date: 2020/12/27 21:12
 * Description: 各demo实体
 */
public class DemoBean {
    private String name;
    private Class clazz;

    public DemoBean(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
