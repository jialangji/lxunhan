package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.NotationBean;
import com.ay.lxunhan.bean.NotationSystemBean;
import com.ay.lxunhan.contract.NotificationContact;
import com.ay.lxunhan.presenter.NotificationPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotationSystemActivity extends BaseActivity<NotificationContact.NotificationView, NotificationPresenter> implements NotificationContact.NotificationView {

    @BindView(R.id.rv_notification)
    RecyclerView rvNotification;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;

    private List<NotationSystemBean> beanList=new ArrayList<>();
    private BaseQuickAdapter baseQuickAdapter;
    private int page=1;
    private boolean isRefresh=true;

    @Override
    public NotificationPresenter initPresenter() {
        return new NotificationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notation_system;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getNotificationSystem(page);
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter = new BaseQuickAdapter<NotationSystemBean,BaseViewHolder>(R.layout.item_notation_system,beanList) {
            @Override
            protected void convert(BaseViewHolder helper, NotationSystemBean item) {
                helper.setText(R.id.tv_time,item.getTimeText());
                helper.setText(R.id.tv_content,item.getContent());
            }
        };
        rvNotification.setLayoutManager(new LinearLayoutManager(this));
        rvNotification.setAdapter(baseQuickAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                isRefresh=true;
                page=1;
                presenter.getNotificationSystem(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==beanList.size()){
                    isRefresh=false;
                    page=page+1;
                    presenter.getNotificationSystem(page);
                }else {
                    swipeRefresh.finishLoadmore();
                }
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

    @Override
    public void getNotificationFinish(NotationBean notationBean) {

    }

    @Override
    public void getNotificationSystemFinish(List<NotationSystemBean> notationSystemBean) {

        if (isRefresh){
            beanList.clear();
            swipeRefresh.finishRefreshing();
        }else{
            swipeRefresh.finishLoadmore();
        }
        beanList.addAll(notationSystemBean);
        baseQuickAdapter.notifyDataSetChanged();

    }

    public static void startNotationSystemActivity(Context context) {
        Intent intent = new Intent(context, NotationSystemActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }
}
