package com.ay.lxunhan.wyyim.liveuser;

import com.alibaba.fastjson.JSONObject;
import com.ay.lxunhan.bean.GiftBean;

public class GiftAttachment extends CustomAttachment {
    private final String KEY_PRESENT = "present";
    private final String KEY_COUNT = "count";

    private GiftBean giftType;
    private int count;

    GiftAttachment() {
        super(CustomAttachmentType.gift);
    }

    public GiftAttachment(GiftBean giftType, int count) {
        this();
        this.giftType = giftType;
        this.count = count;
    }

    @Override
    protected void parseData(JSONObject data) {
        this.giftType = (GiftBean) data.get(KEY_PRESENT);
        this.count = data.getIntValue(KEY_COUNT);
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put(KEY_PRESENT, giftType);
        data.put(KEY_COUNT, count);
        return data;
    }

    public GiftBean getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftBean giftType) {
        this.giftType = giftType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
