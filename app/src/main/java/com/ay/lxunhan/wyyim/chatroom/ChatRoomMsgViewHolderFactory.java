package com.ay.lxunhan.wyyim.chatroom;

import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.HashMap;

public class ChatRoomMsgViewHolderFactory {

    private static HashMap<Class<? extends MsgAttachment>, Class<? extends MsgViewHolderBase>> viewHolders = new HashMap<>();

    static {
    }

    public static void register(Class<? extends MsgAttachment> attach, Class<? extends MsgViewHolderBase> viewHolder) {
        viewHolders.put(attach, viewHolder);
    }

    public static Class<? extends MsgViewHolderBase> getViewHolderByType(IMMessage message) {
        if (message.getMsgType() == MsgTypeEnum.text) {
            return ChatRoomViewHolderText.class;
        } else {
            Class<? extends MsgViewHolderBase> viewHolder = null;
            if (message.getAttachment() != null) {
                Class<? extends MsgAttachment> clazz = message.getAttachment().getClass();
                while (viewHolder == null && clazz != null) {
                    viewHolder = viewHolders.get(clazz);
                    if (viewHolder == null) {
                        clazz = MsgViewHolderFactory.getSuperClass(clazz);
                    }
                }
            }
            return viewHolder == null ? MsgViewHolderUnknown.class : viewHolder;
        }
    }

    public static int getViewTypeCount() {
        // plus text and unknown
        return viewHolders.size() + 2;
    }
}
