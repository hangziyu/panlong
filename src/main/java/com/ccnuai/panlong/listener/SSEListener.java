package com.ccnuai.panlong.listener;

import com.alibaba.fastjson.JSON;
import com.ccnuai.panlong.pojo.DTO.ChatGlmDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Data
public class SSEListener extends EventSourceListener {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private ChatGlmDto chatGlmDto;
    private HttpServletResponse rp;
    private StringBuffer output = new StringBuffer();
    public SSEListener(ChatGlmDto chatGlmDto, HttpServletResponse response) {
        this.chatGlmDto = chatGlmDto;
        this.rp = response;
    }

    /**
     * 建立sse链接
     * @param eventSource
     * @param response
     */
    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        rp.setContentType("text/event-stream");
        rp.setCharacterEncoding("UTF-8");
        rp.setStatus(200);
        log.info("建立sse连接..." + JSON.toJSONString(chatGlmDto));
    }


    /**
     * 事件
     * @param eventSource
     * @param id
     * @param type
     * @param data
     */
    @Override
    public void onEvent(@NotNull EventSource eventSource,
                        @Nullable String id,
                        @Nullable String type,
                        @NotNull String data) {
        try {
            output.append(data);
            if ("finish".equals(type)) {
                log.info("请求结束{} {}", chatGlmDto.getMessageId(), output.toString());
            }
            if ("error".equals(type)) {
                log.error("请求错误{} {}", chatGlmDto.getMessageId(), output.toString());
            }
            if (rp != null) {
                rp.getWriter().write("event:" + type + "\n");
                rp.getWriter().write("id:" + chatGlmDto.getMessageId() + "\n");
                rp.getWriter().write("data:\n\n");
                rp.getWriter().flush();
            }else {
                String[] dataArr = data.split("\\n");
                for (int i = 0; i < dataArr.length; i++) {
                    if (i == 0){
                        rp.getWriter().write("event:" + type + "\n");
                        rp.getWriter().write("id:" + chatGlmDto.getMessageId() + "\n");
                    }
                    if (i == dataArr.length - 1){
                        rp.getWriter().write("data:" + dataArr[i] + "\n\n");
                        rp.getWriter().flush();
                    }else {
                        rp.getWriter().write("data:" + dataArr[i] + "\n");
                        rp.getWriter().flush();
                    }
                }
            }

        } catch (IOException e) {
            log.error("消息错误[" + JSON.toJSONString(chatGlmDto) + "]", e);
            countDownLatch.countDown();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭sse链接
     * @param eventSource
     */
    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        log.info("sse连接关闭:{}", chatGlmDto.getMessageId());
        countDownLatch.countDown();
    }

    /**
     * 失败
     * @param eventSource
     * @param t
     * @param response
     */
    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        log.error("使用事件源时出现异常... [响应：{}]...", chatGlmDto.getMessageId());
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }
}




















