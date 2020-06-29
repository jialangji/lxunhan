package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.NotationBean;
import com.ay.lxunhan.bean.NotationSystemBean;
import com.ay.lxunhan.contract.NotificationContact;
import com.ay.lxunhan.presenter.NotificationPresenter;
import com.ay.lxunhan.ui.home.activity.HomeAskDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeDetailActivity;
import com.ay.lxunhan.ui.home.activity.HomeQuziDetailActivity;
import com.ay.lxunhan.ui.public_ac.activity.PyqDetailActivity;
import com.ay.lxunhan.ui.video.activity.SmallVideoActivity;
import com.ay.lxunhan.ui.video.activity.VideoDetailActivity;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity<NotificationContact.NotificationView, NotificationPresenter> implements NotificationContact.NotificationView {

    @BindView(R.id.rv_notification)
    RecyclerView rvNotification;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_message_count)
    TextView tvMessageCount;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    @BindView(R.id.ll_system)
    LinearLayout llSystem;
    private List<NotationBean.NewsListBean> notificationBeans = new ArrayList<>();
    private BaseQuickAdapter notificationAdapter;
    private int page = 1;
    private boolean isRefresh = true;


    @Override
    protected void initView() {
        super.initView();

        notificationAdapter = new BaseQuickAdapter<NotationBean.NewsListBean, BaseViewHolder>(R.layout.item_notification, notificationBeans) {
            @Override
            protected void convert(BaseViewHolder helper, NotationBean.NewsListBean item) {
                GlideUtil.loadCircleImgForHead(NotificationActivity.this, helper.getView(R.id.iv_header), item.getAvatar());
                helper.setGone(R.id.iv_v, item.getIs_media());
                helper.setText(R.id.tv_name, item.getNickname());
                helper.setText(R.id.tv_content, item.getContent());
                helper.setGone(R.id.iv_img, !item.getCover().equals(""));
                GlideUtil.loadImg(NotificationActivity.this, helper.getView(R.id.iv_img), item.getCover());
                helper.setText(R.id.tv_time, item.getDescs() + " " + item.getTimeText());
            }
        };
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getNotification(1);
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
                presenter.getNotification(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page * Contacts.LIMIT == notificationBeans.size()) {
                    isRefresh = false;
                    page = page + 1;
                    presenter.getNotification(page);
                } else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
        notificationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (notificationBeans.get(position).getGenre()) {
                    case 1:
                        HomeDetailActivity.startHomeDetailActivity(NotificationActivity.this, 1, notificationBeans.get(position).getAid());
                        break;
                    case 2:
                        VideoDetailActivity.startVideoDetailActivity(NotificationActivity.this, String.valueOf(notificationBeans.get(position).getAid()));
                        break;
                    case 3:
                        HomeAskDetailActivity.startHomeAskDetailActivity(NotificationActivity.this, 3, notificationBeans.get(position).getAid());
                        break;
                    case 4:
                        HomeQuziDetailActivity.startHomeQuizDetailActivity(NotificationActivity.this, notificationBeans.get(position).getAid());
                        break;
                    case 5:
                        SmallVideoActivity.startSmallVideoActivity(NotificationActivity.this, notificationBeans.get(position).getAid());
                        break;
                    case 6:
                        PyqDetailActivity.startPyqDetailActivity(NotificationActivity.this, String.valueOf(notificationBeans.get(position).getAid()));
                        break;
                }
            }
        });
    }

    @Override
    public NotificationPresenter initPresenter() {
        return new NotificationPresenter(this);
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


    public static void startNotificationActivity(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_finish, R.id.ll_system})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_system:
                NotationSystemActivity.startNotationSystemActivity(this);
                break;
        }
    }

    @Override
    public void getNotificationFinish(NotationBean notationBean) {
        if (notationBean.getTz_count() == 0 && TextUtils.isEmpty(notationBean.getTz()) && TextUtils.isEmpty(notationBean.getTz_timeText())) {
            llSystem.setVisibility(View.GONE);
            return;
        }
        tvContent.setText(notationBean.getTz());
        if (notationBean.getTz_count() == 0) {
            tvMessageCount.setVisibility(View.GONE);
        } else {
            tvMessageCount.setVisibility(View.VISIBLE);
            tvMessageCount.setText(notationBean.getTz_count() + "");
        }
        tvTime.setText(notationBean.getTz_timeText());
        if (isRefresh) {
            notificationBeans.clear();
            swipeRefresh.finishRefreshing();
        } else {
            swipeRefresh.finishLoadmore();
        }
        notificationBeans.addAll(notationBean.getNews_list());
        notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNotificationSystemFinish(List<NotationSystemBean> notationSystemBean) {

    }

}
