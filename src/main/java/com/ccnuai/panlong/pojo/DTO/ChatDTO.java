package com.ccnuai.panlong.pojo.DTO;

import lombok.Data;

@Data
public class ChatDTO {
    private String role;     // "user" / "assistant"
    private String content;  // 消息内容
}
