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

public class CollectActivity extends BaseActivity<COrHContract.CorHView, CorHpRresenter> implements COrHContract.CorHView {

    @BindView(R.id.rv_collect)
    RecyclerView rvCollect;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<UserMediaListBean> collectList = new ArrayList<>();
    private BaseQuickAdapter collectAdapter;
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
        return R.layout.activity_collect;
    }


    @Override
    protected void initView() {
        super.initView();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jzAutoFullscreenListener = new Jzvd.JZAutoFullscreenListener();
        //设置全屏播放
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向\
        collectAdapter = PublicAdapterUtil.getCollectOrHistoryAdapter(collectList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvCollect.addItemDecoration(new GridSpacingItemDecoration(2, 10, false));
        rvCollect.setLayoutManager(gridLayoutManager);
        rvCollect.setAdapter(collectAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (collectList.get(i).getItemType() == 5) {
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
                presenter.collect(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == collectList.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.collect(page);
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
                case R.id.ll_like:
                    mPosition = position;
                    SendCommentModel sendCommentModel = new SendCommentModel(collectList.get(position).getId() + "", collectList.get(position).getType());
                    presenter.addLike(sendCommentModel);
                    break;
                case R.id.iv_header:
                    FriendDetailActivity.startUserDetailActivity(CollectActivity.this, collectList.get(position).getUid());
                    break;
                case R.id.tv_attention:
                    mPosition = position;
                    presenter.attention(collectList.get(mPosition).getUid());
                    break;
                case R.id.tv_quiz://投票
                    if (!collectList.get(position).getIs_pate()) {
                        mPosition = position;
                        int oid = -1;
                        for (UserMediaListBean.OptionListBean optionListBean : collectList.get(position).getOption_list()) {
                            if (optionListBean.isUserIsSelect()) {
                                oid = optionListBean.getId();
                            }
                        }
                        SendCommentModel quizModel = new SendCommentModel(collectList.get(position).getId(), oid);
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
        presenter.collect(1);
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
        if (collectList.get(mPosition).getIs_fol() == 1) {
            collectList.get(mPosition).setIs_fol(0);
        } else {
            collectList.get(mPosition).setIs_fol(1);
        }
        collectAdapter.notifyDataSetChanged();
    }

    @Override
    public void quziFinish() {
        presenter.collect(1);
    }

    @Override
    public void addLikeFinish() {
        if (collectList.get(mPosition).getIs_like()) {
            collectList.get(mPosition).setIs_like(0);
            collectList.get(mPosition).setLike_count(collectList.get(mPosition).getLike_count() - 1);
        } else {
            collectList.get(mPosition).setIs_like(1);
            collectList.get(mPosition).setLike_count(collectList.get(mPosition).getLike_count() + 1);
        }
        collectAdapter.notifyDataSetChanged();
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
