package com.lifeng.sbm.serviceapi.pojo;

import java.io.Serializable;

/**
 * Created by lifeng on 2018/5/19.
 */
public class User implements Serializable {

    private String userName;

    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
