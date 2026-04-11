package com.example.userservice.controller;

import com.example.userservice.aop.annotation.RequireLogin;
import com.example.userservice.dao.UserInfoDao;
import com.example.userservice.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	UserInfoDao userInfoDao;
	
	/**
	 * 测试
	 * @param id
	 * @return
	 */
	@RequestMapping("/queryUserInfoById")
	@RequireLogin
	public UserInfo queryUserInfoById(@RequestParam String id) {
		log.info("queryUserInfoById:{}", id);
		UserInfo user = userInfoDao.queryUserInfoByWxId(id);
		return user;
	}
	

}
