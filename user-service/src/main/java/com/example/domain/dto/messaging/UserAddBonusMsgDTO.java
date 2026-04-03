package com.example.domain.dto.messaging;

import com.example.domain.dto.BaseObject;
import lombok.Data;

@Data
public class UserAddBonusMsgDTO extends BaseObject {

    private String bizId; // 唯一业务ID，比如 shareId
    /**
     * 为谁增加积分
     */
    private Integer userId;

    /**
     * 加多少积分
     */
    private Integer bonus;

    /**
     * 无参构造函数
     */
    public UserAddBonusMsgDTO() {
        super();
    }
}