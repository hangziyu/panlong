package com.ccnuai.panlong.pojo.DTO;

import lombok.Data;

@Data
public class TeachQuestionDTO {

    private String sessionId;// 会话ID

    private String exerciseId;// 题目ID

    private String question;// 题目内容

    private String roundNumber;// 轮次
}
