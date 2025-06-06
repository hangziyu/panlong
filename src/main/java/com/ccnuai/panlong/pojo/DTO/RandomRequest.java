package com.ccnuai.panlong.pojo.DTO;

import lombok.Data;

@Data
public class RandomRequest {

    private String questionPath;//题目路径
    private String session_id;
    private String knowledgeConcept;
    private String imagecontent;

}
