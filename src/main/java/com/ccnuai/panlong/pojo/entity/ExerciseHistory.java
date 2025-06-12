package com.ccnuai.panlong.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseHistory {


    private Long id;

    private String userId;
    private String sessionId;
    private String exerciseId;

    private String userAnswer;

    private String correctAnswer;

    private Boolean isCorrect;
    private String masteryLevel;

    private String questionContent;

    private LocalDateTime timestamp;
}
