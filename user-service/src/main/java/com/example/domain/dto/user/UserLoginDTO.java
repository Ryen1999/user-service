package com.example.domain.dto.user;

import lombok.Data;

@Data
public class UserLoginDTO {
    /**
     * code
     */
    private String code;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String wxNickName;
}
