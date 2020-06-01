package com.ay.lxunhan.utils;

import android.content.Context;

import com.ay.lxunhan.R;
import com.blankj.utilcode.util.ToastUtils;

public class ToastUtil {
    public static void makeShortText(Context context, String msg) {
        if(context!=null){
            ToastUtils.setBgResource(R.drawable.bg_toast);
            ToastUtils.setMsgColor(ResourceUtil.getColor(context, R.color.white));
            ToastUtils.showShort(msg);
        }
    }

    public static void makeShortText(Context context, int resId) {
        if(context!=null){
            ToastUtils.setBgResource(R.drawable.bg_toast);
            ToastUtils.setMsgColor(ResourceUtil.getColor(context, R.color.white));
            ToastUtils.showShort(ResourceUtil.getContent(context,resId));
        }
    }


}
