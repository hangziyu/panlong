package com.ccnuai.panlong.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.ccnuai.panlong.pojo.DTO.CheckAnswerDTO;
import com.ccnuai.panlong.pojo.DTO.RandomQuestionDTO;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CheckAnswerExerciseSSEClient {

    public void startSSE(CheckAnswerDTO request, SseEmitter emitter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        String json = JSON.toJSONString(request, serializeConfig);
        System.out.println("---------------" + json + "---------------");
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        System.out.println("---------------" + body + "----------c");
        Request httpRequest = new Request.Builder()
                .url("https://444d-223-76-127-34.ngrok-free.app/exercise/check_answer")
                .addHeader("Accept", "*/*")
                .addHeader("token", "d8594674-c3b5-4eaf-8c09-e4bbb2102921")
                .addHeader("username", "jiluofu1")
                .post(body)
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);

        factory.newEventSource(httpRequest, new EventSourceListener() {

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                try {
                    emitter.send(SseEmitter.event()
                            .name(type != null ? type : "message")
                            .data(data));
                    if ("done".equals(type)) {
                        emitter.complete();
                    }
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                try {
                    String errorMsg = (t != null) ? t.getMessage() : "未知错误";
                    emitter.send(SseEmitter.event()
                            .name("error")
                            .data("SSE失败: " + errorMsg));

                    if (response != null && response.body() != null) {
                        String responseBody = response.body().string();
                        System.err.println("SSE错误响应内容: " + responseBody);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    emitter.completeWithError(t);
                }
            }

            @Override
            public void onClosed(EventSource eventSource) {
                emitter.complete();
            }
        });
    }
}
