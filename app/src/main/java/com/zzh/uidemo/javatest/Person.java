package com.zzh.uidemo.javatest;

public class Person {

    private String name;

    public int age;

    public Person(){

    }

    public Person(String name){

    }

    public Person(String name, int age){

    }

    private int getAge(){
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
