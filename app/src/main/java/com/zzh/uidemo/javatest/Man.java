package com.zzh.uidemo.javatest;

public class Man extends Person {

    private int age;

    public Man(){

    }

    public Man(String name, int age){
        super(name);
//        super(name, age);
        super.age = age;
        this.age = age;

        super.setName(name);
        this.setName(name);
    }

    public void setName(String name){

    }

    public void setName1(String name){

    }


}
