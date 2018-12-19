package com.withive.util.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.withive.util.interceptor.HttpHeaderInterceptor;
import com.withive.util.interceptor.HttpLoggerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 辅助类
 * 默认添加了请求日志记录和额外头部拦截器
 */
public class RetrofitHelper {
    private static final HashMap<String, Retrofit> mRetrofitHash = new HashMap<>();

    private RetrofitHelper() {
    }


    /**
     * 创建 retrofit 实例
     *
     * @param baseUrl                   API网址
     * @param defaultTimeout            默认超时时间，单位为秒
     * @param customInterceptors        自定义拦截器
     * @param customNetworkInterceptors 自定义网络拦截器
     * @param showMoreLog               是否调试模式
     * @return retrofit 实例
     */
    public Retrofit getRetrofit(@NonNull String baseUrl,
                                @NonNull int defaultTimeout,
                                @NonNull List<Interceptor> customInterceptors,
                                @Nullable List<Interceptor> customNetworkInterceptors,
                                boolean showMoreLog) {
        if (mRetrofitHash.containsKey(baseUrl) == false) {

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(defaultTimeout, TimeUnit.SECONDS);

            for (Interceptor interceptor : customInterceptors) {
                if (interceptor != null) {
                    httpClientBuilder = httpClientBuilder.addInterceptor(interceptor);
                }
            }

            if(customNetworkInterceptors!=null) {
                for (Interceptor interceptor : customNetworkInterceptors) {
                    if (interceptor != null) {
                        httpClientBuilder = httpClientBuilder.addNetworkInterceptor(interceptor);
                    }
                }
            }

            if (showMoreLog) {
                // 请求参数拦截器
                Interceptor requestInterceptor = new HttpLoggingInterceptor(new HttpLoggerInterceptor())
                        .setLevel(HttpLoggingInterceptor.Level.BODY);

                // 请求头部拦截器
                Interceptor headerInterceptor = new HttpHeaderInterceptor();

                httpClientBuilder = httpClientBuilder.addInterceptor(requestInterceptor)
                        .addInterceptor(headerInterceptor);
            }

            Retrofit instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();

            mRetrofitHash.put(baseUrl, instance);
        }

        return mRetrofitHash.get(baseUrl);
    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 单例
     */
    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }
}
