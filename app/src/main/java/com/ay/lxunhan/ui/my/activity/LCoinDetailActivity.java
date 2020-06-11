package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.CoinBean;
import com.ay.lxunhan.contract.LbBillContract;
import com.ay.lxunhan.presenter.LbBillPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LCoinDetailActivity extends BaseActivity<LbBillContract.LbBillView, LbBillPresenter> implements LbBillContract.LbBillView {

    @BindView(R.id.rv_coin)
    RecyclerView rvCoin;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private BaseQuickAdapter coinAdapter;
    private List<CoinBean> coinBeanList=new ArrayList<>();
    private int page=1;
    private boolean isRefresh=true;

    @Override
    public LbBillPresenter initPresenter() {
        return new LbBillPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        coinAdapter = new BaseQuickAdapter<CoinBean,BaseViewHolder>(R.layout.item_coin,coinBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, CoinBean item) {
                helper.setText(R.id.tv_title,item.getDesc());
                helper.setText(R.id.tv_date,item.getCreated_at());
                helper.setText(R.id.tv_coin,(item.getStatus()?"+":"-" )+item.getGold()+" 乐币");
            }
        };
        rvCoin.setLayoutManager(new LinearLayoutManager(this));
        rvCoin.setAdapter(coinAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getLbBIll(page);
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
                presenter.getLbBIll(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page*Contacts.LIMIT==coinBeanList.size()){
                    isRefresh=false;
                    page=page+1;
                    presenter.getLbBIll(page);
                }else {
                    swipeRefresh.finishLoadmore();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lcoin_detail;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        finish();
    }
    public static void startLCoinDetailActivity(Context context){
        Intent intent=new Intent(context,LCoinDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getLbBillFinish(List<CoinBean> list) {
        if (isRefresh){
            swipeRefresh.finishRefreshing();
            coinBeanList.clear();
        }else{
            swipeRefresh.finishLoadmore();
        }
        coinBeanList.addAll(list);
        coinAdapter.notifyDataSetChanged();
    }
}
