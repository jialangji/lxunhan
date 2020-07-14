package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.LbBean;
import com.ay.lxunhan.bean.RechargeBean;
import com.ay.lxunhan.contract.RechargeContract;
import com.ay.lxunhan.presenter.RechargePresenter;
import com.ay.lxunhan.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyCoinActivity extends BaseActivity<RechargeContract.RechargeView,RechargePresenter> implements RechargeContract.RechargeView {

    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.rv_recharge)
    RecyclerView rvRecharge;

    private List<RechargeBean> rechargeBeans=new ArrayList<>();
    private BaseQuickAdapter rechargeAdapter;

    @Override
    public RechargePresenter initPresenter() {
        return new RechargePresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        rechargeAdapter = new BaseQuickAdapter<RechargeBean,BaseViewHolder>(R.layout.item_recharge,rechargeBeans) {
            @Override
            protected void convert(BaseViewHolder helper, RechargeBean item) {
                helper.setGone(R.id.iv_lable,item.isSelect());
                helper.setText(R.id.tv_lb_coin,item.getGold()+"乐讯币");
                helper.setText(R.id.tv_lb_money,item.getMoney()+"元");
                helper.setTextColor(R.id.tv_lb_money,getResources().getColor(item.isSelect()?R.color.white:R.color.color_78));
                helper.setTextColor(R.id.tv_lb_coin,getResources().getColor(item.isSelect()?R.color.white:R.color.color_161824));
                RelativeLayout rlLayout=helper.getView(R.id.rl_layout);
                rlLayout.setBackground(getResources().getDrawable(item.isSelect()?R.drawable.shape_radius_pink_4:R.drawable.shape_grayec_line_4));
            }
        };
        rvRecharge.setLayoutManager(new GridLayoutManager(this,3));
        rvRecharge.addItemDecoration(new GridSpacingItemDecoration(3,10,true));
        rvRecharge.setAdapter(rechargeAdapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_coin;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.tv_recharge:
                RechargeBean bean = null;
                for (RechargeBean rechargeBean : rechargeBeans) {
                    if (rechargeBean.isSelect()){
                        bean=rechargeBean;
                    }
                }
                if (bean!=null){
                    PayActivity.startPayActivity(this,bean);
                }else{
                    ToastUtil.makeShortText(this,"请选择充值数量");
                }
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        presenter.getGold();
        presenter.getRechargeList();
    }

    @Override
    protected void initListener() {
        super.initListener();
        rechargeAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (RechargeBean rechargeBean : rechargeBeans) {
                rechargeBean.setSelect(false);
            }
            rechargeBeans.get(position).setSelect(true);
            rechargeAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void getShowFinish(LbBean lbBean) {
        tvCoin.setText(lbBean.getGold());
    }

    @Override
    public void getRechargeListFinish(List<RechargeBean> list) {
        rechargeBeans.clear();
        rechargeBeans.addAll(list);
        rechargeAdapter.notifyDataSetChanged();
    }

    public static void startBuyCoinActivity(Context context){
        Intent intent=new Intent(context,BuyCoinActivity.class);
        context.startActivity(intent);
    }
}
