package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.contract.COrHContract;
import com.ay.lxunhan.presenter.CorHpRresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HistoryActivity extends BaseActivity<COrHContract.CorHView, CorHpRresenter> implements COrHContract.CorHView {

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<UserMediaListBean> historyList = new ArrayList<>();
    private BaseQuickAdapter historyAdapter;
    private int page = 1;
    private boolean isRefresh = true;

    @Override
    public CorHpRresenter initPresenter() {
        return new CorHpRresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        super.initView();
        //设置全屏播放
        historyAdapter = PublicAdapterUtil.getCollectOrHistoryAdapter2(historyList, this,false);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.history(page);
    }

    @Override
    protected void initListener() {
        super.initListener();

        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh = true;
                page = 1;
                presenter.history(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == historyList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.history(page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        historyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    switch (historyList.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(HistoryActivity.this, historyList.get(position).getType(), historyList.get(position).getId());
                            break;
                        case 2:
                            VideoDetailActivity.startVideoDetailActivity(HistoryActivity.this, String.valueOf(historyList.get(position).getId()));
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(HistoryActivity.this, historyList.get(position).getType(), historyList.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(HistoryActivity.this, historyList.get(position).getId());
                            break;
                        case 5:
                            SmallVideoActivity.startSmallVideoActivity(HistoryActivity.this, historyList.get(position).getId());

                            break;
                    }
                    break;
            }
        });
    }


    @Override
    protected int getBarColor() {
        return R.color.transparent;
    }

    @Override
    protected boolean barTextIsDark() {
        return false;
    }

    @OnClick({R.id.rl_finish,R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_clear:
                showMConfirmDialog("提示", "确定清空记录吗?", (dialog, which) -> dialog.cancel(), (dialog, which) -> presenter.clearHistory());
                break;
        }
    }

    public void showMConfirmDialog(String title, String message, DialogInterface.OnClickListener okEvent, DialogInterface.OnClickListener cancelEvent){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.cancel,
                okEvent);
        builder.setNegativeButton(R.string.sure,
                cancelEvent);
        builder.show();
    }

    public static void startHistoryActivity(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getListFinish(List<UserMediaListBean> beans) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            historyList.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        historyList.addAll(beans);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void attentionFinish() {
    }

    @Override
    public void quziFinish() {
    }

    @Override
    public void addLikeFinish() {
    }

    @Override
    public void clearFinish() {
        presenter.history(1);
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }
}
