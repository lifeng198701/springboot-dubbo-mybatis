package com.lifeng.sdm.serviceimpl.service;


import com.lifeng.sbm.serviceapi.pojo.User;
import com.lifeng.sbm.serviceapi.service.UserService;
import com.lifeng.sdm.serviceimpl.convert.Convert;
import com.lifeng.sdm.serviceimpl.mapper.TbUserMapper;
import com.lifeng.sdm.serviceimpl.mapper.pojo.TbUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lifeng on 2018/5/19.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private TbUserMapper tbUserMapper;

    //添加事物
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getAllUser(){
        List<TbUser> tbUsers = tbUserMapper.selectAll();
        List<User> resultList = Convert.convertTbUser2User(tbUsers);
        return resultList;
    }
}
