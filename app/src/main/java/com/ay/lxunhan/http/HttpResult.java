package com.ay.lxunhan.http;

public class HttpResult<T> {
    public int error;
    public String msg;
    public String message;
    public T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return error;
    }

    public void setCode(int code) {
        this.error = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
