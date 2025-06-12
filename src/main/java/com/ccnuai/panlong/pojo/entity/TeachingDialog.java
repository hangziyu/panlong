package com.ccnuai.panlong.pojo.entity;

import lombok.Data;

@Data
public class TeachingDialog {


    private Long id; // 主键 ID，自增


    private String userId; // 用户唯一标识


    private String sessionId; // 会话 ID


    private String exerciseId; // 教学关联的题目 ID


    private String role; // 参与者角色（user/agent）


    private String content; // 对话内容
}
