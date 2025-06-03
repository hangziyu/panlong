package com.ccnuai.panlong.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;//题目id
    private String content;//题目内容
    private String answer;//正确答案
    private String chapterId;//章节id
}
