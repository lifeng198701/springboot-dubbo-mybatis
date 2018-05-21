package com.lifeng.web.api.controller;

import com.lifeng.sbm.serviceapi.pojo.User;
import com.lifeng.sbm.serviceapi.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifeng on 2018/5/21.
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getAllUser")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

}
