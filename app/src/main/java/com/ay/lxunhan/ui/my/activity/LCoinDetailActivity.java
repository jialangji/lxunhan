package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.CoinBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LCoinDetailActivity extends BaseActivity {

    @BindView(R.id.rv_coin)
    RecyclerView rvCoin;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private BaseQuickAdapter coinAdapter;
    private List<CoinBean> coinBeanList=new ArrayList<>();

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 5; i++) {
            coinBeanList.add(new CoinBean());
        }
        coinAdapter = new BaseQuickAdapter<CoinBean,BaseViewHolder>(R.layout.item_coin,coinBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, CoinBean item) {

            }
        };
        rvCoin.setLayoutManager(new LinearLayoutManager(this));
        rvCoin.setAdapter(coinAdapter);
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
}
