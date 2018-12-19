package com.withive.util.interceptor;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 自定义Http日志记录拦截器
 */
public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();

    public HttpLoggerInterceptor() {
    }

    @Override
    public void log(String message) {

        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0);
        }

        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(message);
                message = jsonObject.toString(4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mMessage.append(message.concat("\n"));
        if (message.startsWith("<-- END HTTP")) {
            Logger.i(mMessage.toString());
        }
    }
}