package com.example.userservice.dao;

import com.example.userservice.domain.model.BonusEventLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BonusEventLogDao {
    int insertBonusEventLog(BonusEventLog bonusEventLog);
}
