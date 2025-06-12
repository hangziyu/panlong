package com.ccnuai.panlong.pojo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CheckAnswerVO {

    private String analysis;//针对题进行简要分析

    private String correct_answer;//正确答案

    private String isCorrect;//判断用户回答是否正确
}
