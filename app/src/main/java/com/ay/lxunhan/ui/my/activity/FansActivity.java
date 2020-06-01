package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.AttentionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FansActivity extends BaseActivity {

    @BindView(R.id.rl_fans)
    RecyclerView rlFans;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private List<AttentionBean> attentionBeans=new ArrayList<>();
    private BaseQuickAdapter attentionAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 5; i++) {
            attentionBeans.add(new AttentionBean());
        }
        attentionAdapter = new BaseQuickAdapter<AttentionBean, BaseViewHolder>(R.layout.item_attention,attentionBeans) {
            @Override
            protected void convert(BaseViewHolder helper, AttentionBean item) {

            }
        };
        rlFans.setLayoutManager(new LinearLayoutManager(this));
        rlFans.setAdapter(attentionAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans;
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

    public static void startFansActivity(Context context){
        Intent intent=new Intent(context,FansActivity.class);
        context.startActivity(intent);
    }
}
