package com.ccnuai.panlong.service.impl;

import com.ccnuai.panlong.mapper.ExerciseHistoryMapper;
import com.ccnuai.panlong.mapper.QuestionMapper;
import com.ccnuai.panlong.mapper.TeachingDialogMapper;
import com.ccnuai.panlong.pojo.DTO.ChatDTO;
import com.ccnuai.panlong.pojo.VO.QuestionHistoryVO;
import com.ccnuai.panlong.pojo.entity.*;
import com.ccnuai.panlong.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ExerciseHistoryMapper exerciseHistoryMapper;

    @Autowired
    private TeachingDialogMapper teachingDialogMapper;



    private final RestTemplate restTemplate = new RestTemplate();


    /**
     * 随机获取题目
     * @return
     */
    @Override
    public List<Question> random(String chapterId) {
        return questionMapper.random(chapterId);
    }

    /**
     * 保存题目历史
     * @param sessionId
     */
    @Transactional
    @Override
    public void saveQuestionHistory(String sessionId) {
        //1.建立HTTP请求
        String url = "https://444d-223-76-127-34.ngrok-free.app/exercise/history/" + sessionId;

        //2.发送请求
        ResponseEntity<QuestionHistoryVO> response = restTemplate.getForEntity(url, QuestionHistoryVO.class);

        //3.处理响应
        QuestionHistoryVO history = response.getBody();
//        System.out.println(history);

        //4.保存题目历史
        //4.1判断是否为空
        if (history == null) {
            return;
        }
        //4.2获取userId
        //TODO:获取userId
//        String userId = UserHolder.getUser().getUserId();
        String userId = "1";
        //4.3获取题目历史
        Map<String, AnswerRecord> answerRecords = history.getAnswerRecords();
        Map<String, String> masteryLevels = history.getMasteryLevels();
        Map<String, List<ChatDTO>> teachingHistory = history.getTeachingHistory();
        //4.4保存题目历史
        for (String exerciseId : answerRecords.keySet()) {
            AnswerRecord record = answerRecords.get(exerciseId);
            String masteryLevel = masteryLevels.get(exerciseId);
            LocalDateTime answerTime = LocalDateTime.parse(record.getTimestamp());

            //4.4.1保存回答记录
            ExerciseHistory exerciseHistory = new ExerciseHistory();
            exerciseHistory.setUserId(userId);
            exerciseHistory.setSessionId(sessionId);
            exerciseHistory.setExerciseId(exerciseId);
            exerciseHistory.setUserAnswer(record.getUserAnswer());
            exerciseHistory.setCorrectAnswer(record.getCorrectAnswer());
            exerciseHistory.setIsCorrect(record.getIsCorrect());
            exerciseHistory.setMasteryLevel(masteryLevel);
            exerciseHistory.setQuestionContent(record.getQuestionContent());
            exerciseHistory.setTimestamp(answerTime);

            exerciseHistoryMapper.save(exerciseHistory);

        }
        //4.5保存教学历史
        for (Map.Entry<String, List<ChatDTO>> entry : teachingHistory.entrySet()) {
            String exerciseId = entry.getKey();
            List<ChatDTO> dialogList = entry.getValue();

            for (ChatDTO dialog : dialogList) {
                TeachingDialog dialogEntity = new TeachingDialog();
                dialogEntity.setUserId(userId);
                dialogEntity.setSessionId(sessionId);
                dialogEntity.setExerciseId(exerciseId);
                dialogEntity.setRole(dialog.getRole());
                dialogEntity.setContent(dialog.getContent());
                teachingDialogMapper.save(dialogEntity); // 可是 JPA 或 MyBatis Mapper
            }
        }

    }

    /**
     * 获取题目历史
     * @return
     */
    @Override
    public QuestionHistoryVO questionHistory() {

        //TODO:获取userId
//      String useId = UserHolder.getUser().getUserId();
        String userId = "1";

        //1.获取exercise_history
        List<ExerciseHistory> exerciseHistoryList = exerciseHistoryMapper.findByUserId(userId);

        //2.获取teaching_dialog
        List<TeachingDialog> teachingDialogList = teachingDialogMapper.findByUserId(userId);

        //3.封装成QuestionHistoryVO
        Map<String, AnswerRecord> answerRecords = new HashMap<>();
        Map<String, String> masteryLevels = new HashMap<>();
        Map<String, List<ChatDTO>> teachingHistory = new HashMap<>();

        //3.1封装answerRecords
        for (ExerciseHistory history : exerciseHistoryList) {
            AnswerRecord answerRecord = new AnswerRecord();
            answerRecord.setUserAnswer(history.getUserAnswer());
            answerRecord.setCorrectAnswer(history.getCorrectAnswer());
            answerRecord.setIsCorrect(history.getIsCorrect());
            answerRecord.setQuestionContent(history.getQuestionContent());
            answerRecord.setTimestamp(history.getTimestamp().toString());
            answerRecords.put(history.getExerciseId(), answerRecord);
            masteryLevels.put(history.getExerciseId(), history.getMasteryLevel());
        }
        //3.2封装teachingHistory
        for (TeachingDialog dialog : teachingDialogList) {
            String exerciseId = dialog.getExerciseId();
            ChatDTO chatDTO = new ChatDTO();
            chatDTO.setRole(dialog.getRole());
            chatDTO.setContent(dialog.getContent());
            teachingHistory.computeIfAbsent(exerciseId, k -> new java.util.ArrayList<>()).add(chatDTO);
        }
        //3.3封装QuestionHistoryVO
        QuestionHistoryVO historyVO = new QuestionHistoryVO();
        historyVO.setAnswerRecords(answerRecords);
        historyVO.setMasteryLevels(masteryLevels);
        historyVO.setTeachingHistory(teachingHistory);

        return historyVO;
    }


}
