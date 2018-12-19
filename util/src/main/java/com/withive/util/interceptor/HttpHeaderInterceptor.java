package com.withive.util.interceptor;

import android.os.Build;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义Http日志记录拦截器
 */
public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()//配置添加header信息
                .newBuilder()
                .addHeader("source-terminal", "Android")  //操作系统名称（ios,android）//设备型号
                .addHeader("device-model", Build.MODEL)         //设备型号
                .addHeader("os-version", Build.VERSION.RELEASE) //操作系统版本号
                .build();

        Logger.e("HttpHeaderInterceptor\n" +
                        "source-terminal [%s][%s]\n" +
                        "device-model [%s]\n" +
                        "os-version [%s]\n",
                request.url(), request.method(), Build.MODEL, Build.VERSION.RELEASE);

        return chain.proceed(request);
    }
}
