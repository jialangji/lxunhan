package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.InviteBean;
import com.ay.lxunhan.contract.InviteContract;
import com.ay.lxunhan.presenter.InvitePresenter;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteListActivity extends BaseActivity<InviteContract.InviteView, InvitePresenter> implements InviteContract.InviteView {

    @BindView(R.id.rv_invite)
    RecyclerView rvInvite;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private int page = 1;
    private boolean isRefrensh = true;
    private BaseQuickAdapter baseQuickAdapter;
    private List<InviteBean.DataBean> dataBeans=new ArrayList<>();


    @Override
    public InvitePresenter initPresenter() {
        return new InvitePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_list;
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getInviteCoin(page);
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter = new BaseQuickAdapter<InviteBean.DataBean,BaseViewHolder>(R.layout.item_invite,dataBeans) {
            @Override
            protected void convert(BaseViewHolder helper, InviteBean.DataBean item) {
                helper.setText(R.id.tv_friend_lxh,item.getSignal());
                helper.setText(R.id.tv_invite_time,item.getCreated_date());
                helper.setText(R.id.tv_get,item.getGold()+"乐讯币");
            }
        };
        rvInvite.setLayoutManager(new LinearLayoutManager(this));
        rvInvite.setAdapter(baseQuickAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page=1;
                isRefrensh=true;
                presenter.getInviteCoin(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==dataBeans.size()){
                    page=page+1;
                    isRefrensh=false;
                    presenter.getInviteCoin(page);
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @Override
    public void sendInviteCodeFinish() {

    }

    @Override
    public void getInviteCoinFinish(InviteBean bean) {

        if (isRefrensh){
            dataBeans.clear();
            swipeRefresh.finishRefreshing();
        }else{
            swipeRefresh.finishLoadmore();
        }
        dataBeans.addAll(bean.getData());
        baseQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }

    public static void startInviteListActivity(Context context) {
        Intent intent = new Intent(context, InviteListActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }
}
