package com.ay.lxunhan.wyyim.chatroom;

import com.ay.lxunhan.R;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

public class MsgViewHolderUnknown extends MsgViewHolderBase {
    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_unknown;
    }

    @Override
    protected boolean isShowHeadImage() {
        if (message.getSessionType() == SessionTypeEnum.ChatRoom) {
            return false;
        }
        return true;
    }

    @Override
    protected void inflateContentView() {
    }

    @Override
    protected void bindContentView() {
    }
}
