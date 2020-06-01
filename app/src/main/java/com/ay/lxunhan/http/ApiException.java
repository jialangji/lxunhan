package com.ay.lxunhan.http;

/**
 * 异常
 */

public class ApiException extends RuntimeException {

    private int status;
    private String detailMessage;
    public ApiException(int state, String detailMessage) {
        super(detailMessage);
        this.status = state;
        this.detailMessage = detailMessage;
    }

    public int getErrCode() {
        return status;
    }
    public String getErrMessage() {
        return detailMessage;
    }
}
