package com.ay.lxunhan.ui.my.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.ay.lxunhan.R;
import com.ay.lxunhan.base.AppContext;
import com.ay.lxunhan.base.BaseActivity;
import com.ay.lxunhan.bean.PayResult;
import com.ay.lxunhan.bean.RechargeBean;
import com.ay.lxunhan.bean.WxOrderBean;
import com.ay.lxunhan.contract.PayContract;
import com.ay.lxunhan.presenter.PayPresenter;
import com.ay.lxunhan.ui.public_ac.activity.PayResultActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PayActivity extends BaseActivity<PayContract.PayView, PayPresenter> implements PayContract.PayView {

    @BindView(R.id.tv_lxb)
    TextView tvLxb;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.check_wx)
    CheckBox checkWx;
    @BindView(R.id.check_alipay)
    CheckBox checkAlipay;
    private RechargeBean bean;

    @Override
    public PayPresenter initPresenter() {
        return new PayPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        bean = (RechargeBean) getIntent().getSerializableExtra("bean");
        tvLxb.setText(bean.getGold() + "乐讯币");
        tvMoney.setText(bean.getMoney() + "元");
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

    @OnClick({R.id.rl_finish, R.id.ll_wx, R.id.ll_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_finish:
                finish();
                break;
            case R.id.ll_wx:
                if (checkAlipay.isChecked()){
                    checkAlipay.setChecked(false);
                }
                checkWx.setChecked(true);
                presenter.recharge(bean.getId());
                break;
            case R.id.ll_alipay:
                if (checkWx.isChecked()){
                    checkWx.setChecked(false);
                }
                checkAlipay.setChecked(true);
                presenter.rechargeAlipay(bean.getId());
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
    public void rechargeAlipayFinish(String orderBean) {

        initZFB(orderBean);
    }

    @Override
    public void showProgress() {
        hudLoader.show();
    }

    @Override
    public void hideProgress() {
        hudLoader.dismiss();
    }


    public void initZFB(String order) {
        Runnable runnable = () -> {
            PayTask alipay = new PayTask(PayActivity.this);
            Map<String, String> result = alipay.payV2(order, true);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(runnable);
        payThread.start();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        PayResultActivity.startPayResultActivity(PayActivity.this,1);
                        finish();
                    } else {
                        PayResultActivity.startPayResultActivity(PayActivity.this,2);
                        finish();
                    }
                    break;
            }
        }
    };

}
