package com.ay.lxunhan.wyyim.chatroom;

import com.netease.nimlib.sdk.msg.model.IMMessage;

public interface ModuleProxy {
    // 发送消息
    boolean sendMessage(IMMessage msg);

    // 消息输入区展开时候的处理
    void onInputPanelExpand();

    // 应当收起输入区
    void shouldCollapseInputPanel();

    // 是否正在录音
    boolean isLongClickEnabled();
}
