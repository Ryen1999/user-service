package com.example.userservice.dao;

import com.example.user.api.dto.UserInfoResDTO;
import com.example.userservice.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoDao {
	
	int insertUserInfo(String username, String password);
	
	int updateUserInfo(String username, String password);
	
	int deleteUserInfo(String username);
	
	List<UserInfoResDTO> queryUserInfoList();
	
	List<UserInfo> queryUserInfo(String userId);
}
