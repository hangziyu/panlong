package com.ccnuai.panlong.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;

    public static ConnectionPool connectionPool = new ConnectionPool(10, 5, TimeUnit.MINUTES);

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()//构建器
                            .proxy(Proxy.NO_PROXY) //来屏蔽系统代理
                            .connectionPool(connectionPool)
                            .connectTimeout(600, TimeUnit.SECONDS)//连接超时
                            .writeTimeout(600, TimeUnit.SECONDS)//写入超时
                            .readTimeout(600, TimeUnit.SECONDS)//读取超时
                            .build();
                    okHttpClient.dispatcher().setMaxRequestsPerHost(200);
                    okHttpClient.dispatcher().setMaxRequests(200);
                }
            }
        }
        return okHttpClient;
    }
}
