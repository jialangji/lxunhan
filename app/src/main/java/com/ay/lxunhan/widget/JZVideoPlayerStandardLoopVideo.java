package com.ay.lxunhan.widget;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

/**
 * Author:高天宇
 * Date:2019/7/12
 */
public class JZVideoPlayerStandardLoopVideo extends JzvdStd {
    public JZVideoPlayerStandardLoopVideo(Context context) {
        super(context);
    }
    public JZVideoPlayerStandardLoopVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }


}
