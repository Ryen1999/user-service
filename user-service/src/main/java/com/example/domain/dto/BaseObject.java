package com.example.domain.dto;

import lombok.Data;

@Data
public class BaseObject {
    private String id;

    private String MSGId;


    public BaseObject() {
        this.id = java.util.UUID.randomUUID().toString();
    }
}
