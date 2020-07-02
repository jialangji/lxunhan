package com.ay.lxunhan.observer;

public interface AitTextChangeListener {

    void onTextAdd(String content, int start, int length);

    void onTextDelete(int start, int length);
}