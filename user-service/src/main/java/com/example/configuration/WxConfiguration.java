package com.example.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfiguration {

    public static final String APP_ID = "wxba5944d051febd37";
    public static final String APP_SECRET = "dbb14d68739dd3016c6bc8540c17c20e";

    @Bean
    public WxMaConfig WxMaConfig() {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl() ;
        config.setAppid(APP_ID);
        config.setSecret(APP_SECRET);
        return config;
    }

    @Bean
    public WxMaService WxMaService(WxMaConfig wxMaConfig) {
        WxMaServiceImpl service = new WxMaServiceImpl();
        service.setWxMaConfig(wxMaConfig);
        return service;
    }
}
