package com.example.userservice.dto;

import com.example.domain.dto.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo extends BaseObject {
	
	private String wxId;
	private String wxNickname;
	private String roles;
	private String avatarUrl;
	private Date createTime;
	private Date updateTime;
	private Integer bonus;
	
}
