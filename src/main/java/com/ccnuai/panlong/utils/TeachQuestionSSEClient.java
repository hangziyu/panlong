package com.ccnuai.panlong.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.ccnuai.panlong.pojo.DTO.TeachQuestionDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TeachQuestionSSEClient {

    public void startSSE(TeachQuestionDTO request, SseEmitter emitter) {
        // 1.实现SSE客户端逻辑
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        // 2.序列化
        // 2.1 序列化配置，使用蛇形命名策略
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        // 2.2 将请求对象转换为JSON字符串
        String json = JSON.toJSONString(request, serializeConfig);
        log.info("---------------" + json + "---------------");
        // 3.构建HTTP请求
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request httpRequest = new Request.Builder()
                .url("https://444d-223-76-127-34.ngrok-free.app/exercise/teach")
                .addHeader("Accept", "*/*")
                .post(body)
                .build();
        // 4.创建EventSource工厂
        EventSource.Factory factory = EventSources.createFactory(client);

        // 5.创建EventSource并监听事件
        factory.newEventSource(httpRequest, new EventSourceListener() {
            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                emitter.complete();
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
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
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
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

        });
    }
}


















