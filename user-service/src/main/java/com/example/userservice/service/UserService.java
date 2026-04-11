package com.example.userservice.service;

import com.example.domain.dto.user.UserLoginDTO;
import com.example.userservice.dao.UserInfoDao;
import com.example.userservice.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo login(UserLoginDTO userLoginDTO, String openId){

        UserInfo userInfo =userInfoDao.queryUserInfoByWxId(openId);

        if(userInfo == null){
            userInfo =new UserInfo() ;
            userInfo.setWxId(openId);
            userInfo.setBonus(300);
            userInfo.setWxNickname(userLoginDTO.getWxNickName());
            userInfo.setAvatarUrl(userLoginDTO.getAvatarUrl());
            userInfo.setRoles("user");
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfoDao.insertUserInfo(userInfo);

        }

        return userInfo;
    }
}
