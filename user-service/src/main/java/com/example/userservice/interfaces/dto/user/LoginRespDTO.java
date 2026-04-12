package com.example.userservice.interfaces.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRespDTO {

    /**
     * token
     */
    private JwtTokenRespDTO token;

    /**
     * 用户信息
     */
    private UserRespDTO user;
}
