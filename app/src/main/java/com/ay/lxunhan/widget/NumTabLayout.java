package com.ay.lxunhan.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class NumTabLayout extends TabLayout {
    //notice 有两种设置TabVIew宽度的方式，在这里我们没必要设置app:tabMinWidth这个属性，因为屏幕的宽度是不固定的，
    // 我们不可能将tabView的宽度设死。所以就跑到else的方法里。在这里mMode的模式有两种MODE_SCROLLABLE和MODE_FIXED，
    //notice  MODE_FIXED是一下子展示所有的TabView,
    //notice MODE_SCROLLABLE是展示部分的TabView，其他的TabView通过滑动来实现，(最小宽度系统写死72dp)
    private static final int TabViewNumber = 7;
    //support 28的 字段名变了 scrollableTabMinWidth(api设置)
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";
    private static final String SCROLLABLE_TAB_MIN_WIDTH_28 = "scrollableTabMinWidth";
    public NumTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public NumTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public NumTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }

    private void initTabMinWidth() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int tabMinWidth = screenWidth / TabViewNumber;
        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH_28);
            field.setAccessible(true);//去除私有属性
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}