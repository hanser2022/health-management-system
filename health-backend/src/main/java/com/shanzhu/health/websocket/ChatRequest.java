package com.shanzhu.health.websocket;

import lombok.Data;

@Data
public class ChatRequest {
    private String type;
    private String text;
    private String msg;
    private Integer userId;
    private String username;
}
