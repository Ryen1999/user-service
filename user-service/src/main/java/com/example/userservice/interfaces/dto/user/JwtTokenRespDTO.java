package com.example.userservice.interfaces.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenRespDTO {
    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Long expirationTime;
}
