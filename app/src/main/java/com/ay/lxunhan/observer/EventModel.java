package com.ay.lxunhan.observer;

public class EventModel<T> {
    public static final int CHANGECHANNEL=1;
    public static final int ARTICLELIKE=2;
    public static final int TWOCOMMENTLIKE=3;
    public static final int SENDCOMMENT=4;
    public static final int LOGIN_OUT=10000;
    public static final int LOGIN=10001;
    public static final int WX_LOGIN=5;
    private int messageType;
    private int data2;
    private T data;

    public EventModel(int messageType, T data) {
        this.messageType = messageType;
        this.data = data;
    }

    public EventModel(int messageType, T data, int data2) {
        this.messageType = messageType;
        this.data = data;
        this.data2 = data2;
    }

    public int getMessageType() {
        return messageType;
    }

    public T getData() {
        return data;
    }

    public int getData2() {
        return data2;
    }

    public EventModel(int messageType) {
        this.messageType = messageType;
    }
}
