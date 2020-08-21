package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class CollectActivity extends BaseActivity<COrHContract.CorHView, CorHpRresenter> implements COrHContract.CorHView {

    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<UserMediaListBean> collectList = new ArrayList<>();
    private BaseQuickAdapter collectAdapter;
    private int page = 1;
    private boolean isRefresh = true;

    @Override
    public CorHpRresenter initPresenter() {
        return new CorHpRresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }


    @Override
    protected void initView() {
        super.initView();

        collectAdapter = PublicAdapterUtil.getCollectOrHistoryAdapter2(collectList, this,true);
        rvCollect.setLayoutManager(new LinearLayoutManager(this));
        rvCollect.setAdapter(collectAdapter);

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
                presenter.collect(0,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == collectList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.collect(0,page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        collectAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.ll_linear:
                    switch (collectList.get(position).getType()) {
                        case 1:
                            HomeDetailActivity.startHomeDetailActivity(CollectActivity.this, collectList.get(position).getType(), collectList.get(position).getId());
                            break;
                        case 2:
                            VideoDetailActivity.startVideoDetailActivity(CollectActivity.this, String.valueOf(collectList.get(position).getId()));
                            break;
                        case 3:
                            HomeAskDetailActivity.startHomeAskDetailActivity(CollectActivity.this, collectList.get(position).getType(), collectList.get(position).getId());
                            break;
                        case 4:
                            HomeQuziDetailActivity.startHomeQuizDetailActivity(CollectActivity.this, collectList.get(position).getId());
                            break;
                        case 5:
                            SmallVideoActivity.startSmallVideoActivity(CollectActivity.this,collectList.get(position).getId());
                            break;
                    }
                    break;
                case R.id.tv_del:
                    break;

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //播放器重力感应
        presenter.collect(0,1);
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

    public static void startCollectActivity(Context context) {
        Intent intent = new Intent(context, CollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getListFinish(List<UserMediaListBean> beans) {
        if (isRefresh) {
            swipeRefresh.finishRefreshing();
            collectList.clear();
        } else {
            swipeRefresh.finishLoadmore();
        }
        collectList.addAll(beans);
        collectAdapter.notifyDataSetChanged();
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
