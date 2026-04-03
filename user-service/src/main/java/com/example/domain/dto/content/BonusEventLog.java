package com.example.domain.dto.content;

import com.example.domain.dto.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BonusEventLog extends BaseObject {

    private String bizId; // 唯一业务ID，比如 shareId


    private Integer userId;

    /**
     * 积分
     */
    private Integer value;

    /**
     * 事件
     */
    private String event;

    private Date createTime;

    /**
     * 描述
     */
    private String description;
}
