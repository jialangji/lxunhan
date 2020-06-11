package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.BillBean;
import com.ay.lxunhan.contract.BillContract;
import com.ay.lxunhan.presenter.BillPresenter;
import com.ay.lxunhan.utils.Contacts;
import com.ay.lxunhan.utils.StringUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BillActivity extends BaseActivity<BillContract.BillView, BillPresenter> implements BillContract.BillView {
    @BindView(R.id.tv_rechange)
    TextView tvRechange;
    @BindView(R.id.tv_withdraw)
    TextView tvWithdraw;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.swipe_refresh)
    TwinklingRefreshLayout swipeRefresh;
    private List<BillBean.BalanceLogBean> billBeans=new ArrayList<>();
    private BaseQuickAdapter billAdapter;
    private int type;
    private int page=1;
    private boolean isRefresh=true;
    private int timeType=1;

    @Override
    public BillPresenter initPresenter() {
        return new BillPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        type=getIntent().getIntExtra("type",0);
        billAdapter = new BaseQuickAdapter<BillBean.BalanceLogBean,BaseViewHolder>(R.layout.item_bill,billBeans) {
            @Override
            protected void convert(BaseViewHolder helper, BillBean.BalanceLogBean item) {
                helper.setText(R.id.tv_title,item.getDesc());
                helper.setText(R.id.tv_date,item.getCreated_at());
                helper.setText(R.id.tv_money,item.getBalance());
            }
        };
        rvBill.setLayoutManager(new LinearLayoutManager(this));
        rvBill.setAdapter(billAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getBillList(type,timeType,page);
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
                presenter.getBillList(type,timeType,page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (page* Contacts.LIMIT==billBeans.size()){
                    isRefresh=false;
                    page=page+1;
                    presenter.getBillList(type,timeType,page);
                }else{
                    swipeRefresh.finishLoadmore();
                }
            }
        });
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

    public static void startBillActivity(Context context,int type) {
        Intent intent = new Intent(context, BillActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    private void showSelectTime() {// 弹出选择器
        List<String> typeList=new ArrayList<>();
        typeList.add("全部");
        typeList.add("本月");
        typeList.add("近七天");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            tvSelect.setText(typeList.get(options1));
            timeType=options1;
            isRefresh=true;
            page=1;
            presenter.getBillList(type,timeType,page);
        })
                .build();
        pvOptions.setPicker(typeList);//三级选择器
        pvOptions.show();
    }


    @OnClick({R.id.rl_finish, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_select:
                showSelectTime();
                break;
        }
    }

    @Override
    public void getBillListFinish(BillBean list) {
        tvRechange.setText(String.format(StringUtil.getString(R.string.rechange_price),list.getExchange()));
        tvWithdraw.setText(String.format(StringUtil.getString(R.string.withdraw_price),list.getWithdraw()));
        if (isRefresh){
            billBeans.clear();
            swipeRefresh.finishRefreshing();
        }else{
            swipeRefresh.finishLoadmore();
        }
        billBeans.addAll(list.getBalance_log());
        billAdapter.notifyDataSetChanged();
    }
}
