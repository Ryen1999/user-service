package com.example.userservice.service;

import com.example.domain.dto.content.BonusEventLog;
import com.example.userservice.dao.BonusEventLogDao;
import com.example.userservice.dao.UserInfoDao;
import com.example.userservice.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class AddBonusService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BonusEventLogDao bonusEventLogDao;

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(Integer userId, Integer bonus, String bizId) {
        try {
            // 第一步：先做幂等（插入唯一记录）
            bonusEventLogDao.insertBonusEventLog(
                    BonusEventLog.builder()
                            .userId(userId)
                            .value(bonus)
                            .event("CONTRIBUTE")
                            .bizId(bizId)
                            .createTime(new Date())
                            .description("投稿加积分")
                            .build()
            );

            // 第二步：执行业务
            UserInfo user = userInfoDao.queryUserInfoByWxId(String.valueOf(userId));
            if (user == null) {
                throw new RuntimeException("用户不存在：" + userId);
            }

            log.info("当前用户 {}：积分：{}，增加：{}", user.getId(), user.getBonus(), bonus);
            user.setBonus(user.getBonus() + bonus);
            user.setUpdateTime(new Date());
            userInfoDao.updateUserInfo(user);

        } catch (DuplicateKeyException e) {
            log.warn("重复消费，bizId: {}，直接忽略", bizId);
            // 注意：这里不能抛异常，否则消息会被重新投递，变成死循环
        }
    }
}