package com.example.userservice.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.example.userservice.aop.annotation.RequireLogin;
import com.example.userservice.dao.UserInfoDao;
import com.example.userservice.dto.UserInfo;
import com.example.userservice.interfaces.dto.user.JwtTokenRespDTO;
import com.example.userservice.interfaces.dto.user.LoginRespDTO;
import com.example.userservice.interfaces.dto.user.UserLoginDTO;
import com.example.userservice.interfaces.dto.user.UserRespDTO;
import com.example.userservice.security.jwt.JwtOperator;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录
 * @Author 郭旺
 * @Date 16:06 2026.4.11
 * @Param
 * @return
 **/
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    WxMaService wxMaService;

    @Autowired
    UserService userService;

    @Autowired
    JwtOperator jwtOperator;

    @Autowired
    UserInfoDao userInfoDao;

    /**
     * 登录
     * @Author 郭旺
     * @Date 16:06 2026.4.11
     * @Param
     * @return
     **/
    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO userLoginDTO) throws WxErrorException {
        // 获取openid
        WxMaJscode2SessionResult result =this.wxMaService.getUserService().getSessionInfo(userLoginDTO.getCode());

        String openid =result.getOpenid();

        log.info("openid:{}",openid);

        //看用户是否在注册，如果没有注册就插入
        UserInfo userInfo = userService.login(userLoginDTO,openid);

        //颁发token
        Map<String,Object> claims =new HashMap<>(3);
        claims.put("id",userInfo.getId());
        claims.put("wxNickName",userInfo.getWxNickname());
        claims.put("role",userInfo.getRoles());

        String token = jwtOperator.generateToken(claims);
        log.info("用户:{}登录成功，生成的token：{},有效期到:{}",userInfo.getWxNickname(),token,jwtOperator.getExpirationDateFromToken(token));
        LoginRespDTO loginRespDTO = LoginRespDTO.builder().token(JwtTokenRespDTO.builder()
                .token(token)
                .expirationTime(jwtOperator.getExpirationDateFromToken(token).getTime())
                .build())
                .user(UserRespDTO.builder()
                        .id(userInfo.getId())
                        .wxNickname(userInfo.getWxNickname())
                        .avatarUrl(userInfo.getAvatarUrl())
                        .bonus(userInfo.getBonus()).build()
                ).build();
        return  loginRespDTO;


    }

    /**
     * 获取用户信息
     * @Author 郭旺
     * @Date 16:06 2026.4.11
     * @Param
     * @return
     **/
    @PostMapping("/info")
    @RequireLogin
    public UserInfo queryUserInfoById(@RequestParam String id) {
        log.info("queryUserInfoById:{}", id);
        UserInfo user = userInfoDao.queryUserInfoByWxId(id);
        return user;
    }

}
