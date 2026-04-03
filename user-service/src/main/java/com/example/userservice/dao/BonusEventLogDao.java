package com.example.userservice.dao;

import com.example.domain.dto.content.BonusEventLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BonusEventLogDao {
    int insertBonusEventLog(BonusEventLog bonusEventLog);
}
