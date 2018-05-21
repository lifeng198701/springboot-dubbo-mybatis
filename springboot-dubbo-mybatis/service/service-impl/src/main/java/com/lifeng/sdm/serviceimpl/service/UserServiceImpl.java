package com.lifeng.sdm.serviceimpl.service;


import com.lifeng.sbm.serviceapi.service.UserService;
import com.lifeng.sbm.serviceapi.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lifeng on 2018/5/19.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUser(){
        User u = new User();
        u.setPassWord("654321");
        u.setUserName("lifeng");
        List<User> list = new ArrayList<>();
        list.add(u);
        return list;
    }
}
