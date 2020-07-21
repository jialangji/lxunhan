package com.ay.lxunhan.wyyim.chatroom;

import android.app.Activity;
import android.content.Intent;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.Serializable;

public abstract class BaseAction implements Serializable {

    private int iconResId;

    private int titleId;

    private transient int index;
    private transient Container container;

    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    protected BaseAction(int iconResId, int titleId) {
        this.iconResId = iconResId;
        this.titleId = titleId;
    }

    public Activity getActivity() {
        return container.activity;
    }

    public String getAccount() {
        return container.account;
    }

    public SessionTypeEnum getSessionType() {
        return container.sessionType;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getTitleId() {
        return titleId;
    }

    public Container getContainer() {
        return container;
    }

    public abstract void onClick();

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // default: empty
    }

    protected void sendMessage(IMMessage message) {
        container.proxy.sendMessage(message);
    }

    protected int makeRequestCode(int requestCode) {
        if ((requestCode & 0xffffff00) != 0) {
            throw new IllegalArgumentException("Can only use lower 8 bits for requestCode");
        }
        return ((index + 1) << 8) + (requestCode & 0xff);
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
