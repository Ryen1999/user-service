package com.example.userservice.dao;

import com.example.user.api.dto.UserInfoResDTO;
import com.example.userservice.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoDao {
	
	int insertUserInfo(UserInfo  userInfo);
	
	int updateUserInfo(UserInfo user);
	
	int deleteUserInfo(String username);
	
	List<UserInfoResDTO> queryUserInfoList();
	
	UserInfo queryUserInfoByWxId(String wxId);
}
