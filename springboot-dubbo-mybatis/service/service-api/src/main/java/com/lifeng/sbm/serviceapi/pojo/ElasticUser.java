package com.lifeng.sbm.serviceapi.pojo;

import java.io.Serializable;

/**
 * Created by lifeng on 2018/5/19.
 */
public class ElasticUser implements Serializable {

    private int id;

    private String code;

    private String name;

    private String sex;

    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
