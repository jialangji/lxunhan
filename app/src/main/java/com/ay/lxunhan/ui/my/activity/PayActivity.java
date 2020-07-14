package com.ay.lxunhan.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.RechargeBean;
import com.ay.lxunhan.bean.WxOrderBean;
import com.ay.lxunhan.contract.PayContract;
import com.ay.lxunhan.presenter.PayPresenter;
import com.tencent.mm.opensdk.modelpay.PayReq;

import butterknife.BindView;
import butterknife.OnClick;

public class PayActivity extends BaseActivity<PayContract.PayView, PayPresenter> implements PayContract.PayView {

    @BindView(R.id.tv_lxb)
    TextView tvLxb;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.check_wx)
    CheckBox checkWx;
    private RechargeBean bean;

    @Override
    public PayPresenter initPresenter() {
        return new PayPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        bean = (RechargeBean) getIntent().getSerializableExtra("bean");
        tvLxb.setText(bean.getGold()+"乐讯币");
        tvMoney.setText(bean.getMoney()+"元");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected int getBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean barTextIsDark() {
        return true;
    }

    @OnClick({R.id.rl_finish, R.id.ll_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_wx:
                checkWx.setChecked(true);
                presenter.recharge(bean.getId());
                break;
        }
    }

    public static void startPayActivity(Context context, RechargeBean bean) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    public void rechargeFinish(WxOrderBean data) {
        PayReq req = new PayReq();
        req.appId = data.getAppid();
        req.partnerId = data.getPartnerid();
        req.prepayId = data.getPrepayid();
        req.packageValue = data.getPackageX();
        req.nonceStr = data.getNoncestr();
        req.timeStamp = data.getTimestamp();
        req.sign = data.getSign();
        AppContext.mWxApi.sendReq(req);
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
