package com.example.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
	
	private String id;
	private String wx_id;
	private String wx_nickname;
	private String roles;
	private String avatar_url;
	private Date createTime;
	private Date updateTime;
	private String bonus;
	
}
