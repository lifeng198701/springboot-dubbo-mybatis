package com.lifeng.sdm.serviceimpl.convert;

import com.lifeng.sbm.serviceapi.pojo.User;
import com.lifeng.sdm.serviceimpl.mapper.pojo.TbUser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lifeng on 2018/5/21.
 * 实体对象转化类
 */
public class Convert {

    public static List<User> convertTbUser2User(List<TbUser> tbUserList){
        return tbUserList.stream().map(v -> convertTbUser2User(v)).collect(Collectors.toList());
    }

    public static User convertTbUser2User(TbUser tbUser){
        User user = new User();
        user.setUserName(tbUser.getUserName());
        user.setPassWord(String.valueOf(tbUser.getId()));
        return user;
    }
}
