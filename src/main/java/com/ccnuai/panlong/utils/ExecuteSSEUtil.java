package com.ccnuai.panlong.utils;

import com.ccnuai.panlong.listener.SSEListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;

@Slf4j
public class ExecuteSSEUtil {
    public static void executeSSE(String url,
                                  String authToken,
                                  SSEListener sseListener,
                                  String chatGlm) throws InterruptedException {
        log.info("执行sse");
        RequestBody formBody = RequestBody.create(chatGlm, MediaType.parse("application/json; charset=utf-8"));
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.addHeader("Authorization", authToken);
        Request request = requestBuilder.url(url).post(formBody).build();
        EventSource.Factory factory = EventSources.createFactory(OkHttpUtil.getInstance());
        //创建事件
        factory.newEventSource(request,sseListener);
        sseListener.getCountDownLatch().await();
    }
}
