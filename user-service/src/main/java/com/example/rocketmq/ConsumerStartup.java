//package com.example.rocketmq;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.cloud.stream.binding.BindingsLifecycleController;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ConsumerStartup implements ApplicationListener<ApplicationReadyEvent> {
//
//    @Autowired
//    private BindingsLifecycleController bindingsLifecycleController;
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        // 应用完全就绪后再启动消费者
//        bindingsLifecycleController.changeState("addBonus-in-0", BindingsLifecycleController.State.STARTED);
//    }
//}