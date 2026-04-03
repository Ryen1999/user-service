//package com.example.rocketmq;
//
//import com.example.domain.dto.content.BonusEventLog;
//import com.example.domain.dto.messaging.UserAddBonusMsgDTO;
//import com.example.userservice.dao.BonusEventLogDao;
//import com.example.userservice.dao.UserInfoDao;
//import com.example.userservice.dto.UserInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import java.util.Date;
//
///**
// * 监听器
// */
//@Slf4j
//@Service
//@RocketMQMessageListener(
//    topic = "add-bonus",
//    consumerGroup = "consumer-group-add-bonus"
//)
//public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {
//
//    @Autowired
//    UserInfoDao userInfoDao;
//
//    @Autowired
//    BonusEventLogDao bonusEventLogDao;
//
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void onMessage(UserAddBonusMsgDTO message) {
//
//        log.info("========== 收到 add-bonus 消息 ==========");
//        log.info("收到消息：{}", message);
//
//        if (message == null) {
//            throw new IllegalArgumentException("消息不能为空");
//        }
//
//        Integer userId = message.getUserId();
//        Integer bonus = message.getBonus();
//        String bizId = message.getBizId(); // 新增
//
//        if (userId == null || userId <= 0) {
//            throw new IllegalArgumentException("用户 ID 无效：" + userId);
//        }
//
//        if (bonus == null || bonus == 0) {
//            throw new IllegalArgumentException("积分值无效：" + bonus);
//        }
//
//        if (bizId == null || bizId.isEmpty()) {
//            throw new IllegalArgumentException("bizId不能为空");
//        }
//
//        try {
//            // 第一步：先做幂等（插入唯一记录）
//            bonusEventLogDao.insertBonusEventLog(
//                    BonusEventLog.builder()
//                            .userId(userId)
//                            .value(bonus)
//                            .event("CONTRIBUTE")
//                            .bizId(bizId) //  使用 bizId 字段
//                            .createTime(new Date())
//                            .description("投稿加积分")
//                            .build()
//            );
//
//            // 如果能执行到这里 → 说明是第一次消费
//
//            // 第二步：再执行业务
//            UserInfo user = userInfoDao.queryUserInfoByUserId(String.valueOf(userId));
//
//            if (user == null) {
//                throw new RuntimeException("用户不存在：" + userId);
//            }
//
//            log.info("当前用户{}:积分：{}, 增加：{}", user.getId(), user.getBonus(), bonus);
//
//            user.setBonus(user.getBonus() + bonus);
//            user.setUpdateTime(new Date());
//            userInfoDao.updateUserInfo(user);
//
//        } catch (DuplicateKeyException e) {
//            // 核心：唯一索引冲突 = 已消费
//            log.warn("重复消费，bizId: {}，直接忽略", bizId);
//            return;
//
//        } catch (Exception e) {
//            log.error("处理加积分失败", e);
//            throw new RuntimeException(e);
//        }
//
//        log.info("========== add-bonus 消息处理完成 ==========");
//    }
//}
