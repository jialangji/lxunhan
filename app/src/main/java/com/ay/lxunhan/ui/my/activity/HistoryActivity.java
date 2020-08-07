package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.adapter.PublicAdapterUtil;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.UserMediaListBean;
import com.ay.lxunhan.bean.model.SendCommentModel;
import com.ay.lxunhan.contract.COrHContract;
import com.ay.lxunhan.presenter.CorHpRresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.FriendDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class HistoryActivity extends BaseActivity<COrHContract.CorHView, CorHpRresenter> implements COrHContract.CorHView {

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<UserMediaListBean> historyList = new ArrayList<>();
    private BaseQuickAdapter historyAdapter;
    private int page = 1;
    private boolean isRefresh = true;
    private SensorManager sensorManager;
    private Jzvd.JZAutoFullscreenListener jzAutoFullscreenListener;
    private int mPosition;

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
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new Jzvd.JZAutoFullscreenListener();
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向\
        historyAdapter = PublicAdapterUtil.getCollectOrHistoryAdapter(historyList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvHistory.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
        rvHistory.setLayoutManager(gridLayoutManager);
        rvHistory.setAdapter(historyAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (historyList.get(i).getItemType() == 5) {
                    return 1;
                } else {
                    return 2;
                }
            }
        });
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
                case R.id.ll_like:
                    mPosition = position;
                    SendCommentModel sendCommentModel = new SendCommentModel(historyList.get(position).getId() + "", historyList.get(position).getType());
                    presenter.addLike(sendCommentModel);
                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(HistoryActivity.this, historyList.get(position).getUid());
                    break;
                case R.id.tv_attention:
                    mPosition = position;
                    presenter.attention(historyList.get(mPosition).getUid());
                    break;
                case R.id.tv_quiz://投票
                    if (!historyList.get(position).getIs_pate()) {
                        mPosition = position;
                        int oid = -1;
                        for (UserMediaListBean.OptionListBean optionListBean : historyList.get(position).getOption_list()) {
                            if (optionListBean.isUserIsSelect()) {
                                oid = optionListBean.getId();
                            }
                        }
                        SendCommentModel quizModel = new SendCommentModel(historyList.get(position).getId(), oid);
                        presenter.quiz(quizModel);
                    }
                    break;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(jzAutoFullscreenListener);
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //播放器重力感应
        presenter.history(1);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(jzAutoFullscreenListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        JzvdStd.goOnPlayOnResume();
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
        if (historyList.get(mPosition).getIs_fol() == 1) {
            historyList.get(mPosition).setIs_fol(0);
        } else {
            historyList.get(mPosition).setIs_fol(1);
        }
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void quziFinish() {
        presenter.history(1);
    }

    @Override
    public void addLikeFinish() {
        if (historyList.get(mPosition).getIs_like()) {
            historyList.get(mPosition).setIs_like(0);
            historyList.get(mPosition).setLike_count(historyList.get(mPosition).getLike_count() - 1);
        } else {
            historyList.get(mPosition).setIs_like(1);
            historyList.get(mPosition).setLike_count(historyList.get(mPosition).getLike_count() + 1);
        }
        historyAdapter.notifyDataSetChanged();
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
