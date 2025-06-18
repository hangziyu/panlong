package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.BookMapper;
import com.ccnuai.panlong.mapper.ChapterMapper;
import com.ccnuai.panlong.pojo.entity.Book;
import com.ccnuai.panlong.pojo.entity.Chapter;
import com.ccnuai.panlong.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    /**
     * 查询所有书籍
     * @return
     */
    @Override
    public List<Book> query() {

        return bookMapper.query();
    }




    /**
     * 构建章节树
     * @param chapters
     * @return
     */
    private List<Chapter> buildTree(List<Chapter> chapters) {
        Map<Integer, Chapter> idToNode = new HashMap<>();
        List<Chapter> rootList = new ArrayList<>();

        for (Chapter ch : chapters) {
            idToNode.put(ch.getId(), ch);
        }

        for (Chapter ch : chapters) {
            if (ch.getParentId() == null) {
                rootList.add(ch);
            } else {
                Chapter parent = idToNode.get(ch.getParentId());
                if (parent != null) {
                    parent.getChildren().add(ch);
                }
            }
        }

        return rootList;
    }
}
