package com.ccnuai.panlong.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 书本实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private String id;//书本id
    private String name;//书本名称
    private String subject;//学科
    private List<Chapter> chapters;//章节列表
}
