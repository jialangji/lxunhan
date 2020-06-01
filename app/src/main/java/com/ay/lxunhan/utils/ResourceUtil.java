package com.ay.lxunhan.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResourceUtil {
    public static String getContent(Context c, int rid) {
        if (null != c) {
            return c.getString(rid);
        } else {
            return "";
        }
    }

    public static int getColor(Context c, int rid) {
        if (null != c) {
            return c.getResources().getColor(rid);
        } else {
            return 0;
        }
    }

    public static float getDimension(Context c, int rid) {
        if (null != c) {
            return c.getResources().getDimension(rid);
        } else {
            return 0;
        }

    }

    public static Drawable getDrawable(Context c, int rid) {
        if (null != c) {
            return c.getResources().getDrawable(rid);
        } else {
            return null;
        }

    }

    public static String[] getStringArray(Context c, int rid) {
        if (null != c) {
            return c.getResources().getStringArray(rid);
        } else {
            return null;
        }


    }

    public static void setTextViewDrawableLeft(Context c, TextView view, int rid) {
        if (null != c) {
            Drawable img = c.getResources().getDrawable(rid);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            view.setCompoundDrawables(img, null, null, null); //设置左图标
        }

    }

    public static void setTextViewDrawableLeft(Context c, TextView view, Drawable drawable, int paddingStart) {
        if (null != c) {
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawables(drawable, null, null, null); //设置左图标
            view.setCompoundDrawablePadding(paddingStart);
        }

    }

    public static void setTextViewDrawableTop(Context c, TextView view, int rid) {
        if (null != c) {
            Drawable img = c.getResources().getDrawable(rid);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            view.setCompoundDrawables(null, img, null, null); //设置左图标
        }

    }

    public static void setTextViewDrawableRight(Context c, TextView view, int rid) {
        if (null != c) {
            Drawable img = c.getResources().getDrawable(rid);
// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            view.setCompoundDrawables(null, null, img, null); //设置左图标
        }

    }

    public static void setTextViewMarginTop(  TextView tv, int marginTop) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, marginTop, 0, 0);
        tv.setLayoutParams(params);
    }

}

