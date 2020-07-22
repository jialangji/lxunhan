package com.ay.lxunhan.wyyim.liveuser;

import com.alibaba.fastjson.JSONObject;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;

public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JSONObject object =new JSONObject();
            object.getJSONObject(json);
            int type = object.getInteger(KEY_TYPE);
            JSONObject data = object.getJSONObject(KEY_DATA);
            switch (type) {
                case CustomAttachmentType.like:
                    attachment = new LikeAttachment();
                    break;
                case CustomAttachmentType.gift:
                    attachment = new GiftAttachment();
                    break;
                default:
                    attachment = new DefaultCustomAttachment();
                    break;
            }

            if (attachment != null) {
                attachment.fromJson(data);
            }
        } catch (Exception e) {

        }

        return attachment;
    }

    public static String packData(int type, JSONObject data) {
        JSONObject object = new JSONObject();
        object.put(KEY_TYPE, type);
        if (data != null) {
            object.put(KEY_DATA, data);
        }

        return object.toJSONString();
    }
}