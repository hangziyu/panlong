package com.ccnuai.panlong.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRecord {
    @JsonProperty("user_answer")
    private String userAnswer;// 用户的回答
    @JsonProperty("is_correct")
    private Boolean isCorrect;// 是否正确
    @JsonProperty("correct_answer")
    private String correctAnswer;// 正确的回答
    @JsonProperty("timestamp")
    private String timestamp;// 时间戳
    @JsonProperty("question_content")
    private String questionContent;// 问题内容
}
