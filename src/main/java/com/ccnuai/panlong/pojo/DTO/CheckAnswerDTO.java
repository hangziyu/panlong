package com.ccnuai.panlong.pojo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckAnswerDTO {

    @JsonProperty("complete_response")
    private String completeResponse;//问题题目

    @JsonProperty("session_id")
    private String sessionId;//会话id

    @JsonProperty("exercise_id")
    private String exerciseId;//习题id

    @JsonProperty("user_answer")
    private String userAnswer;//用户的回答

    @JsonProperty("curquestion")
    private String curquestion;//完整的题目和题目答案解析
}
