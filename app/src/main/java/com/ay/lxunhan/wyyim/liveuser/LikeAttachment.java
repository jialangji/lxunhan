package com.ay.lxunhan.wyyim.liveuser;

import com.alibaba.fastjson.JSONObject;

public class LikeAttachment extends CustomAttachment {
    public LikeAttachment() {
        super(CustomAttachmentType.like);
    }

    @Override
    protected void parseData(JSONObject data) {

    }

    @Override
    protected JSONObject packData() {
        return null;
    }
}