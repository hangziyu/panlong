package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.ChapterMapper;
import com.ccnuai.panlong.pojo.entity.Chapter;
import com.ccnuai.panlong.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;


    /**
     * 查询章节
     * @return
     */
    @Transactional
    @Override
    public List<Chapter> findChapter(Integer bookId) {
        //1.查询出所有章节
        List<Chapter> chapterList = chapterMapper.queryChapterById(bookId);
        //2.构建一个map parentId -> List<Chapter>
        Map<Integer,List<Chapter>> parentMap = new HashMap<>();
        //3.遍历章节
        for (Chapter chapter : chapterList) {
            parentMap.computeIfAbsent(chapter.getParentId(),k -> new ArrayList<>()).add(chapter);
        }
        //4.构建树
        return buildTree(0,parentMap);

    }

    /**
     * 构建树
     * @param parentId
     * @param parentMap
     * @return
     */
    private List<Chapter> buildTree(Integer parentId, Map<Integer, List<Chapter>> parentMap) {
        List<Chapter> children = parentMap.getOrDefault(parentId, new ArrayList<>());
        for (Chapter chapter : children) {
            chapter.setChildren(buildTree(chapter.getId(), parentMap));
        }
        return children;
    }
}
