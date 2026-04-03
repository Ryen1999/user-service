package com.example.rocketmq;

import com.example.domain.dto.messaging.UserAddBonusMsgDTO;
import com.example.userservice.dao.BonusEventLogDao;
import com.example.userservice.dao.UserInfoDao;
import com.example.userservice.service.AddBonusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration  // ① 改成 @Configuration，用于承载 @Bean 方法
public class AddBonusStreamListener {

    @Autowired
    private AddBonusService addBonusService; // ② 把 @Transactional 业务逻辑抽到 Service

    // ③ 方法名 "addBonus" 决定了 yaml 里的 binding 名：addBonus-in-0
    @Bean
    public Consumer<Message<UserAddBonusMsgDTO>> addBonus() {
        return msg -> {
            UserAddBonusMsgDTO message = msg.getPayload();

            log.info("========== 收到 add-bonus 消息 ==========");
            log.info("收到消息：{}", message);

            if (message == null) {
                throw new IllegalArgumentException("消息不能为空");
            }

            Integer userId = message.getUserId();
            Integer bonus = message.getBonus();
            String bizId = message.getBizId();

            if (userId == null || userId <= 0) {
                throw new IllegalArgumentException("用户 ID 无效：" + userId);
            }
            if (bonus == null || bonus == 0) {
                throw new IllegalArgumentException("积分值无效：" + bonus);
            }
            if (bizId == null || bizId.isEmpty()) {
                throw new IllegalArgumentException("bizId 不能为空");
            }

            // ④ 调 Service 方法，@Transactional 写在 Service 里才生效
            //   （Lambda 内部直接加 @Transactional 是无效的，Spring AOP 代理不到）
            addBonusService.addBonus(userId, bonus, bizId);

            log.info("========== add-bonus 消息处理完成 ==========");
        };
    }
}