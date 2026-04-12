package com.example.userservice.interfaces.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRespDTO {
    /**
     * 用户id
     */
    private String id;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 积分
     */
    private Integer bonus;

    /**
     * 昵称
     */
    private String wxNickname;
}
