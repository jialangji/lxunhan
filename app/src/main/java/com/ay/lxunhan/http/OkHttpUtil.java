package com.ay.lxunhan.http;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.ay.lxunhan.utils.Contacts.NETWORK_INTERCEPTOR_TYPE_HEADER;
import static com.ay.lxunhan.utils.Contacts.NETWORK_INTERCEPTOR_TYPE_LOG;
import static com.ay.lxunhan.utils.Contacts.NETWORK_INTERCEPTOR_TYPE_PUBLIC_PARAMS;


class OkHttpUtil {
    static Interceptor getInterceptor(int type) {
        Interceptor mInterceptor = null;
        switch (type) {
            case NETWORK_INTERCEPTOR_TYPE_LOG:
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.e("OKHTTP", message));
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                mInterceptor = loggingInterceptor;
                break;

            case NETWORK_INTERCEPTOR_TYPE_HEADER:
                //设置头拦截器
                mInterceptor = (Interceptor.Chain chain) -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder()
                            .method(originalRequest.method(), originalRequest.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                };
                break;
            case NETWORK_INTERCEPTOR_TYPE_PUBLIC_PARAMS:
                //公共参数拦截器
                mInterceptor = chain -> {
                    Request originalRequest = chain.request();
                    Request request;
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                };
                break;

        }
        return mInterceptor;
    }

    //设置http配置信息
    static void setHttpConfig(OkHttpClient.Builder builder) {
        //希望超时时能重连
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
    }


}
