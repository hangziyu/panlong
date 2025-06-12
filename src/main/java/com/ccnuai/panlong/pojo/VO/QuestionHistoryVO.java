package com.ccnuai.panlong.pojo.VO;

import com.ccnuai.panlong.pojo.DTO.ChatDTO;
import com.ccnuai.panlong.pojo.entity.AnswerRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QuestionHistoryVO {
    @JsonProperty("answer_records")
    private Map<String, AnswerRecord> answerRecords;// 题目ID -> 回答记录
    @JsonProperty("mastery_levels")
    private Map<String, String> masteryLevels;// 题目ID -> 掌握程度
    @JsonProperty("teaching_history")
    private Map<String, List<ChatDTO>> teachingHistory;// 题目ID -> 教学历史

}
