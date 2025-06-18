package com.ccnuai.panlong.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * 章节实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    private Integer id;//章节id
    private Integer bookId;//书籍id
    private String name;//章节名称
    private String chapterNumber;//章节路径
    private Integer parentId;//父章节id
    private String title;//标题;

    private List<Chapter> children = new ArrayList<>();//子章节列表
}
