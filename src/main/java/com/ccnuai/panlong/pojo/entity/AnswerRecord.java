package com.ccnuai.panlong.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRecord {

    private Long id;// 主键id

    private Long userId;// 用户ID

    private String questionId;// 题目ID

    private String userAnswer;// 用户答案

    private Boolean isCorrect;// 是否正确

    private String masteryLevel;// 掌握程度

    private String teachingHistory; // JSON 字符串，建议使用 TEXT 存储

    private LocalDateTime createTime;// 创建时间
}
