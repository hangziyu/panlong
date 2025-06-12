package com.ccnuai.panlong.utils;

import com.alibaba.fastjson.JSON;
import com.ccnuai.panlong.pojo.DTO.RandomQuestionDTO;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RandomExerciseSSEClient {

    public void startSSE(RandomQuestionDTO request, SseEmitter emitter) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)  // 0 表示永不超时，适合长连接
                .build();

        String json = JSON.toJSONString(request);
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        Request httpRequest = new Request.Builder()
                .url("https://444d-223-76-127-34.ngrok-free.app/exercise/random")
                .post(body)
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);

        EventSource eventSource = factory.newEventSource(httpRequest, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                try {
                    emitter.send(SseEmitter.event()
                            .name(type)  // event: init / chunk / done / error
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
                    emitter.send(SseEmitter.event()
                            .name("error")
                            .data("SSE失败: " + t.getMessage()));
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
