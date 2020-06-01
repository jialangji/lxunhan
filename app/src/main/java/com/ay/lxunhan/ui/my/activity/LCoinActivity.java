package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.TaskBean;
import com.ay.lxunhan.utils.StringUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LCoinActivity extends BaseActivity {


    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    private List<Integer> imgList = new ArrayList<>();
    private List<TaskBean> taskBeanList=new ArrayList<>();
    private BaseQuickAdapter adapter;
    private BaseQuickAdapter taskAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        imgList.add(R.drawable.ic_weal);
        imgList.add(R.drawable.ic_user_task);
        imgList.add(R.drawable.ic_coming_soon);
        adapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_lb_img, imgList) {
            @Override
            protected void convert(BaseViewHolder helper, Integer item) {
                helper.setImageResource(R.id.iv_lb_img, item);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImg.setLayoutManager(linearLayoutManager);
        rvImg.setAdapter(adapter);
        taskAdapter = new BaseQuickAdapter<TaskBean,BaseViewHolder>(R.layout.item_task,taskBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, TaskBean item) {

            }
        };
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.setAdapter(taskAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0 || position == 1) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lcoin;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startLCoinActivity(Context context) {
        Intent intent = new Intent(context, LCoinActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_exchange,R.id.rl_finish, R.id.tv_detail,R.id.iv_shop, R.id._iv_anchor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_exchange:
                ExChangeActivity.startExChangeActivity(this);
                break;
            case R.id.tv_detail:
                LCoinDetailActivity.startLCoinDetailActivity(this);
                break;
            case R.id.iv_shop:
                break;
            case R.id._iv_anchor:
                break;
        }
    }
}
