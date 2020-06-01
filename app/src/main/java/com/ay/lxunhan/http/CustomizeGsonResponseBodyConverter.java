package com.ay.lxunhan.http;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.ay.lxunhan.http.HttpMethods.HTTP_SUCCESS;


public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    //    private final TypeAdapter<T> adapter;
    private Type type;

    CustomizeGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String response = value.string();
        try {
            try {
                BaseBean baseBean = gson.fromJson(response, BaseBean.class);
                if (baseBean.error != HTTP_SUCCESS) {
                    throw new ApiException(baseBean.error, baseBean.message);
                }
                if (baseBean.data == null) {
                    HttpResult<Object> objectHttpResult = new HttpResult<>();
                    objectHttpResult.data = "";
                    objectHttpResult.error = baseBean.error;
                    objectHttpResult.msg = baseBean.message;
                    return (T) objectHttpResult;
                } else {
                    return gson.fromJson(response, type);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return gson.fromJson(response, type);
            }
        } finally {
            value.close();
        }
    }
}