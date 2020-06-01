package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.NotificationBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.rv_notification)
    RecyclerView rvNotification;
    private List<NotificationBean> notificationBeans=new ArrayList<>();
    private BaseQuickAdapter notificationAdapter;


    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 3; i++) {
            notificationBeans.add(new NotificationBean());
        }
        notificationAdapter = new BaseQuickAdapter<NotificationBean,BaseViewHolder>(R.layout.item_notification,notificationBeans) {
            @Override
            protected void convert(BaseViewHolder helper, NotificationBean item) {

            }
        };
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }

    public static void startNotificationActivity(Context context){
        Intent intent=new Intent(context,NotificationActivity.class);
        context.startActivity(intent);
    }
}
