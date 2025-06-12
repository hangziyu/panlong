package com.ccnuai.panlong.pojo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RandomQuestionDTO {

    @JsonProperty("questionPath")
    private String questionPath;//题目文件路径（如 .docx 文件路径），用于加载题目数据

    @JsonProperty("session_id")
    private String sessionId;//会话 ID，如果未提供则创建新会话

    @JsonProperty("knowledgeConcept")
    private String knowledgeConcept;//如果启用自动生成题目的逻辑，用于指定知识点

    @JsonProperty("imagecontent")
    private String imageContent;//图像描述或上下文内容，可用于图像辅助生成题目
}
