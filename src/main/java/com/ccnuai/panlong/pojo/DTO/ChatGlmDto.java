package com.ccnuai.panlong.pojo.DTO;

import lombok.Data;

@Data
public class ChatGlmDto {
    private String messageId;
    private Object prompt;
    private String requestTaskNo;
    private boolean incremental = true;
    private boolean notSensitive = true;
}