package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.base.BasePresenter;
import com.ay.lxunhan.bean.BillBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BillActivity extends BaseActivity {
    @BindView(R.id.tv_rechange)
    TextView tvRechange;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private List<BillBean> billBeans=new ArrayList<>();
    private BaseQuickAdapter billAdapter;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        billAdapter = new BaseQuickAdapter<BillBean,BaseViewHolder>(R.layout.item_bill,billBeans) {
            @Override
            protected void convert(BaseViewHolder helper, BillBean item) {

            }
        };
        rvBill.setLayoutManager(new LinearLayoutManager(this));
        rvBill.setAdapter(billAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    public static void startBillActivity(Context context) {
        Intent intent = new Intent(context, BillActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.rl_finish, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_select:
                break;
        }
    }
}
