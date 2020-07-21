package com.ay.lxunhan.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.widget
 * @date 2020/7/21
 */
public class CountPop extends PopupWindow {
    private View conentView;
    private List<String> list = new ArrayList<>();
    private onItemClick onItemClick;
    private final int popupHeight;
    private final int popupWidth;

    public CountPop(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.service_list_window, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = conentView.getMeasuredHeight();
        popupWidth = conentView.getMeasuredWidth();
        list.add("1000");

        list.add("500");

        list.add("100");

        list.add("10");
        list.add("1");
        RecyclerView rvType = conentView.findViewById(R.id.rv_type);

        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.service_list_item, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setText(item);
            }
        };
        rvType.setLayoutManager(new LinearLayoutManager(context));
        rvType.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (onItemClick != null) {
                onItemClick.itemClick(list.get(position));
            }
        });
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.NO_GRAVITY,  (location[0] + parent.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
        } else {
            this.dismiss();
        }
    }

    public void setOnItemClick(onItemClick onItemClick) {
        if (onItemClick != null) {
            this.onItemClick = onItemClick;
        }
    }

    public interface onItemClick {
        void itemClick(String str);
    }


}
