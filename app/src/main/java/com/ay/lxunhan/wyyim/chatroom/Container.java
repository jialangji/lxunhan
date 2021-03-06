package com.ay.lxunhan.wyyim.chatroom;

import android.app.Activity;

import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

public class Container {
    public final Activity activity;
    public final String account;
    public final SessionTypeEnum sessionType;
    public final ModuleProxy proxy;

    public Container(Activity activity, String account, SessionTypeEnum sessionType, ModuleProxy proxy) {
        this.activity = activity;
        this.account = account;
        this.sessionType = sessionType;
        this.proxy = proxy;
    }
}